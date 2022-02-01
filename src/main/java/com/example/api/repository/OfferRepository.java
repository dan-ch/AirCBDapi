package com.example.api.repository;

import com.example.api.model.Offer;
import com.example.api.dto.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findAllByCityAndStatus(String city, Offer.OfferStatus status);

    List<Offer> findAllByCityAndStatusAndMaxPeopleGreaterThanEqual(String city, Offer.OfferStatus status, Integer people);

    @Query("select new com.example.api.dto.Ratings(count(o), avg(o.rate)) from Opinion o where o.offer.id = :id")
    Ratings getRatingsByOfferId(@Param("id") Long id);

    @Query("select distinct o.city from Offer o")
    List<String> getCities();

    @Query(nativeQuery = true, value = "select * from offers order by id desc limit 5")
    List<Offer> getNewest();

    List<Offer> getAllByOwnerEmail(String email);
}
