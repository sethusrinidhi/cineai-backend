package com.sunnychanu.cineai.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String screenName;

    @Column(nullable = false)
    private String seatRow; // "A", "B", ...

    @Column(nullable = false)
    private Integer seatNumber; // 1, 2, ...

    @Column(nullable = false)
    private String seatCode; // "A1", "D7"

    @Column(nullable = false)
    private String tier; // "VIP", "PREMIUM", "STANDARD"

    @Column(nullable = false)
    private Double price;
}
