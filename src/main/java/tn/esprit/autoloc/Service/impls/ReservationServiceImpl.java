package tn.esprit.autoloc.Service.impls;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Repository.IReservationRepository;
import tn.esprit.autoloc.Service.IReservationService;
import tn.esprit.autoloc.domain.Reservation;

import java.util.List;

@Service
public class ReservationServiceImpl implements IReservationService {
    private final IReservationRepository reservationRepository;

    public ReservationServiceImpl(IReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found: " + id));
    }

    @Override
    public Reservation getById(Long id) {
        return findById(id);
    }

    @Override
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation add(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation update(Long id, Reservation reservation) {
        Reservation existingReservation = findById(id);
        existingReservation.setDateDebut(reservation.getDateDebut());
        existingReservation.setDateFin(reservation.getDateFin());
        existingReservation.setStatut(reservation.getStatut());
        existingReservation.setVehicule(reservation.getVehicule());
        existingReservation.setClient(reservation.getClient());

        return reservationRepository.save(existingReservation);
    }

    @Override
    public void delete(Long id) {
        Reservation existingReservation = findById(id);
        reservationRepository.delete(existingReservation);
    }
}
