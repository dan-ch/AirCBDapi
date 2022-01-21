package com.example.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "images")
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private Long id;

  @NotBlank
  @Column(length = 1024, nullable = false)
  private String url;

  @Column(nullable = false)
  @JsonIgnore
  private String cloudId;

  @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JsonIgnore
  private Offer offer;

  public Image() {
  }

  public Image(String url, String cloudId) {
    this.url = url;
    this.cloudId = cloudId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getCloudId() {
    return cloudId;
  }

  public void setCloudId(String cloudId) {
    this.cloudId = cloudId;
  }

  public Offer getOffer() {
    return offer;
  }

  public void setOffer(Offer offer) {
    this.offer = offer;
  }
}
