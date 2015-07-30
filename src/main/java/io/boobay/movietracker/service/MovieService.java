package io.boobay.movietracker.service;

import com.google.common.collect.Lists;
import io.boobay.movietracker.bo.Movie;
import io.boobay.movietracker.exception.MovieTrackerException;
import io.boobay.movietracker.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by boobay on 7/11/15.
 */
@Component
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Cacheable(value = "searchResults")
    public List<Movie> findMovies() {
        return Lists.newArrayList(movieRepository.findAll(new Sort(Sort.Direction.ASC, "name")));
    }

    @Cacheable(value = "searchResultsOwned")
    public List<Movie> findMovies(boolean owned) {
        return Lists.newArrayList(movieRepository.findByOwnedOrderByNameAsc(owned));
    }

    @Cacheable(value = "movie", key = "#id")
    public Movie findMovie(Long id) throws Exception {
        if (id == null || id.equals(0L)) {
            throw new MovieTrackerException("Please provide a valid movie.");
        }
        return movieRepository.findOne(id);
    }

    @CacheEvict(value = {"searchResults", "searchResultsOwned", "searchMovies"}, allEntries = true)
    @CachePut(value = "movie", key = "#movie.id")
    public Movie addMovie(Movie movie) throws Exception {
        if (movie == null) {
            throw new MovieTrackerException("Movie doesn't exist");
        }

        if (movie.getName() == null) {
            throw new MovieTrackerException("Name is null");
        }

        if (movie.getOwned() == null) {
            throw new MovieTrackerException("Do you own the frickin' movie?");
        }

        movieRepository.save(movie);
        return movie;
    }

    @CacheEvict(value = {"searchResults", "searchResultsOwned", "searchMovies"}, allEntries = true)
    @CachePut(value = "movie", key = "#movie.id")
    public Movie updateMovie(Movie movie) throws Exception {
        if (movie == null) {
            throw new MovieTrackerException("Movie doesn't exist");
        }

        if (movie.getName() == null) {
            throw new MovieTrackerException("Name is null");
        }

        if (movie.getOwned() == null) {
            throw new MovieTrackerException("Do you own the frickin' movie?");
        }

        movieRepository.save(movie);

        return movie;
    }

    @Cacheable(value = "searchMovies")
    public List<Movie> searchMovies(String movie) {
        System.out.println("Not caching");
        return Lists.newArrayList(movieRepository.findByNameContainingIgnoreCaseOrderByNameAsc(movie));
    }
}
