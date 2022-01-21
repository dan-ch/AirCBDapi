package com.example.api.repository;

import com.example.api.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByOffer_IdAndStartDateGreaterThanEqual(Long offerId, LocalDate date);

    List<Reservation> findByOwnerEmail(String email);
}
