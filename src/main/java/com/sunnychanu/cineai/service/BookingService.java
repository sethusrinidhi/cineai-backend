package com.sunnychanu.cineai.service;

import com.sunnychanu.cineai.entity.*;
import com.sunnychanu.cineai.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatLockRepository seatLockRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Booking createBooking(Long userId, Long showtimeId, List<Long> seatIds, String lockToken) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new IllegalArgumentException("Showtime not found: " + showtimeId));

        List<Seat> seats = seatRepository.findAllById(seatIds);
        double total = seats.stream().mapToDouble(Seat::getPrice).sum() + 2.50; // fee

        String bookingRef = "CN-SUNNY-" + (100000 + new Random().nextInt(900000));

        Booking booking = Booking.builder()
                .bookingReference(bookingRef)
                .user(user)
                .showtime(showtime)
                .bookedSeats(seats)
                .totalAmount(total)
                .bookingTime(LocalDateTime.now())
                .status("CONFIRMED")
                .qrCodeData("CINEAI-VALID-" + bookingRef + "-TIME-" + showtime.getStartTime())
                .build();

        // Release or convert the temporary locks
        if (lockToken != null) {
            seatLockRepository.findById(lockToken).ifPresent(lock -> {
                lock.setStatus(SeatLock.LockStatus.CONVERTED_TO_BOOKING);
                seatLockRepository.save(lock);
            });
        }

        return bookingRepository.save(booking);
    }
}
