package com.example.api.service;

import com.example.api.model.Offer;
import com.example.api.model.Opinion;
import com.example.api.model.Reservation;
import com.example.api.model.User;
import com.example.api.repository.OfferRepository;
import com.example.api.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final ReservationRepository reservationRepository;
    private final DateService dateService;

    @Autowired
    public OfferService(OfferRepository offerRepository, ReservationRepository reservationRepository, DateService dateService) {
        this.offerRepository = offerRepository;
        this.reservationRepository = reservationRepository;
        this.dateService = dateService;
    }

    public List<Offer> getAvailableOffersByCityName(String city, LocalDate startDate, LocalDate endDate) {
        List<Offer> offers = offerRepository.findAllByCityAndStatus(city, Offer.OfferStatus.ACTIVE);

        return filterAvailableOffers(offers, startDate, endDate);
    }

    public List<Offer> getAvailableOffersByCityNameAndPeople(String city, Integer people, LocalDate startDate,
                                                             LocalDate endDate) {
        List<Offer> offers = offerRepository.findAllByCityAndStatusAndMaxPeopleGreaterThanEqual(
                city, Offer.OfferStatus.ACTIVE, people);

        return filterAvailableOffers(offers, startDate, endDate);
    }

    public Offer getOffer(Long offerId) {
        Optional<Offer> optionalOffer = offerRepository.findById(offerId);
        return optionalOffer.orElseThrow(() -> new IllegalArgumentException("Offer not Found"));
    }

    public Offer getOfferWithRatings(Long offerId) {
        Optional<Offer> optionalOffer = offerRepository.findById(offerId);
        if(optionalOffer.isPresent()){
            Offer offer = optionalOffer.get();
            offer.setRatings(offerRepository.getRatingsByOfferId(offerId));
            return offer;
        }
        throw new IllegalArgumentException("Offer not Found");
    }

    public User getOfferOwner(Long offerId) {
        Optional<Offer> optionalOffer = offerRepository.findById(offerId);
        if(optionalOffer.isPresent())
            return optionalOffer.get().getOwner();
        throw new IllegalArgumentException("Offer not Found");
    }

    public List<Reservation> getOfferReservations(Long offerId, Boolean current){
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new IllegalArgumentException("Offer not Found"));
        if(current)
            return reservationRepository.findByOffer_IdAndStartDateGreaterThanEqual(offerId, LocalDate.now());
        return offer.getReservations();
    }

    public List<Opinion> getOfferOpinions(Long offerId) {
        Optional<Offer> optionalOffer = offerRepository.findById(offerId);
        if(optionalOffer.isPresent())
            return optionalOffer.get().getOpinions();
        throw new IllegalArgumentException("Offer not Found");
    }

    public Offer addOffer(Offer offer){
        offer.setStatus(Offer.OfferStatus.ACTIVE);
        return offerRepository.save(offer);
    }

    public void editOffer(Offer requestedOffer){
        offerRepository.findById(requestedOffer.getId()).map(offer -> {
            offer.setTitle(requestedOffer.getTitle());
            offer.setCity(requestedOffer.getCity());
            offer.setMaxPeople(requestedOffer.getMaxPeople());
            offer.setDailyPrice(requestedOffer.getDailyPrice());
            return offerRepository.save(offer);
        }).orElseThrow(() -> new IllegalArgumentException("Offer not found"));
    }

    public void deleteOffer(Long offerId){
        offerRepository.deleteById(offerId);
    }

    private List<Offer> filterAvailableOffers(List<Offer> offers, LocalDate startDate, LocalDate endDate){
        return offers.stream()
                .filter(offer -> {
                            Long reservationsCount = offer.getReservations().stream()
                                    .filter(reservation -> dateService.isReservationDateRangeCollideWithDateRange(
                                            reservation.getStartDate(), reservation.getEndDate(), startDate, endDate))
                                    .count();
                            return reservationsCount.equals(0L);
                        }
                ).collect(Collectors.toList());
    }

}


//        System.out.println(isOfferAvailable(
//                reservations.get(0).getStartDate(),
//                reservations.get(0).getEndDate(), LocalDate.of(2021, 3, 11),
//                LocalDate.of(2021, 3, 14)
//        ));//true
//        System.out.println(isOfferAvailable(
//                reservations.get(0).getStartDate(),
//                reservations.get(0).getEndDate(), LocalDate.of(2021, 3, 11),
//                LocalDate.of(2021, 3, 17)
//        ));//false
//        System.out.println(isOfferAvailable(
//                reservations.get(0).getStartDate(),
//                reservations.get(0).getEndDate(), LocalDate.of(2021, 3, 16),
//                LocalDate.of(2021, 3, 17)
//        ));//false
//        System.out.println(isOfferAvailable(
//                reservations.get(0).getStartDate(),
//                reservations.get(0).getEndDate(), LocalDate.of(2021, 3, 17),
//                LocalDate.of(2021, 3, 21)
//        ));//false
//        System.out.println(isOfferAvailable(
//                reservations.get(0).getStartDate(),
//                reservations.get(0).getEndDate(), LocalDate.of(2021, 3, 19),
//                LocalDate.of(2021, 3, 20)
//        ));//true
//        System.out.println(System.lineSeparator());
//        System.out.println(isOfferAvailable(
//                reservations.get(0).getStartDate(),
//                reservations.get(0).getEndDate(), LocalDate.of(2021, 3, 12),
//                LocalDate.of(2021, 3, 15)
//        ));//true
//        System.out.println(isOfferAvailable(
//                reservations.get(0).getStartDate(),
//                reservations.get(0).getEndDate(), LocalDate.of(2021, 3, 15),
//                LocalDate.of(2021, 3, 20)
//        ));//true
//        System.out.println(isOfferAvailable(
//                reservations.get(0).getStartDate(),
//                reservations.get(0).getEndDate(), LocalDate.of(2021, 3, 15),
//                LocalDate.of(2021, 3, 18)
//        ));//true
//        System.out.println(isOfferAvailable(
//                reservations.get(0).getStartDate(),
//                reservations.get(0).getEndDate(), LocalDate.of(2021, 3, 12),
//                LocalDate.of(2021, 3, 18)
//        ));//true
//        System.out.println(isOfferAvailable(
//                reservations.get(0).getStartDate(),
//                reservations.get(0).getEndDate(), LocalDate.of(2021, 3, 11),
//                LocalDate.of(2021, 3, 15)
//        ));//true
//        //2021-03-15
//        //2021-03-18