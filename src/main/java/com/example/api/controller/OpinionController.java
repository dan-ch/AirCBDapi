package com.example.api.controller;

import com.example.api.exception.IllegalProcessingException;
import com.example.api.model.Opinion;
import com.example.api.service.OpinionService;
import com.example.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@IllegalProcessingException
@RequestMapping("/opinion")
@CrossOrigin
public class OpinionController {

    private final OpinionService opinionService;
    private final UserService userService;

    @Autowired
    public OpinionController(OpinionService opinionService, UserService userService) {
        this.opinionService = opinionService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> addOpinion(@Valid Opinion opinion, Principal principal){
        opinion.setAuthor(userService.getUserByEmail(principal.getName()));
        opinionService.addOpinion(opinion);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOpinion(@PathVariable Long id, Principal principal){
        opinionService.deleteOpinion(id, userService.getUserByEmail(principal.getName()));
        return ResponseEntity.noContent().build();
    }
}
