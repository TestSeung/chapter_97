package com.github.supercoding.web.dto.airline;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ReservationRequest {
    private Integer userId;
    private Integer airlineTicketId;

    public Integer getUserId() {
        return userId;
    }

    public Integer getAirlineTicketId() {
        return airlineTicketId;
    }

    public ReservationRequest() {
    }
}
