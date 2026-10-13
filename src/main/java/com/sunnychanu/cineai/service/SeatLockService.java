package com.sunnychanu.cineai.service;

import com.sunnychanu.cineai.entity.Seat;
import com.sunnychanu.cineai.entity.SeatLock;
import com.sunnychanu.cineai.entity.Showtime;
import com.sunnychanu.cineai.repository.SeatLockRepository;
import com.sunnychanu.cineai.repository.SeatRepository;
import com.sunnychanu.cineai.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatLockService {

    private final SeatLockRepository seatLockRepository;
    private final SeatRepository seatRepository;
    private final ShowtimeRepository showtimeRepository;

    /**
     * ACID Pessimistic Concurrency Reservation.
     * Prevents duplicate bookings by locking seat records in MySQL at transaction boundary.
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<SeatLock> acquireLocks(List<Long> seatIds, Long showtimeId, String userId) {
        LocalDateTime now = LocalDateTime.now();
        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new IllegalArgumentException("Showtime not found: " + showtimeId));

        List<SeatLock> acquiredLocks = new ArrayList<>();
        String lockGroupToken = "ACID-TX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        for (Long seatId : seatIds) {
            // Pessimistic write lock query (SELECT ... FOR UPDATE)
            seatLockRepository.findActiveLockForUpdate(seatId, showtimeId, now).ifPresent(activeLock -> {
                throw new IllegalStateException("Seat ID " + seatId + " is currently locked under active ACID transaction by user: " + activeLock.getUserId());
            });

            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new IllegalArgumentException("Seat not found: " + seatId));

            SeatLock lock = SeatLock.builder()
                    .lockToken(lockGroupToken + "-" + seat.getSeatCode())
                    .seat(seat)
                    .showtime(showtime)
                    .userId(userId)
                    .lockedAt(now)
                    .expiresAt(now.plusMinutes(5)) // 5 minute reservation window
                    .status(SeatLock.LockStatus.HELD)
                    .build();

            acquiredLocks.add(seatLockRepository.save(lock));
        }

        return acquiredLocks;
    }

    @Transactional
    public void releaseLock(String lockToken) {
        seatLockRepository.findById(lockToken).ifPresent(lock -> {
            lock.setStatus(SeatLock.LockStatus.EXPIRED);
            seatLockRepository.save(lock);
        });
    }

    /**
     * Background daemon reconciling expired seat locks every 15 seconds.
     */
    @Scheduled(fixedRate = 15000)
    @Transactional
    public void cleanupExpiredLocks() {
        LocalDateTime now = LocalDateTime.now();
        List<SeatLock> expired = seatLockRepository.findExpiredLocks(now);
        for (SeatLock lock : expired) {
            lock.setStatus(SeatLock.LockStatus.EXPIRED);
            seatLockRepository.save(lock);
        }
        if (!expired.isEmpty()) {
            System.out.println("⏰ [ACID Auto-Reconciler] Auto-released " + expired.size() + " expired seat locks back to public availability.");
        }
    }
}
