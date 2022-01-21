package com.example.api.service;

import com.example.api.exception.ResourceNotFoundException;
import com.example.api.model.Reservation;
import com.example.api.model.User;
import com.example.api.repository.ReservationRepository;
import com.example.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public UserService(UserRepository userRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public Boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<Reservation> getUserReservations(String userEmail){
        return reservationRepository.findByOwnerEmail(userEmail);
    }

}
