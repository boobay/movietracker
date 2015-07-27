package io.boobay.movietracker.repository;

import io.boobay.movietracker.bo.Movie;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by boobay on 7/26/15.
 */

public interface MovieRepository extends CrudRepository<Movie, Long> {

    List<Movie> findAll(Sort sort);

    List<Movie> findByNameContainingIgnoreCaseOrderByNameAsc(String movie);

    List<Movie> findByOwnedOrderByNameAsc(Boolean owned);
}
