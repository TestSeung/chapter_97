package com.github.supercoding.repository.passenger;

import com.github.supercoding.repository.items.ItemEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PassengerDao implements PassengerRepository {
    private JdbcTemplate jdbcTemplate;

    static RowMapper<Passenger> itemEntityRowMapper = ((rs, rowNum) ->
            new Passenger.PassengerBuilder()
                    .passengerId(rs.getInt("passenger_id"))
                    .userId(rs.getInt("user_id"))
                    .passportNum(rs.getNString("passport_num"))
                    .build());

    public PassengerDao(@Qualifier("jdbcTemplate2") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Passenger findPassengerByUserId(Integer userId) {
        System.out.println("user id: " + userId);
        return jdbcTemplate.queryForObject("select * from passenger where user_id=?", itemEntityRowMapper, userId);
    }
}
