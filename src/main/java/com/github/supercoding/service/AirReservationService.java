package com.github.supercoding.service;

import com.github.supercoding.repository.airlineTicket.AirlineTicket;
import com.github.supercoding.repository.airlineTicket.AirlineTicketAndFlightInfo;
import com.github.supercoding.repository.airlineTicket.AirlineTicketRepository;
import com.github.supercoding.repository.passenger.Passenger;
import com.github.supercoding.repository.passenger.PassengerRepository;
import com.github.supercoding.repository.reservations.Reservation;
import com.github.supercoding.repository.reservations.ReservationRepository;
import com.github.supercoding.repository.users.UserEntity;
import com.github.supercoding.repository.users.UserRepository;
import com.github.supercoding.service.exceptions.InvalidValueException;
import com.github.supercoding.service.exceptions.NotAcceptException;
import com.github.supercoding.service.exceptions.NotFoundException;
import com.github.supercoding.service.mapper.TicketMapper;
import com.github.supercoding.web.dto.airline.ReservationRequest;
import com.github.supercoding.web.dto.airline.ReservationResult;
import com.github.supercoding.web.dto.airline.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirReservationService {

    private final UserRepository userRepository;
    private final AirlineTicketRepository airlineTicketRepository;

    private final PassengerRepository passengerRepository;
    private final ReservationRepository reservationRepository;



    public List<Ticket> findUserFavoritePlaceTickets(Integer userId, String ticketType) throws InvalidValueException, NotFoundException {
        // 필요한 repository: userRepository,airLineTicketRepository
        //1. 유저를 userID로 가져와서,선호하는 여행지 도출
        //2. 선호하는 여행지와 ticketType으로 AirlineTicket table 질의 해서 필요한 AirlineTicket
        //3. 이둘의 정보를 조합해서 Ticket DTO를 만든다.

        Set<String> ticketTypeSet = new HashSet<>(Arrays.asList("편도","왕복"));

        if(!ticketTypeSet.contains(ticketType)) {
            throw new InvalidValueException("해당 TicketType"+ ticketType+"은 지원하지않습니다.");
        }

        UserEntity userEntity = userRepository.findUserById(userId).orElseThrow(()->new NotFoundException("해당 "+ userId+" 유저를 찾을 수 없습니다."));
        String likePlace = userEntity.getLikeTravelPlace();

        List<AirlineTicket> airlineTickets
                = airlineTicketRepository.findAllAirlineTicketWithPlaceAndTicketType(likePlace,ticketType);
        if (airlineTickets.isEmpty())
            throw new NotFoundException("해당 likePlace: "+likePlace+"와 TicketType: "+ticketType+"에 해당하는 항공권을 찾을 수 없습니다.");
        List<Ticket> tickets = airlineTickets.stream().map(TicketMapper.INSTANCE::airlineTicketToTicket).collect(Collectors.toList());
        return tickets;
    }

    @Transactional(transactionManager = "tm2")
    public ReservationResult makeReservation(ReservationRequest reservationRequest) throws NotFoundException {
        //1. Reservation Repository, Join table(flight/airline_ticket) passenger repository
        //0.
        Integer userId = reservationRequest.getUserId();
        Integer airlineTicketId = reservationRequest.getAirlineTicketId();
        //1. Passenger
        Passenger passenger = passengerRepository.findPassengerByUserId(userId)
                                                    .orElseThrow(()->new NotFoundException("요청하신 userId"+userId+"에 해당하는 Passenger를 찾을 수 없습니다."));
        Integer passengerId = passenger.getPassengerId();

        //2.price 정보 불러오기
        List<AirlineTicketAndFlightInfo> airlineTicketAndFlightInfo
                = airlineTicketRepository.findAllAirLineTicketAndFlightInfo(airlineTicketId);

        if (airlineTicketAndFlightInfo.isEmpty())
            throw new NotFoundException("AirlineTicket Id"+airlineTicketId+"에 해당하는 항공편과 항공권을 찾을 수 없습니다.");

        Boolean isSuccess =false;

        //3. reservation 생성
        Reservation reservation = new Reservation(passengerId,airlineTicketId);
        try {
            isSuccess  = reservationRepository.saveReservation(reservation);
        } catch (Exception e) {
            throw new NotAcceptException("Reservation이 등록되는 과정이 거부되었습니다.");
        }

        //reservationResult DTO
        List<Integer>prices = airlineTicketAndFlightInfo.stream()
                    .map(AirlineTicketAndFlightInfo::getPrice)
                    .collect(Collectors.toList());
        List<Integer>charges = airlineTicketAndFlightInfo.stream()
                .map(AirlineTicketAndFlightInfo::getCharge)
                .collect(Collectors.toList());
        Integer tax = airlineTicketAndFlightInfo.stream()
                .map(AirlineTicketAndFlightInfo::getTax).findFirst().get();
        Integer totalPrice = airlineTicketAndFlightInfo.stream()
                .map(AirlineTicketAndFlightInfo::getTotalPrice).findFirst().get();

        return  new ReservationResult(prices,charges,tax,totalPrice,isSuccess);
    }
}
