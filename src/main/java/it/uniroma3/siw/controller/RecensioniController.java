package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.RecensioneValidator;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.repository.MovieRepository;
import it.uniroma3.siw.repository.RecensioneRepository;
import it.uniroma3.siw.service.MovieService;
import it.uniroma3.siw.service.RecensioniService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class RecensioniController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieService movieService;

    @Autowired
    private GlobalController globalController;

    @Autowired
    private RecensioneValidator recensioneValidator;

    @Autowired
    private RecensioniService recensioniService;

    @Autowired
    private RecensioneRepository recensioneRepository;

    @PostMapping("/user/uploadReview/{movieId}")
    public String newReview(Model model, @Valid @ModelAttribute("review") Recensione review, BindingResult bindingResult, @PathVariable("movieId") Long id) {
        this.recensioneValidator.validate(review,bindingResult);
        Movie movie = this.movieRepository.findById(id).get();
        if(!bindingResult.hasErrors()){
            if(this.globalController.getUser() != null && !movie.getReviews().contains(review)){
                review.setAuthor(this.globalController.getUser().getUsername());
                this.recensioneRepository.save(review);
                movie.getReviews().add(review);
            }
        }
        this.movieRepository.save(movie);

        return this.movieService.function(model, movie, this.globalController.getUser());
    }
} 