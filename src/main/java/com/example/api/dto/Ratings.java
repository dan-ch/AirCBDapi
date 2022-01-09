package com.example.api.dto;

public class Ratings {

  private Long opinionsCount;

  private Double rateCount;

  public Ratings(Long opinionsCount, Double rateCount) {
    this.opinionsCount = opinionsCount;
    this.rateCount = rateCount;
  }

  public Long getOpinionsCount() {
    return opinionsCount;
  }

  public void setOpinionsCount(Long opinionsCount) {
    this.opinionsCount = opinionsCount;
  }

  public Double getRateCount() {
    return rateCount;
  }

  public void setRateCount(Double rateCount) {
    this.rateCount = rateCount;
  }
}
