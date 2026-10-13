package com.sunnychanu.cineai.repository;

import com.sunnychanu.cineai.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByScreenName(String screenName);
    Optional<Seat> findByScreenNameAndSeatCode(String screenName, String seatCode);
}
