package com.example.api.controller;


import com.example.api.exception.IllegalProcessingException;
import com.example.api.model.Reservation;
import com.example.api.repository.UserRepository;
import com.example.api.service.ReservationService;
import com.example.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "*")
@IllegalProcessingException
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public ReservationController(ReservationService reservationService, UserService userService) {
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<?> addReservation(@Valid Reservation reservation, Principal principal){
        reservation.setOwner(userRepository.getById(1L)); //TODO powienien byc principal
        reservationService.addReservation(reservation);
        return null;
    }
}
