package com.sunnychanu.cineai.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * ACID Concurrency Seat Lock Record
 * Holds temporary pessimistic reservation on a seat to prevent double booking.
 */
@Entity
@Table(name = "cineai_seat_locks", 
       indexes = {
           @Index(name = "idx_seat_showtime", columnList = "seat_id, showtime_id"),
           @Index(name = "idx_lock_status", columnList = "status, expires_at")
       })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatLock {

    @Id
    private String lockToken; // e.g. "ACID-TX-89FA12"

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "showtime_id", nullable = false)
    private Showtime showtime;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private LocalDateTime lockedAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt; // lockedAt + 5 minutes

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LockStatus status; // HELD, EXPIRED, CONVERTED_TO_BOOKING

    public enum LockStatus {
        HELD,
        EXPIRED,
        CONVERTED_TO_BOOKING
    }

    public boolean isExpired(LocalDateTime now) {
        return this.status != LockStatus.HELD || this.expiresAt.isBefore(now);
    }
}
