package com.github.supercoding.repository.reservations;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reservation {
    private Integer reservationId;

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    private Integer passengerId;
    private Integer airlineTicketId;
    private String reservationStatus;
    private LocalDateTime reserveAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(reservationId, that.reservationId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(reservationId);
    }

    public Reservation(Integer passengerId, Integer airlineTicketId) {
        this.passengerId = passengerId;
        this.airlineTicketId = airlineTicketId;
        this.reservationStatus = "대기";
        this.reserveAt = LocalDateTime.now();
    }

    public Integer getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Integer passengerId) {
        this.passengerId = passengerId;
    }

    public Integer getAirlineTicketId() {
        return airlineTicketId;
    }

    public void setAirlineTicketId(Integer airlineTicketId) {
        this.airlineTicketId = airlineTicketId;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public LocalDateTime getReserveAt() {
        return reserveAt;
    }

    public void setReserveAt(LocalDateTime reserveAt) {
        this.reserveAt = reserveAt;
    }
}
