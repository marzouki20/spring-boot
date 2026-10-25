# API AutoLoc

AutoLoc est une API REST Spring Boot pour une application de location de
véhicules. Le projet utilise Java 17, Spring Boot, Spring Web MVC, Spring Data
JPA, Hibernate, MySQL et Lombok.

## État actuel du projet

Le modèle métier contient déjà les véhicules, agences, clients, employés,
réservations, contrats, paiements, maintenances et équipements.

Pour le moment, le seul endpoint REST implémenté est la création d'un véhicule :

```text
POST http://localhost:8086/api/vehicules/add
```

Les opérations CRUD des véhicules, employés, agences, clients et contrats sont
maintenant disponibles. Les entités réservation, paiement, maintenance et
équipement sont encore modélisées en JPA, mais leurs contrôleurs REST et leurs
services ne sont pas encore disponibles.

## Prérequis

- Java 17
- Maven ou le Maven Wrapper inclus dans le projet
- MySQL Server
- Un client HTTP comme Postman, Insomnia ou curl

L'application est configurée pour utiliser :

```text
Base de données : autoloc_db
Hôte            : localhost
Port            : 3306
Utilisateur     : root
Mot de passe    : vide par défaut
```

Ces valeurs sont définies dans
`src/main/resources/application.properties`. Adaptez-les à votre
environnement, en particulier le nom d'utilisateur et le mot de passe.

## Lancer l'application

Depuis la racine du projet sous Windows :

```powershell
.\mvnw.cmd spring-boot:run
```

Ou compilez puis lancez le fichier JAR :

```powershell
.\mvnw.cmd clean package
java -jar target\autoloc-0.0.1-SNAPSHOT.jar
```

Le serveur démarre sur le port `8086` :

```text
http://localhost:8086
```

La base de données est créée automatiquement si elle n'existe pas :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/autoloc_db?createDatabaseIfNotExist=true
```

Hibernate utilise actuellement :

```properties
spring.jpa.hibernate.ddl-auto=update
```

Cette configuration permet à Hibernate de mettre à jour le schéma à partir des
entités pendant le développement. En production, utilisez plutôt Flyway ou
Liquibase et évitez `ddl-auto=update`.

## Architecture et cycle d'une requête

Le projet suit une architecture en couches :

```text
Requête HTTP
    |
    v
Contrôleur (web/controller)
    |
    v
Interface service (Service)
    |
    v
Implémentation service (Service/impls)
    |
    v
Repository (Repository)
    |
    v
JPA/Hibernate
    |
    v
Base de données MySQL
```

### 1. Couche entités/domain

Les classes du dossier
`src/main/java/tn/esprit/autoloc/domain` représentent les objets de
l'application et les tables de la base de données. `@Entity` indique à JPA
qu'une classe doit être persistée et `@Table` définit le nom de la table.

Par exemple, `Vehicule` est associé à la table `vehicule` :

```java
@Entity
@Table(name = "vehicule")
public class Vehicule {
    // champs
}
```

`@Id` et `@GeneratedValue(strategy = GenerationType.IDENTITY)` définissent une
clé primaire auto-incrémentée.

### 2. Couche repository

`IVehiculeRepository` étend `JpaRepository<Vehicule, Long>` :

```java
public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {
}
```

Spring Data crée automatiquement l'implémentation au démarrage. Cela fournit
notamment les méthodes suivantes :

- `save(vehicle)` : enregistrer ou modifier
- `findById(id)` : rechercher par identifiant
- `findAll()` : récupérer tous les éléments
- `deleteById(id)` : supprimer par identifiant
- `existsById(id)` : vérifier l'existence

Le repository est responsable de l'accès aux données. Les règles métier
doivent normalement rester dans le service, et non dans le contrôleur.

### 3. Couche service

`IVehiculeService` définit les opérations disponibles pour les véhicules :

```java
Vehicule findById(Long id);
Vehicule getById(Long id);
List<Vehicule> getAll();
Vehicule add(Vehicule vehicule);
Vehicule update(Long id, Vehicule vehicule);
void delete(Long id);
```

`VehiculeServiceImpl` implémente cette interface et reçoit le repository par
injection de constructeur :

```java
@Service
public class VehiculeServiceImpl implements IVehiculeService {
    private final IVehiculeRepository vehiculeRepository;

    public VehiculeServiceImpl(IVehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }
}
```

La méthode `add` actuelle délègue la sauvegarde au repository :

```java
public Vehicule add(Vehicule vehicule) {
    return vehiculeRepository.save(vehicule);
}
```

Les méthodes `getById`, `getAll` et `delete` sont encore des placeholders.
`update` sauvegarde actuellement l'objet fourni sans charger ni valider le
véhicule existant. Ces méthodes doivent être complétées avant d'exposer leurs
endpoints publiquement.

### 4. Couche web/controller

`VehiculeController` expose les endpoints HTTP sous le chemin
`/api/vehicules` :

```java
@RestController
@RequestMapping("/api/vehicules")
public class VehiculeController {
}
```

L'endpoint actuel est :

```java
@PostMapping("/add")
public Vehicule add(@RequestBody Vehicule vehicule) {
    return vehiculeService.add(vehicule);
}
```

Le contrôleur reçoit le JSON. Spring/Jackson le convertit en objet `Vehicule`,
puis le contrôleur appelle le service. Le service appelle le repository et JPA
insère finalement la ligne dans MySQL.

## API des véhicules

### Créer un véhicule

Requête :

```http
POST /api/vehicules/add
Content-Type: application/json
```

Exemple de corps JSON :

```json
{
  "immatriculation": "456 TUN 789",
  "marque": "Toyota",
  "modele": "Corolla",
  "categorie": "BERLINE",
  "tarifJournalier": 120.00,
  "statut": "DISPONIBLE"
}
```

Exemple avec curl :

```powershell
curl.exe -X POST "http://localhost:8086/api/vehicules/add" `
  -H "Content-Type: application/json" `
  -d '{\"immatriculation\":\"456 TUN 789\",\"marque\":\"Toyota\",\"modele\":\"Corolla\",\"categorie\":\"BERLINE\",\"tarifJournalier\":120.00,\"statut\":\"DISPONIBLE\"}'
```

La réponse contient le véhicule enregistré et son identifiant généré. Elle
peut aussi contenir des données liées si les relations ont été renseignées.

### Champs d'un véhicule

| Champ JSON | Type Java | Obligatoire en base | Description |
|---|---|---:|---|
| `idVehicule` | `Long` | Non | Clé primaire générée |
| `immatriculation` | `String` | Oui | Numéro d'immatriculation unique |
| `marque` | `String` | Oui | Marque |
| `modele` | `String` | Oui | Modèle |
| `categorie` | `CategorieVehicule` | Oui | Catégorie |
| `tarifJournalier` | `BigDecimal` | Oui | Tarif de location journalier |
| `statut` | `StatutVehicule` | Oui | État actuel du véhicule |
| `agence` | `agence` | Non | Agence affectée |
| `equipements` | `List<Equipement>` | Non | Équipements affectés |
| `reservations` | `List<Reservation>` | Non | Réservations |
| `maintenances` | `List<Maintenance>` | Non | Opérations de maintenance |

Ne transmettez pas `idVehicule` lors de la création : il est généré par la
base de données.

### Valeurs autorisées des enums

`categorie` :

```text
CITADINE, BERLINE, SUV, UTILITAIRE
```

`statut` :

```text
DISPONIBLE, LOUE, MAINTENANCE
```

Les valeurs sont sensibles à la casse et doivent respecter exactement ces
noms.

## API des employés

Les employés sont gérés sous le chemin `/api/employes`. Un employé possède un
identifiant, un rôle (`AGENT` ou `MANAGER`) et peut être affecté à une agence.

### Endpoints employés

```http
POST   /api/employes/add
GET    /api/employes
GET    /api/employes/{id}
PUT    /api/employes/{id}
DELETE /api/employes/{id}
```

Créer un employé :

```json
{
  "role": "AGENT",
  "agence": {
    "idagence": 1
  }
}
```

Modifier un employé :

```http
PUT http://localhost:8086/api/employes/1
Content-Type: application/json
```

```json
{
  "role": "MANAGER",
  "agence": {
    "idagence": 1
  }
}
```

Le service charge d'abord l'employé existant avec l'identifiant présent dans
l'URL, modifie son rôle et son agence, puis sauvegarde l'entité. L'identifiant
`idEmploye` est généré automatiquement et ne doit pas être fourni lors d'une
création.

La suppression renvoie `204 No Content` si elle réussit. Si l'identifiant
n'existe pas, le service lève une erreur `Employee not found: {id}`.

## API des agences, clients et contrats

Les trois ressources suivantes utilisent le même fonctionnement CRUD que les
véhicules et les employés :

```text
Agences  : /api/agences
Clients  : /api/clients
Contrats : /api/contrats
```

Pour chaque ressource, les endpoints disponibles sont :

```http
POST   /api/{ressource}/add
GET    /api/{ressource}
GET    /api/{ressource}/{id}
PUT    /api/{ressource}/{id}
DELETE /api/{ressource}/{id}
```

### Exemple d'agence

```json
{
  "nom": "Agence Centre",
  "ville": "Tunis",
  "adresse": "10 avenue Habib Bourguiba",
  "telephone": "71123456"
}
```

Endpoint :

```http
POST http://localhost:8086/api/agences/add
```

### Exemple de client

```json
{
  "nom": "Ben Ali",
  "prenom": "Ahmed",
  "email": "ahmed.benali@example.com",
  "telephone": "22123456",
  "numPermis": "PERMIS-12345",
  "dateInscription": "2026-10-25"
}
```

Endpoint :

```http
POST http://localhost:8086/api/clients/add
```

L'email et le numéro de permis doivent être uniques.

### Exemple de contrat

```json
{
  "dateSignature": "2026-10-25",
  "montantTotal": 850.00,
  "valide": true,
  "reservation": {
    "idReservation": 1
  }
}
```

Endpoint :

```http
POST http://localhost:8086/api/contrats/add
```

Une réservation ne peut être liée qu'à un seul contrat, car la relation
contrat-réservation possède une clé étrangère unique. Pour une modification,
le service charge d'abord l'entité existante avec l'identifiant de l'URL, puis
met à jour ses champs avant d'appeler le repository. La suppression renvoie
`204 No Content` si elle réussit.

### Afficher tous les véhicules

```http
GET /api/vehicules
```

La réponse contient une liste JSON de tous les véhicules enregistrés.

### Afficher un véhicule

```http
GET /api/vehicules/{id}
```

Exemple :

```text
GET http://localhost:8086/api/vehicules/1
```

Si l'identifiant n'existe pas, le service lève une erreur
`Vehicle not found: {id}`. Un gestionnaire global d'exceptions devrait être
ajouté pour la convertir en réponse HTTP 404.

### Modifier un véhicule

```http
PUT /api/vehicules/{id}
Content-Type: application/json
```

Exemple :

```json
{
  "immatriculation": "456 TUN 789",
  "marque": "Toyota",
  "modele": "Corolla Hybrid",
  "categorie": "BERLINE",
  "tarifJournalier": 135.00,
  "statut": "DISPONIBLE"
}
```

Exemple :

```text
PUT http://localhost:8086/api/vehicules/1
```

Le service charge d'abord le véhicule avec l'identifiant fourni, met à jour
ses champs principaux, puis sauvegarde l'entité existante. L'identifiant dans
l'URL est utilisé comme référence ; il n'est pas nécessaire de transmettre
`idVehicule` dans le JSON. Les champs modifiés sont l'immatriculation, la
marque, le modèle, la catégorie, le tarif, le statut, l'agence et les
équipements.

L'immatriculation doit rester unique. Modifier un véhicule avec
l'immatriculation d'un autre véhicule provoquera une erreur de contrainte
d'unicité.

### Supprimer un véhicule

```http
DELETE /api/vehicules/{id}
```

Exemple :

```text
DELETE http://localhost:8086/api/vehicules/1
```

En cas de succès, l'API renvoie le statut `204 No Content`. Si le véhicule
n'existe pas, une erreur `Vehicle not found: {id}` est levée. La suppression
peut aussi être refusée par MySQL si le véhicule est encore référencé par des
réservations, des maintenances ou la table de jointure des équipements ; ces
relations doivent alors être traitées selon les règles métier de l'application.

## Relations entre les entités

### Agence et véhicules : un-à-plusieurs

Une agence peut posséder plusieurs véhicules :

```text
agence 1 -------- * vehicule
```

Dans `agence` :

```java
@OneToMany(mappedBy = "agence")
private List<Vehicule> vehicules;
```

Dans `Vehicule` :

```java
@ManyToOne
private agence agence;
```

`Vehicule.agence` est le côté propriétaire car il contient la clé étrangère.
Hibernate crée une colonne similaire à `agence_idagence` dans la table
`vehicule`. `mappedBy = "agence"` indique que la relation est gérée par le
champ `agence` de `Vehicule`.

Le même principe est utilisé pour les agences et les employés :

```text
agence 1 -------- * employe
```

### Véhicules et réservations : un-à-plusieurs

Un véhicule peut apparaître dans plusieurs réservations et chaque réservation
concerne un seul véhicule :

```text
vehicule 1 -------- * reservation
```

`Reservation` est le côté propriétaire :

```java
@ManyToOne
@JoinColumn(name = "id_vehicule", nullable = false)
private Vehicule vehicule;
```

La clé étrangère est `reservation.id_vehicule`. La collection inverse dans
`Vehicule` utilise `mappedBy = "vehicule"`.

### Client et réservations : un-à-plusieurs

Un client peut effectuer plusieurs réservations :

```text
client 1 -------- * reservation
```

La clé étrangère est `reservation.id_client` et le champ propriétaire est
`Reservation.client`.

### Réservation et contrat : un-à-un

Une réservation peut avoir un contrat et un contrat appartient à une seule
réservation :

```text
reservation 1 -------- 1 contrat
```

`Contrat` est le côté propriétaire :

```java
@OneToOne
@JoinColumn(name = "id_reservation", nullable = false, unique = true)
private Reservation reservation;
```

`unique = true` empêche de relier la même réservation à plusieurs contrats.

### Contrat et paiements : un-à-plusieurs

Un contrat peut contenir plusieurs paiements :

```text
contrat 1 -------- * paiement
```

La clé étrangère est `paiement.id_contrat` et le champ propriétaire est
`Paiement.contrat`.

### Véhicule et maintenance : un-à-plusieurs

Un véhicule peut avoir plusieurs opérations de maintenance :

```text
vehicule 1 -------- * maintenance
```

La clé étrangère est `maintenance.id_vehicule` et le champ propriétaire est
`Maintenance.vehicule`.

### Véhicules et équipements : plusieurs-à-plusieurs

Un véhicule peut avoir plusieurs équipements et un équipement peut appartenir à
plusieurs véhicules :

```text
vehicule * -------- * equipement
```

Cette relation utilise une table de jointure :

```text
vehicule_equipement
    id_vehicule
    id_equipement
```

`Vehicule` définit la table de jointure avec `@JoinTable`, donc c'est le côté
propriétaire. `Equipement` utilise `mappedBy = "equipements"` et représente
le côté inverse.

### Vue globale du domaine

```text
agence
  | 1
  |------ * vehicule
  |------ * employe

client
  | 1
  |------ * reservation * ------ 1 vehicule
                    |
                    | 1
                    |------ 1 contrat
                              |
                              | 1
                              |------ * paiement

vehicule
  | 1
  |------ * maintenance
  |
  *------ * equipement
```

## Contraintes et erreurs fréquentes

La plupart des champs utilisent `nullable = false`. Une valeur obligatoire
manquante peut donc provoquer une erreur de contrainte en base. Les valeurs
suivantes sont uniques :

- `Vehicule.immatriculation`
- `Client.email`
- `Client.numPermis`
- `Contrat.reservation`, grâce à sa clé étrangère unique

Créer un deuxième véhicule avec
`"immatriculation": "123 TUN 456"` provoque :

```text
Duplicate entry '123 TUN 456'
```

C'est normal car une immatriculation identifie un seul véhicule. Utilisez une
autre immatriculation ou vérifiez l'existence du véhicule avant d'appeler
`save`.

Le contrôleur actuel ne transforme pas encore les exceptions de base de
données en réponses HTTP personnalisées. Cette erreur peut donc être renvoyée
en HTTP 500. Une API de production devrait utiliser la validation et un
gestionnaire global d'exceptions afin de retourner, par exemple, HTTP 409
Conflict.

## Lombok

Les entités utilisent les annotations Lombok suivantes :

- `@Getter` et `@Setter` génèrent les accesseurs
- `@NoArgsConstructor` génère le constructeur sans argument requis par JPA
- `@AllArgsConstructor` génère un constructeur avec tous les champs

Vérifiez que le traitement des annotations est activé dans l'IDE. Maven est
configuré pour utiliser Lombok comme processeur d'annotations.

## Configuration

Les propriétés importantes de `application.properties` sont :

| Propriété | Valeur actuelle | Rôle |
|---|---|---|
| `server.port` | `8086` | Port HTTP |
| `spring.datasource.url` | URL MySQL | Connexion à la base |
| `spring.datasource.username` | `root` | Utilisateur MySQL |
| `spring.datasource.password` | vide | Mot de passe MySQL |
| `spring.jpa.hibernate.ddl-auto` | `update` | Met à jour le schéma depuis les entités |
| `spring.jpa.show-sql` | `true` | Affiche les requêtes SQL |
| `spring.jpa.properties.hibernate.format_sql` | `true` | Formate les requêtes SQL |

Les logs peuvent afficher un avertissement si MySQL signale une ancienne
version. Cet avertissement est indépendant des erreurs de démarrage ou
d'insertion d'un véhicule.

## Tests

Lancer les tests :

```powershell
.\mvnw.cmd test
```

Le test actuel vérifie que le contexte Spring se charge correctement. Il faut
encore ajouter des tests pour la création d'un véhicule, les immatriculations
dupliquées, les relations et la validation.

## Améliorations recommandées

1. Compléter `getById`, `getAll`, `update` et `delete` dans
   `VehiculeServiceImpl`.
2. Ajouter les endpoints `GET`, `PUT` et `DELETE` pour les véhicules.
3. Ajouter les contrôleurs, services et repositories des autres entités.
4. Utiliser des DTO au lieu d'exposer directement les entités JPA en JSON.
5. Ajouter des annotations de validation comme `@NotBlank`, `@NotNull`,
   `@Positive` et `@Email`.
6. Ajouter un gestionnaire global pour les erreurs de validation, de
   ressource introuvable et de données dupliquées.
7. Éviter les boucles JSON causées par les relations bidirectionnelles avec
   des DTO ou une configuration Jackson adaptée.
8. Ajouter des migrations de base de données et des identifiants configurés
   par environnement avant un déploiement en production.
