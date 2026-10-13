-- CineAI by Sunnychanu - Normalized Relational MySQL Schema
-- Brand Ecosystem: Sunnychanu Technologies
-- B.Tech Major Project - Computer Science & Engineering

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER'
);

CREATE TABLE IF NOT EXISTS movies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    tagline VARCHAR(255),
    genre VARCHAR(100),
    duration_minutes INT,
    rating VARCHAR(20),
    imdb_rating DOUBLE,
    cine_ai_match_score INT,
    director VARCHAR(150),
    synopsis TEXT,
    poster_url VARCHAR(500),
    backdrop_url VARCHAR(500),
    positive_sentiment_score INT
);

CREATE TABLE IF NOT EXISTS showtimes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id BIGINT NOT NULL,
    screen_name VARCHAR(100) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    projection_format VARCHAR(50),
    FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS seats (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    screen_name VARCHAR(100) NOT NULL,
    seat_row VARCHAR(5) NOT NULL,
    seat_number INT NOT NULL,
    seat_code VARCHAR(10) NOT NULL,
    tier VARCHAR(20) NOT NULL,
    price DOUBLE NOT NULL
);

-- Core ACID Pessimistic Lock Table
-- Prevents race conditions and double bookings during concurrent ticket checkouts
CREATE TABLE IF NOT EXISTS cineai_seat_locks (
    lock_token VARCHAR(100) PRIMARY KEY,
    seat_id BIGINT NOT NULL,
    showtime_id BIGINT NOT NULL,
    user_id VARCHAR(100) NOT NULL,
    locked_at TIMESTAMP NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'HELD',
    FOREIGN KEY (seat_id) REFERENCES seats(id),
    FOREIGN KEY (showtime_id) REFERENCES showtimes(id),
    INDEX idx_seat_showtime (seat_id, showtime_id),
    INDEX idx_lock_status (status, expires_at)
);

CREATE TABLE IF NOT EXISTS bookings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_reference VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    showtime_id BIGINT NOT NULL,
    total_amount DOUBLE NOT NULL,
    booking_time TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'CONFIRMED',
    qr_code_data VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (showtime_id) REFERENCES showtimes(id)
);

CREATE TABLE IF NOT EXISTS booking_seats (
    booking_id BIGINT NOT NULL,
    seat_id BIGINT NOT NULL,
    PRIMARY KEY (booking_id, seat_id),
    FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE,
    FOREIGN KEY (seat_id) REFERENCES seats(id) ON DELETE CASCADE
);
