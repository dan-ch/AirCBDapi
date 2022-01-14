package com.example.api.controller;

import com.example.api.exception.IllegalProcessingException;
import com.example.api.model.Opinion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@IllegalProcessingException
@RequestMapping("/opinion")
@CrossOrigin(origins = "*")
public class OpinionController {

    @PostMapping
    public ResponseEntity<?> addOpinion(@Valid Opinion opinion){
        System.out.println("halo");
        return null;
    }
}
