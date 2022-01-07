package com.example.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "opinions")
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @Max(5)
    private Integer rate;

    @NotBlank
    @Column(length = 500)
    @Length(max = 500)
    private String content;

    @ManyToOne
    @JsonBackReference
    private Offer offer;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JsonBackReference
    private User author;

    public Opinion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
