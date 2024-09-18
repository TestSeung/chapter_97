package com.github.supercoding.web.controller;

import com.github.supercoding.repository.userDetails.CustomUserDetails;
import com.github.supercoding.service.AirReservationService;
import com.github.supercoding.service.exceptions.InvalidValueException;
import com.github.supercoding.service.exceptions.NotFoundException;
import com.github.supercoding.web.dto.airline.ReservationRequest;
import com.github.supercoding.web.dto.airline.ReservationResult;
import com.github.supercoding.web.dto.airline.Ticket;
import com.github.supercoding.web.dto.airline.TicketResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/air-reservation")
@RequiredArgsConstructor
@Slf4j
public class AirReservationController{

    private final AirReservationService airReservationService;

    @GetMapping("/tickets")
    public TicketResponse findAirlineTickets(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                             @RequestParam("airline-ticket-type")String ticketType) throws InvalidValueException, NotFoundException {

        Integer userId = customUserDetails.getUserId();
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
    @Operation(summary = "userId의 예약한 항공편과 수수료 총합")
    @GetMapping("/users-sum-price")
    public Double findUserFlightSumPrice(
            @Parameter(name ="user-id",description = "유저 ID",example = "1")
            @RequestParam("user-id") Integer userId) throws NotFoundException {

        Double sum = airReservationService.findUserFlightSumPrice(userId);
        return sum;
    }

}
