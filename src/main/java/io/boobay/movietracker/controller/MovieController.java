package io.boobay.movietracker.controller;

import io.boobay.movietracker.bo.Movie;
import io.boobay.movietracker.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping(value = "/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, @RequestParam(value = "movies", required = false) List<Movie> movies, @RequestParam(value = "search", required = false) String search, @RequestParam(value = "owned", required = false) Boolean owned) {
        if (movies == null || movies.size() == 0) {
            if (owned == null) {
                movies = movieService.findMovies();
            } else {
                movies = movieService.findMovies(owned);
            }
        }

        model.addAttribute("movies", movies);


        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("search", search);
        }

        return "movies";
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public String details(@RequestParam("id") Long id, Model model) {
        System.out.println(id);
        Movie movie = null;
        try {
            movie = movieService.findMovie(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("movie", movie);
        return "details";
    }

    @RequestMapping(value = "/details", method = RequestMethod.POST)
    public String updateDetails(@RequestParam("id") Long id, @ModelAttribute Movie movie, Model model) {
        System.out.println(movie.getId());
        System.out.println(movie.getName());
        System.out.println(movie.getOwned());
        try {
            movieService.updateMovie(movie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/movies/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addMovieGet(Model model) {
        Movie movie = new Movie();
        model.addAttribute("movie", movie);

        return "add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addMoviePost(@Valid @ModelAttribute Movie movie, Model model) {
        System.out.println(movie.getId());
        System.out.println(movie.getName());
        System.out.println(movie.getOwned());
        try {
            movieService.addMovie(movie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/movies/list";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(Model model, @RequestParam("movie") String movie, RedirectAttributes attributes) {
        System.out.println(movie);
        if (movie != null && !movie.isEmpty()) {
            attributes.addAttribute("movies", movieService.searchMovies(movie));
            attributes.addAttribute("search", movie);
        }
        return "redirect:/movies/list";
    }

}