package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.ReviewValidator;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.repository.MovieRepository;
import it.uniroma3.siw.repository.ReviewRepository;
import it.uniroma3.siw.service.MovieService;
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
public class ReviewController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieService movieService;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewValidator reviewValidator;
    @Autowired
    private GlobalController globalController;

   @PostMapping("/user/uploadReview/{movieId}")
    public String newReview(Model model, @Valid @ModelAttribute("review") Review review, BindingResult bindingResult, @PathVariable("movieId") Long id) {
        this.reviewValidator.validate(review,bindingResult);
        Movie movie = this.movieRepository.findById(id).get();
        if(!bindingResult.hasErrors()){
            if(this.globalController.getUser() != null && !movie.getReviews().contains(review)){
                review.setAuthor(this.globalController.getUser().getUsername());
                this.reviewRepository.save(review);
                movie.getReviews().add(review);
            }
        }
        this.movieRepository.save(movie);

        return this.movieService.function(model, movie, this.globalController.getUser());
    } 

    @GetMapping("/admin/deleteReview/{movieId}/{reviewId}")
    public String removeReview(Model model, @PathVariable("movieId") Long movieId,@PathVariable("reviewId") Long reviewId){
        Movie movie = this.movieRepository.findById(movieId).get();
        Review review = this.reviewRepository.findById(reviewId).get();

        movie.getReviews().remove(review);
        this.reviewRepository.delete(review);
        this.movieRepository.save(movie);
        return this.movieService.function(model, movie, this.globalController.getUser());
    }
}
