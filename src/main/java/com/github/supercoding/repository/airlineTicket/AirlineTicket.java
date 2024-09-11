package com.github.supercoding.repository.airlineTicket;

import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "ticketId")

public class AirlineTicket {
    private Integer ticketId;
    private String ticketType;
    private String departureLocation;
    private String arrivalLocation;
    private LocalDateTime departureAt;
    private LocalDateTime returnAt;
    private Double tax;
    private Double totalPrice;

    public AirlineTicket(Integer ticketId, String ticketType, String departureLocation, String arrivalLocation, Date departureAt, Date returnAt, Double tax, Double totalPrice) {
        this.ticketId = ticketId;
        this.ticketType = ticketType;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureAt = departureAt.toLocalDate().atStartOfDay();
        this.returnAt = returnAt.toLocalDate().atStartOfDay();
        this.tax = tax;
        this.totalPrice = totalPrice;
    }

}
