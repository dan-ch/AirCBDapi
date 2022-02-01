package com.example.api.controller;

import com.example.api.exception.IllegalProcessingException;
import com.example.api.model.Offer;
import com.example.api.service.OfferService;
import com.example.api.service.PhotoUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin(origins = "*")
@IllegalProcessingException
public class HomeController {

  private final OfferService offerService;

  @Autowired
  private PhotoUploadService photoUploadService;

  public HomeController(OfferService offerService) {
    this.offerService = offerService;
  }

  @GetMapping("/cities")
  public ResponseEntity<List<String>> getCities(){
    return ResponseEntity.ok()
        .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
        .body(offerService.getOfferCities());
  }

  @GetMapping("/newest")
  public ResponseEntity<List<Offer>> getNewOffers(){
    return ResponseEntity.ok(offerService.getNewestOffers());
  }


//  @GetMapping("/upload")
//  public ResponseEntity<?> upload() throws IOException {
//    String path = fileUploadService.uploadFile("folder");
//    return ResponseEntity.ok(path);
//  }
//
//  @PostMapping("/upload")
//  public void upload(@Valid Offer offer, @RequestParam MultipartFile[] photos) throws IOException {
//    Image image = photoUploadService.uploadPhoto(photos[0]);
//    System.out.println("halo");
//  }
//
  @GetMapping("/delete")
  public void delete() throws Exception {
    photoUploadService.deletePhoto("sda");
  }


//    private UserRepository userRepository;
//    private OfferRepository offerRepository;
//    private ReservationRepository reservationRepository;
//    private OpinionRepository opinionRepository;
//
//    @Autowired
//    public HomeController(UserRepository userRepository, OfferRepository offerRepository,
//                          ReservationRepository reservationRepository, OpinionRepository opinionRepository) {
//        this.userRepository = userRepository;
//        this.offerRepository = offerRepository;
//        this.reservationRepository = reservationRepository;
//        this.opinionRepository = opinionRepository;
//    }
//
//    @GetMapping("/fakeData")
//    public String home(){
//        Faker faker = new Faker(new Locale("pl"));
//        Random random  = new Random();
//        for (int i = 0; i < 10; i++) {
//            User user = new User();
//            user.setFirstName(faker.name().firstName());
//            user.setLastName(faker.name().lastName());
//            user.setPassword(faker.beer().style());
//            user.setEmail(faker.internet().emailAddress());
//            userRepository.save(user);
//        }
//        for (int i = 0; i < 30; i++) {
//            Offer offer = new Offer();
//            offer.setId((long) i + 1);
//            offer.setCity(faker.address().cityName());
//            offer.setDailyPrice(random.nextInt(1000) + random.nextDouble());
//            offer.setMaxPeople(random.nextInt(10) + 1);
//            offer.setStatus(Offer.OfferStatus.ACTIVE);
//            offer.setTitle(faker.book().title());
//            List<Reservation> reservations = new ArrayList<>();
//            for (int j = 0; j < random.nextInt(10)+10; j++) {
//                Reservation reservation = new Reservation();
//                Reservation.ReservationStatus status;
//                if(j % 3 == 0)
//                    status = Reservation.ReservationStatus.NEW;
//                else if(j % 3 == 1)
//                    status = Reservation.ReservationStatus.CONFIRMED;
//                else
//                    status = Reservation.ReservationStatus.PAID;
//                reservation.setStatus(status);
//                reservation.setPrice((double) (random.nextInt(10000)+ 1000));
//                reservation.setOffer(offer);
//                Month month = Month.of(random.nextInt(12)+1);
//                int day = random.nextInt(20) + 1;
//                reservation.setStartDate(LocalDate.of(2021, month, day));
//                reservation.setEndDate(LocalDate.of(2021, month, day + random.nextInt(4)+1));
//                reservations.add(reservation);
//            }
//            List<Opinion> opinions = new ArrayList<>();
//            for (int j = 0; j < random.nextInt(10)+10; j++){
//                Opinion opinion = new Opinion();
//                opinion.setOffer(offer);
//                opinion.setContent(faker.lorem().characters(100, 500));
//                opinion.setRate(random.nextInt(5)+1);
//                Month month = Month.of(random.nextInt(12)+1);
//                opinion.setDate(LocalDate.of(2021, month, random.nextInt(28) + 1));
//                opinions.add(opinion);
//            }
//            offer.setOpinions(opinions);
//            offer.setReservations(reservations);
//            offerRepository.save(offer);
//            reservationRepository.saveAll(reservations);
//            opinionRepository.saveAll(opinions);
//        }
//        for (int i = 0; i < 10; i++) {
//            Offer offer = new Offer();
//            offer.setId(31L+i);
//            offer.setCity(faker.address().cityName());
//            offer.setDailyPrice(random.nextInt(1000) + random.nextDouble());
//            offer.setMaxPeople(random.nextInt(10) + 1);
//            offer.setStatus(Offer.OfferStatus.INACTIVE);
//            offer.setTitle(faker.book().title());
//            List<Reservation> reservations = new ArrayList<>();
//            for (int j = 0; j < random.nextInt(10)+10; j++) {
//                Reservation reservation = new Reservation();
//                Reservation.ReservationStatus status;
//                if(j % 3 == 0)
//                    status = Reservation.ReservationStatus.NEW;
//                else if(j % 3 == 1)
//                    status = Reservation.ReservationStatus.CONFIRMED;
//                else
//                    status = Reservation.ReservationStatus.PAID;
//                reservation.setStatus(status);
//                reservation.setPrice((double) (random.nextInt(10000)+ 1000));
//                reservation.setOffer(offer);
//                Month month = Month.of(random.nextInt(12)+1);
//                int day = random.nextInt(20) + 1;
//                reservation.setStartDate(LocalDate.of(2021, month, day));
//                reservation.setEndDate(LocalDate.of(2021, month, day + random.nextInt(4)+1));
//                reservations.add(reservation);
//            }
//            List<Opinion> opinions = new ArrayList<>();
//            for (int j = 0; j < random.nextInt(10)+10; j++){
//                Opinion opinion = new Opinion();
//                opinion.setOffer(offer);
//                opinion.setContent(faker.lorem().characters(100, 500));
//                opinion.setRate(random.nextInt(5)+1);
//                Month month = Month.of(random.nextInt(12)+1);
//                opinion.setDate(LocalDate.of(2021, month, random.nextInt(28) + 1));
//                opinions.add(opinion);
//            }
//            offer.setOpinions(opinions);
//            offer.setReservations(reservations);
//            offerRepository.save(offer);
//            reservationRepository.saveAll(reservations);
//            opinionRepository.saveAll(opinions);
//        }
//
//        return "done";
//    }
}