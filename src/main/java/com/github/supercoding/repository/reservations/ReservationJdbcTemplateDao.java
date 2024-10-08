//package com.github.supercoding.repository.reservations;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.sql.Date;
//import java.sql.Timestamp;
//
//@Repository
//public class ReservationJdbcTemplateDao implements ReservationRepository{
//
//    private JdbcTemplate jdbcTemplate;
//
//    public ReservationJdbcTemplateDao(@Qualifier("jdbcTemplate2") JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    public Boolean saveReservation(Reservation reservation) {
//        try {
//            Integer rowNums = jdbcTemplate.update("INSERT INTO reservation(passenger_id,airline_ticket_id,reservation_status,reserve_at)" +
//                            "VALUES (?,?,?,?)",reservation.getPassenger().getPassengerId(),
//                    reservation.getAirlineTicket().getTicketId(),
//                    reservation.getReservationStatus(),
//                    new Date(Timestamp.valueOf(reservation.getReserveAt()).getTime()));
//            return rowNums > 0;
//        } catch (Exception e) {
//            throw new RuntimeException();
//        }
//
//    }
//}
