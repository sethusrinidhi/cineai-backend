package com.sunnychanu.cineai.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "showtimes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Showtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(nullable = false)
    private String screenName; // e.g. "Screen 1 (IMAX Grand Laser)"

    @Column(nullable = false)
    private LocalDateTime startTime;

    private String projectionFormat; // "IMAX 3D", "Dolby Atmos"
}
