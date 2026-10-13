package com.sunnychanu.cineai.controller;

import com.sunnychanu.cineai.entity.Booking;
import com.sunnychanu.cineai.service.BookingService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;

    @Data
    public static class BookingRequest {
        private Long userId;
        private Long showtimeId;
        private List<Long> seatIds;
        private String lockToken;
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmBooking(@RequestBody BookingRequest request) {
        try {
            Booking booking = bookingService.createBooking(
                    request.getUserId() != null ? request.getUserId() : 1L,
                    request.getShowtimeId(),
                    request.getSeatIds(),
                    request.getLockToken()
            );
            return ResponseEntity.ok(Map.of(
                    "status", "CONFIRMED",
                    "bookingReference", booking.getBookingReference(),
                    "totalAmount", booking.getTotalAmount(),
                    "qrCodeData", booking.getQrCodeData(),
                    "bookingTime", booking.getBookingTime().toString()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "FAILED",
                    "message", e.getMessage()
            ));
        }
    }
}
