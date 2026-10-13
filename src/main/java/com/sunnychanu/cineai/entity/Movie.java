package com.sunnychanu.cineai.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String tagline;
    private String genre;
    private Integer durationMinutes;
    private String rating;
    private Double imdbRating;
    private Integer cineAiMatchScore;
    private String director;

    @Column(length = 2000)
    private String synopsis;

    private String posterUrl;
    private String backdropUrl;

    private Integer positiveSentimentScore; // e.g. 96%
}
