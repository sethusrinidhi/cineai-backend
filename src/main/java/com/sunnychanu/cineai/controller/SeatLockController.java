package com.sunnychanu.cineai.controller;

import com.sunnychanu.cineai.entity.SeatLock;
import com.sunnychanu.cineai.service.SeatLockService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/locks")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SeatLockController {

    private final SeatLockService seatLockService;

    @Data
    public static class LockRequest {
        private List<Long> seatIds;
        private Long showtimeId;
        private String userId;
    }

    @PostMapping("/acquire")
    public ResponseEntity<?> acquireSeatLock(@RequestBody LockRequest request) {
        try {
            List<SeatLock> locks = seatLockService.acquireLocks(
                    request.getSeatIds(),
                    request.getShowtimeId(),
                    request.getUserId() != null ? request.getUserId() : "guest_user"
            );
            return ResponseEntity.ok(Map.of(
                    "status", "SUCCESS",
                    "message", "ACID Pessimistic Lock Acquired for 5 minutes",
                    "locks", locks
            ));
        } catch (IllegalStateException e) {
            // Concurrency conflict / double booking attempt detected!
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "status", "CONFLICT",
                    "errorCode", "CONCURRENT_BOOKING_PREVENTED",
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "ERROR",
                    "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/release/{lockToken}")
    public ResponseEntity<?> releaseSeatLock(@PathVariable String lockToken) {
        seatLockService.releaseLock(lockToken);
        return ResponseEntity.ok(Map.of("status", "RELEASED", "token", lockToken));
    }
}
