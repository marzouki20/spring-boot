package tn.esprit.autoloc.Service;

import tn.esprit.autoloc.domain.Reservation;

import java.util.List;

public interface IReservationService {

    Reservation findById(Long id);

    Reservation getById(Long id);

    List<Reservation> getAll();

    Reservation add(Reservation reservation);

    Reservation update(Long id, Reservation reservation);

    void delete(Long id);
}
