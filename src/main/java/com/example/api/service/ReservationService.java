package com.example.api.service;

import com.example.api.exception.ResourceNotFoundException;
import com.example.api.model.Reservation;
import com.example.api.model.User;
import com.example.api.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final DateService dateService;
    private final UserService userService;

    public ReservationService(ReservationRepository reservationRepository, DateService dateService,
                              UserService userService) {
        this.reservationRepository = reservationRepository;
        this.dateService = dateService;
        this.userService = userService;
    }

    public Reservation getReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
            .orElseThrow(() -> new ResourceNotFoundException("Reservation not Found"));
    }


    public Reservation addReservation(Reservation reservation, User principal){
        long reservationsCount = reservation.getOffer().getReservations().stream()
            .filter(offerReservation -> dateService.isReservationDateRangeCollideWithDateRange(
                offerReservation.getStartDate(), offerReservation.getEndDate(),
                reservation.getStartDate(), reservation.getEndDate()))
            .count();
        if(reservationsCount == 0L){
            reservation.setOwner(principal);
            int days = (int) DAYS.between(reservation.getStartDate(), reservation.getEndDate()) + 1;
            reservation.setPrice(reservation.getOffer().getDailyPrice() * days);
            reservation.setStatus(Reservation.ReservationStatus.PAID);
            return reservationRepository.save(reservation);
        }
        throw new IllegalStateException("Cannot add reservation due to date range");
    }

    public void deleteReservation(Long reservationId, User principal){
        Reservation reservation = getReservation(reservationId);

        if(reservation.getStartDate().isBefore(LocalDate.now()))
            throw new IllegalStateException("Reservation cannot be deleted");

        if(reservation.getOwner()==principal)
            reservationRepository.deleteById(reservationId);
        else
            throw new IllegalStateException("No permission to perform action");
    }
}
