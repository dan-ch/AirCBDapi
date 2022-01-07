package com.example.api.controller;

import com.example.api.model.Opinion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/opinion")
public class OpinionController {

    @PostMapping
    public ResponseEntity<?> addOpinion(@Valid Opinion opinion){
        System.out.println("halo");
        return null;
    }
}
