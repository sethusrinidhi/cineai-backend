-- Seed Users
INSERT INTO users (username, password, email, role) VALUES 
('sunnychanu', '$2a$10$wE1VlU9kL9hA.Qf.xGzLueYt9p9e9O3c4F/m0B7I5M6XyY8Z1234', 'sunny@sunnychanu.com', 'ROLE_ADMIN'),
('sarah92', '$2a$10$wE1VlU9kL9hA.Qf.xGzLueYt9p9e9O3c4F/m0B7I5M6XyY8Z1234', 'sarah@example.com', 'ROLE_USER');

-- Seed Movies
INSERT INTO movies (title, tagline, genre, duration_minutes, rating, imdb_rating, cine_ai_match_score, director, synopsis, poster_url, backdrop_url, positive_sentiment_score) VALUES
('Dune: Part Two', 'Long live the fighters.', 'Sci-Fi, Adventure, Action', 166, 'PG-13', 8.6, 98, 'Denis Villeneuve', 'Paul Atreides unites with Chani and the Fremen while seeking revenge against the conspirators who destroyed his family.', 'https://images.unsplash.com/photo-1534447677768-be436bb09401', 'https://images.unsplash.com/photo-1509198397868-475647b2a1e5', 96),
('Oppenheimer', 'The world forever changes.', 'Biography, Drama, History', 180, 'R', 8.9, 95, 'Christopher Nolan', 'The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb during World War II.', 'https://images.unsplash.com/photo-1440404653325-ab127d49abc1', 'https://images.unsplash.com/photo-1451187580459-43490279c0fa', 94),
('Interstellar', 'Mankind was born on Earth. It was never meant to die here.', 'Sci-Fi, Drama, Adventure', 169, 'PG-13', 8.7, 99, 'Christopher Nolan', 'When Earth becomes uninhabitable, an ex-NASA pilot leads a team across a wormhole in search of a new home.', 'https://images.unsplash.com/photo-1446776811953-b23d57bd21aa', 'https://images.unsplash.com/photo-1506703719100-a0f3a48c0f86', 97);

-- Seed Seats for Screen 1
INSERT INTO seats (screen_name, seat_row, seat_number, seat_code, tier, price) VALUES
('Screen 1 (IMAX Grand Laser)', 'A', 1, 'A1', 'VIP', 18.50),
('Screen 1 (IMAX Grand Laser)', 'A', 2, 'A2', 'VIP', 18.50),
('Screen 1 (IMAX Grand Laser)', 'D', 6, 'D6', 'PREMIUM', 14.00),
('Screen 1 (IMAX Grand Laser)', 'D', 7, 'D7', 'PREMIUM', 14.00),
('Screen 1 (IMAX Grand Laser)', 'F', 5, 'F5', 'STANDARD', 10.00);
