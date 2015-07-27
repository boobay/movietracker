package io.boobay.movietracker.service;

import io.boobay.movietracker.bo.Movie;

import java.util.List;

/**
 * Created by boobay on 7/11/15.
 */
public interface MovieService {
    List<Movie> findMovies();
    List<Movie> findMovies(boolean owned);
    Movie findMovie(Long id) throws Exception;
    void addMovie(Movie movie) throws Exception;
    void updateMovie(Movie movie) throws Exception;
    List<Movie> searchMovies(String movie);
}
