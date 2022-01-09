package com.example.api.controller;

import com.example.api.exception.IllegalProcessingException;
import com.example.api.model.Offer;
import com.example.api.model.Opinion;
import com.example.api.model.Reservation;
import com.example.api.model.User;
import com.example.api.service.OfferService;
import com.example.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@IllegalProcessingException
@RequestMapping("/offer")
public class OfferController {

    private final OfferService offerService;
    private final UserService userService;

    @Autowired
    public OfferController(OfferService offerService, UserService userService) {
        this.offerService = offerService;
        this.userService = userService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Offer>> findOffersByCityAndPeople(
            @RequestParam String city,
            @RequestParam Integer people,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        List<Offer> offers = offerService.getAvailableOffersByCityNameAndPeople(city, people, startDate, endDate);
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOffer(@PathVariable Long id,
                                          @RequestParam(defaultValue = "false") Boolean withRatings){
        if(withRatings)
            return ResponseEntity.ok(offerService.getOfferWithRatings(id));
        return ResponseEntity.ok(offerService.getOffer(id));
    }

    @GetMapping("/{id}/owner")
    public ResponseEntity<User> getOfferOwner(@PathVariable Long id){
        return ResponseEntity.ok(offerService.getOfferOwner(id));
    }

    @GetMapping("/{id}/reservations")
    public ResponseEntity<List<Reservation>> getOfferReservations(@PathVariable Long id,
                                                                  @RequestParam(defaultValue = "true") Boolean current){
       return ResponseEntity.ok(offerService.getOfferReservations(id, current));
    }

    @GetMapping("/{id}/opinions")
    public ResponseEntity<List<Opinion>> getOfferOpinions(@PathVariable Long id){
        return ResponseEntity.ok(offerService.getOfferOpinions(id));
    }

    @PostMapping()
    public ResponseEntity<?> addOffer(@Valid Offer offer, Principal principal){
        offer.setOwner(userService.getUserByEmail(principal.getName())); //TODO zmienić na principal
        Offer savedOffer = offerService.addOffer(offer);
        return ResponseEntity.created(URI.create("/offer/" + savedOffer.getId())).build();
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, path = "/{id}")
    public ResponseEntity<?> updateOffer(@Valid Offer offer, @PathVariable Long id, Principal principal){
        offer.setId(id);
        offerService.editOffer(offer, userService.getUserByEmail(principal.getName()));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable Long id, Principal principal){
        offerService.deleteOffer(id, userService.getUserByEmail(principal.getName()));
        return ResponseEntity.noContent().build();
    }

}
