package com.sunnychanu.cineai.repository;

import com.sunnychanu.cineai.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByGenreContainingIgnoreCase(String genre);
    List<Movie> findByDirectorContainingIgnoreCase(String director);
}
