package com.sunnychanu.cineai.repository;

import com.sunnychanu.cineai.entity.SeatLock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SeatLockRepository extends JpaRepository<SeatLock, String> {

    /**
     * ACID Pessimistic Write Lock.
     * Generates: SELECT ... FOR UPDATE in MySQL/InnoDB.
     * Prevents other concurrent threads or transactions from modifying or double-locking this seat.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT sl FROM SeatLock sl WHERE sl.seat.id = :seatId AND sl.showtime.id = :showtimeId AND sl.status = 'HELD' AND sl.expiresAt > :now")
    Optional<SeatLock> findActiveLockForUpdate(
        @Param("seatId") Long seatId, 
        @Param("showtimeId") Long showtimeId, 
        @Param("now") LocalDateTime now
    );

    List<SeatLock> findByShowtimeIdAndStatus(Long showtimeId, SeatLock.LockStatus status);

    @Query("SELECT sl FROM SeatLock sl WHERE sl.status = 'HELD' AND sl.expiresAt < :now")
    List<SeatLock> findExpiredLocks(@Param("now") LocalDateTime now);
}
