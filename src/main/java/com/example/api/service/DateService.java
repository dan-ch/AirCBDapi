package com.example.api.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DateService {

    public boolean isReservationDateRangeCollideWithDateRange(LocalDate reservationStartDate, LocalDate reservationEndDate,
                                                               LocalDate requestStartDate, LocalDate requestEndDate) {
        return (isDateEqualToDates(reservationStartDate, requestStartDate, requestEndDate)
                || isDateEqualToDates(reservationEndDate, requestStartDate, requestEndDate)
                || isFirstDateRangeWithinSecondDateRange(reservationStartDate, reservationEndDate,
                requestStartDate, requestEndDate)
                || isFirstDateRangeWithinSecondDateRange(requestStartDate, requestEndDate,
                reservationStartDate, reservationEndDate)
        );
    }

    public boolean isFirstDateRangeWithinSecondDateRange(LocalDate firstStartDate, LocalDate firstEndDate,
                                                          LocalDate secondStartDate, LocalDate secondEndDate) {
        return (firstStartDate.isAfter(secondStartDate) && firstStartDate.isBefore(secondEndDate))
                || (firstEndDate.isAfter(secondStartDate) && firstEndDate.isBefore(secondEndDate));
    }

    public boolean isDateEqualToDates(LocalDate date, LocalDate datesFirst, LocalDate datesSecond){
        return date.isEqual(datesFirst) || date.isEqual(datesSecond);
    }
}
