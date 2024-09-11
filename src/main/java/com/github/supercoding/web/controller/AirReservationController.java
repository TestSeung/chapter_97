package com.github.supercoding.web.controller;

import com.github.supercoding.service.AirReservationService;
//import com.github.supercoding.service.exceptions.InvalidValueException;
//import com.github.supercoding.service.exceptions.NotFoundException;
import com.github.supercoding.service.exceptions.InvalidValueException;
import com.github.supercoding.service.exceptions.NotFoundException;
import com.github.supercoding.web.dto.airline.ReservationRequest;
import com.github.supercoding.web.dto.airline.ReservationResult;
import com.github.supercoding.web.dto.airline.Ticket;
import com.github.supercoding.web.dto.airline.TicketResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/air-reservation")
@RequiredArgsConstructor
@Slf4j
public class AirReservationController{

    private final AirReservationService airReservationService;

    @GetMapping("/tickets")
    public TicketResponse findAirlineTickets(@RequestParam("user-Id")Integer userId,
                                             @RequestParam("airline-ticket-type")String ticketType) throws InvalidValueException, NotFoundException {

        List<Ticket> tickets = airReservationService.findUserFavoritePlaceTickets(userId,ticketType);
        TicketResponse ticketResponse = new TicketResponse(tickets);
        return ticketResponse;

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reservations")
    public ReservationResult makeReservation(@RequestBody ReservationRequest reservationRequest) throws NotFoundException {

        ReservationResult reservationResult = airReservationService.makeReservation(reservationRequest);
        return reservationResult ;

    }
}
