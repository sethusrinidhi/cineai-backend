package com.sunnychanu.cineai.service;

import com.sunnychanu.cineai.entity.Movie;
import com.sunnychanu.cineai.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public List<Movie> searchByGenre(String genre) {
        return movieRepository.findByGenreContainingIgnoreCase(genre);
    }
}
