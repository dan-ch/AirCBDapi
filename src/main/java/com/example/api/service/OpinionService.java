package com.example.api.service;

import com.example.api.exception.ResourceNotFoundException;
import com.example.api.model.Offer;
import com.example.api.model.Opinion;
import com.example.api.model.Reservation;
import com.example.api.model.User;
import com.example.api.repository.OpinionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class OpinionService {

  private final OpinionRepository opinionRepository;

  @Autowired
  public OpinionService(OpinionRepository opinionRepository) {
    this.opinionRepository = opinionRepository;
  }

  public Opinion getOpinion(Long opinionId) {
    return opinionRepository.findById(opinionId)
        .orElseThrow(() -> new ResourceNotFoundException("Offer not Found"));
  }


  public Opinion addOpinion(Opinion opinion){
    opinion.setDate(LocalDate.now());
    return opinionRepository.save(opinion);
  }

  public void deleteOpinion(Long opinionId, User principal){
    Opinion opinion = getOpinion(opinionId);

    if(opinion.getAuthor()==principal)
      opinionRepository.deleteById(opinionId);
    else
      throw new IllegalStateException("No permission to perform action");
  }
}
