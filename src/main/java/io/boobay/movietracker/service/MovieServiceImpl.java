package io.boobay.movietracker.service;

import com.google.common.collect.Lists;
import io.boobay.movietracker.bo.Movie;
import io.boobay.movietracker.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by boobay on 7/11/15.
 */
@Component
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> findMovies() {
        return Lists.newArrayList(movieRepository.findAll(new Sort(Sort.Direction.ASC, "name")));
    }

    public List<Movie> findMovies(boolean owned) {
        return Lists.newArrayList(movieRepository.findByOwnedOrderByNameAsc(owned));
    }

    public Movie findMovie(Long id) throws Exception {
        if (id == null || id.equals(0L)) {
            throw new Exception("Please provide a valid movie.");
        }
        return movieRepository.findOne(id);
    }

    public void addMovie(Movie movie) throws Exception {
        if (movie == null) {
            throw new Exception("Movie doesn't exist");
        }

        if (movie.getName() == null) {
            throw new Exception("Name is null");
        }

        if (movie.getOwned() == null) {
            throw new Exception("Do you own the frickin' movie?");
        }

        movieRepository.save(movie);
    }

    public void updateMovie(Movie movie) throws Exception {
        if (movie == null) {
            throw new Exception("Movie doesn't exist");
        }

        if (movie.getName() == null) {
            throw new Exception("Name is null");
        }

        if (movie.getOwned() == null) {
            throw new Exception("Do you own the frickin' movie?");
        }

        movieRepository.save(movie);
    }

    public List<Movie> searchMovies(String movie) {
        return Lists.newArrayList(movieRepository.findByNameContainingIgnoreCaseOrderByNameAsc(movie));
    }
}
