package com.github.supercoding.repository.reservations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationJpaRepository extends JpaRepository<Reservation, Integer> {
    @Query("SELECT new com.github.supercoding.repository.reservations.FlightPriceAndCharge(f.flightPrice,f.charge) " +
            " FROM Reservation r " +
            " JOIN r.passenger p " + // id당 티켓 수량
            " JOIN r.airlineTicket a " +
            " JOIN a.flightList f " + // 값과 세금을 얻기 위해
            "WHERE p.user.userId = :userId" )
    List<FlightPriceAndCharge> findFlightPriceAndCharge(Integer userId);
}
