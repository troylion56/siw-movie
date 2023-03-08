package it.uniroma3.siw.siw_movie_0.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.siw_movie_0.repository.MovieRepository;
import it.uniroma3.siw.siw_movie_0.model.Movie;

@Controller
public class MovieController {
    @Autowired MovieRepository movieRepository;

    @GetMapping("/formNewMovie")
    public String formNewMovie(Model model){
        model.addAttribute("movie", new Movie());
        return "formNewMovie.html";
    }

    @PostMapping("/movies")
    public String newMovie (@ModelAttribute("movie") Movie movie, Model model ) {
        if(!movieRepository.existsByTitleAndYear(movie.getTitle(), movie.getYear())) {
            this.movieRepository.save(movie);
            model.addAttribute("movie", movie);
            return "movie.html";
        }
        else {
            model.addAttribute("messaggioErrore", "Questo film esiste gia");
            return "formNewMovie.htlm";
        }
    }

    @GetMapping("/movies/{id}")
    public String getMovie(@PathVariable("id") Long id, Model model) {
        model.addAttribute("movie", this.movieRepository.findById(id).get());
        return "movie.htlm";
    }

    @GetMapping("/movies")
    public String showMovies(Model model) {
        model.addAttribute("movies", this.movieRepository.findAll());
        return "movies.html";
    }

    @GetMapping("/formSerchMovies")
    public String formSearchMovies() {
        return "formSearchMovies.html";
    }

    @PostMapping("/searchMovies")
    public String searchMovies(Model model,@RequestParam Integer year) {
        model.addAttribute("movies", this.movieRepository.findByYear(year));
        return "foundMovies.html";
    }    
}
