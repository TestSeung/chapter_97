package com.github.supercoding.repository.reservations;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "reservationId")
public class Reservation {
    private Integer reservationId;
    private Integer passengerId;
    private Integer airlineTicketId;
    private String reservationStatus;
    private LocalDateTime reserveAt;


    public Reservation(Integer passengerId, Integer airlineTicketId) {
        this.passengerId = passengerId;
        this.airlineTicketId = airlineTicketId;
        this.reservationStatus = "대기";
        this.reserveAt = LocalDateTime.now();
    }

}
