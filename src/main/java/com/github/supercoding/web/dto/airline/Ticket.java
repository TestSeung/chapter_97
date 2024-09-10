package com.github.supercoding.web.dto.airline;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.supercoding.repository.airlineTicket.AirlineTicket;

import java.time.format.DateTimeFormatter;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Ticket {
    private String depart;
    private String arrive;
    private String departureTime;
    private String returnTime;
    private Integer ticketId;

    public Ticket() {
    }

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Ticket(AirlineTicket airlineTicket) {
        this.ticketId = airlineTicket.getTicketId();
        this.depart = airlineTicket.getDepartureLocation();
        this.arrive = airlineTicket.getArrivalLocation();
        this.departureTime = airlineTicket.getDepartureAt().format(formatter);
        this.returnTime = airlineTicket.getReturnAt().format(formatter);
    }

    public String getDepart() {
        return depart;
    }

    public String getArrive() {
        return arrive;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public Integer getTicketId() {
        return ticketId;
    }
}
