package com.example.api.service;

import com.example.api.model.Reservation;
import com.example.api.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;


    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }


    public Reservation addReservation(Reservation reservation){
        System.out.println("ss");
        return null;
    }
}
