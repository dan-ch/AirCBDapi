package com.example.api.service;

import com.example.api.exception.ResourceNotFoundException;
import com.example.api.model.*;
import com.example.api.repository.OfferRepository;
import com.example.api.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final ReservationRepository reservationRepository;
    private final DateService dateService;
    private final PhotoUploadService photoUploadService;

    @Autowired
    public OfferService(OfferRepository offerRepository, ReservationRepository reservationRepository, DateService dateService, PhotoUploadService photoUploadService) {
        this.offerRepository = offerRepository;
        this.reservationRepository = reservationRepository;
        this.dateService = dateService;
        this.photoUploadService = photoUploadService;
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
        return optionalOffer.orElseThrow(() -> new ResourceNotFoundException("Offer not Found"));
    }

    public Offer getOfferWithRatings(Long offerId) {
        Offer offer = getOffer(offerId);
        offer.setRatings(offerRepository.getRatingsByOfferId(offerId));
        return offer;
    }

    public User getOfferOwner(Long offerId) {
        Offer offer = getOffer(offerId);
        return offer.getOwner();
    }

    public List<Reservation> getOfferReservations(Long offerId, Boolean current){
        if(current)
            return reservationRepository.findByOffer_IdAndStartDateGreaterThanEqual(offerId, LocalDate.now());
        return getOffer(offerId).getReservations();
    }

    public List<Opinion> getOfferOpinions(Long offerId) {
        Offer offer = getOffer(offerId);
        return offer.getOpinions();
    }

    public Offer addOffer(Offer offer, MultipartFile[] photos){
        List<Image> images = Arrays.stream(photos).map(photo -> {
            try {
                Image image = photoUploadService.uploadPhoto(photo);
                image.setOffer(offer);
                return image;
            } catch (IOException e) {
                throw new RuntimeException("Photo upload error");
            }
        }).collect(Collectors.toList());
        offer.setMainImage(images.get(0));
        offer.setImages(images.subList(1, images.size()));
        offer.setStatus(Offer.OfferStatus.ACTIVE);
        return offerRepository.save(offer);
    }

    public Offer editOffer(Offer requestedOffer, User principal){
        Offer offer = getOffer(requestedOffer.getId());
        if(offer.getOwner()==principal){
            offer.setTitle(requestedOffer.getTitle());
            offer.setCity(requestedOffer.getCity());
            offer.setMaxPeople(requestedOffer.getMaxPeople());
            offer.setDailyPrice(requestedOffer.getDailyPrice());
            offer.setDescription(requestedOffer.getDescription());
            return offerRepository.save(offer);
        }
        else
            throw new IllegalStateException("No permission to perform action");
    }

    public void deleteOffer(Long offerId, User principal){
        Offer offer = getOffer(offerId);
        offer.getImages().stream()
            .forEach(image -> {
                try {
                    photoUploadService.deletePhoto(image.getCloudId());
                } catch (Exception e) {
                    throw new RuntimeException("Photo deleting error");
                }
            });
        if(offer.getOwner()==principal)
            offerRepository.deleteById(offerId);
        else
            throw new IllegalStateException("No permission to perform action");
    }

    public List<String> getOfferCities(){
        return offerRepository.getCities();
    }

    public List<Offer> getNewestOffers(){
        return offerRepository.getNewest();
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