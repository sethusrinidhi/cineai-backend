package com.sunnychanu.cineai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * CineAI by Sunnychanu
 * An Intelligent Movie Discovery and Conversational Ticket Booking Platform
 * Brand Ecosystem: Sunnychanu Technologies
 * Department of Computer Science & Engineering - B.Tech Major Project
 */
@SpringBootApplication
@EnableScheduling
public class CineAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CineAiApplication.class, args);
        System.out.println("==================================================================");
        System.out.println("🚀 CineAI Core Booking Engine (Spring Boot 3) Started Successfully!");
        System.out.println("🔒 ACID Pessimistic Concurrency Engine: READY");
        System.out.println("🛡️ Stateless JWT Security Filter: ACTIVE");
        System.out.println("🌐 REST API Endpoints listening on port: 8080");
        System.out.println("==================================================================");
    }
}
