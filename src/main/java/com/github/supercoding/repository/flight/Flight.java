package com.github.supercoding.repository.flight;


import com.github.supercoding.repository.airlineTicket.AirlineTicket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flight")
public class Flight {
    @Id @Column(name ="flight_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flightId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="ticket_id",nullable = true)
    private AirlineTicket airlineTicket;

    @Column(name ="departure_at")
    private LocalDateTime departAt;

    @Column(name ="arrival_at")
    private LocalDateTime arrivalAt;

    @Column(name ="departure_loc",length = 50)
    private String departureLocation;

    @Column(name ="arrival_loc",length = 50)
    private String arrivalLocation;

    @Column(name ="flight_price")
    private Double flightPrice;

    @Column(name ="charge")
    private Double charge;
}
