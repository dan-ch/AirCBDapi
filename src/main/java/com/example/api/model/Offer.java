package com.example.api.model;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "offers")
public class Offer {

    public enum OfferStatus{
        ACTIVE,
        INACTIVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(min = 5, max = 50)
    private String title;

    @NotBlank
    private String city;

    @NotNull
    private Integer maxPeople;

    @NotNull
    private Integer dailyPrice;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonBackReference
    private User owner;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "offer")
    @JsonBackReference
    private List<Reservation> reservations;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "offer")
    @JsonBackReference
    private List<Opinion> opinions;


    // TODO rating albo zamienić na customowy
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Ratings ratings;


    public Offer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    public Integer getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(Integer dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public OfferStatus getStatus() {
        return status;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }

    public List<Opinion> getOpinions() {
        return opinions;
    }

    public void setOpinions(List<Opinion> opinions) {
        this.opinions = opinions;
    }

    public Ratings getRatings() {
        return ratings;
    }

    public void setRatings(Ratings ratings) {
        this.ratings = ratings;
    }
}
