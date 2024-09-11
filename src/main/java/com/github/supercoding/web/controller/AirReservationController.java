package com.github.supercoding.web.controller;

import com.github.supercoding.service.AirReservationService;
import com.github.supercoding.service.Exceptions.InvalidValueException;
import com.github.supercoding.service.Exceptions.NotAcceptException;
import com.github.supercoding.service.Exceptions.NotFoundException;
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
public class AirReservationController {

    private final AirReservationService airReservationService;

    @GetMapping("/tickets")
    public ResponseEntity findAirlineTickets(@RequestParam("user-Id")Integer userId,
                                             @RequestParam("airline-ticket-type")String ticketType) {
        try{
            List<Ticket> tickets = airReservationService.findUserFavoritePlaceTickets(userId,ticketType);
            TicketResponse ticketResponse = new TicketResponse(tickets);
            return new ResponseEntity(ticketResponse, HttpStatus.OK);
        }catch (InvalidValueException ive){
            log.error("Clinet 요청에 문제가 있었습니다."+ive.getMessage());
            return new ResponseEntity(ive.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (NotFoundException nfe){
            log.error("Client 요청이후 DB 검색 중 에러로 다음처럼 출력합니다 "+nfe.getMessage());
            return new ResponseEntity(nfe.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/reservations")
    public ResponseEntity makeReservation(@RequestBody ReservationRequest reservationRequest){
        try {
            ReservationResult reservationResult = airReservationService.makeReservation(reservationRequest);
            return new ResponseEntity(reservationResult, HttpStatus.OK);
        }catch (NotFoundException nfe){
            log.error("Client 요청이후 DB 검색 중 에러로 다음처럼 출력합니다 "+nfe.getMessage());
            return new ResponseEntity(nfe.getMessage(),HttpStatus.NOT_FOUND);
        }catch (NotAcceptException nae){
            log.error("Client 가 거부됩니다. "+nae.getMessage());
            return new ResponseEntity(nae.getMessage(),HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
