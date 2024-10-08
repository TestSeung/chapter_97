package com.github.supercoding.repository.reservations;

import com.github.supercoding.repository.airlineTicket.AirlineTicket;
import com.github.supercoding.repository.airlineTicket.AirlineTicketJpaRepository;
import com.github.supercoding.repository.passenger.Passenger;
import com.github.supercoding.repository.passenger.PassengerJpaRepository;
import com.github.supercoding.service.AirReservationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // slice test => Dao Lay/ Jpa 사용하고 있는 Slice Test
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class ReservationJpaRepositoryTest {

//    @Autowired
//    private AirReservationService airReservationService; //레포지토리만 가능

    @Autowired
    private ReservationJpaRepository reservationJpaRepository;

    @Autowired
    private PassengerJpaRepository passengerJpaRepository;

    @Autowired
    private AirlineTicketJpaRepository airlineTicketJpaRepository;



    @DisplayName("FindFlightPriceAndCharge")
    @Test
    void findFlightPriceAndCharge() {
        //given
        Integer userId =1;

        //when
        List<FlightPriceAndCharge> flightPriceAndCharges = reservationJpaRepository.findFlightPriceAndCharge(userId);

        //then
        log.info("결과: "+ flightPriceAndCharges);
    }
    @DisplayName("Reservation 예약 진행")
    @Test
    void saveReservation(){
        Integer userId =1;
        Integer ticketId = 5;

        Passenger passenger = passengerJpaRepository.findPassengerByUserUserId(userId).get();
        AirlineTicket airlineTicket = airlineTicketJpaRepository.findById(2).get();

        Reservation reservation = new Reservation(passenger,airlineTicket);
        Reservation res = reservationJpaRepository.save(reservation);

        log.info("결과 : "+res);

        assertEquals(res.getPassenger(),passenger);
        assertEquals(res.getAirlineTicket(),airlineTicket);
    }


}