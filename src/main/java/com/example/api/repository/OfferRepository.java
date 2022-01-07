package com.example.api.repository;

import com.example.api.model.Offer;
import com.example.api.model.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findAllByCityAndStatus(String city, Offer.OfferStatus status);

    List<Offer> findAllByCityAndStatusAndMaxPeopleGreaterThanEqual(String city, Offer.OfferStatus status, Integer people);

    @Query("select new com.example.api.model.Ratings(count(o), avg(o.rate)) from Opinion o where o.offer.id = :id")
    Ratings getRatingsByOfferId(@Param("id") Long id);

}
