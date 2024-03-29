package it.uniroma3.siw.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.MovieValidator;
import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.service.ArtistService;
import it.uniroma3.siw.service.MovieService;
import jakarta.validation.Valid;

@Controller
public class MovieController {

	@Autowired 
	private MovieValidator movieValidator;

	@Autowired 
	private MovieService movieService;

	@Autowired 
	private ArtistService artistService;

	@Autowired
    private GlobalController globalController;

	@GetMapping(value="/admin/formNewMovie")
	public String formNewMovie(Model model) {
		model.addAttribute("movie", new Movie());
		return "admin/formNewMovie.html";
	}

	@GetMapping(value="/admin/formUpdateMovie/{id}")
	public String formUpdateMovie(@PathVariable("id") Long id, Model model) {
		Movie movie=movieService.findMovieById(id);
		if(movie!=null)
			model.addAttribute("movie", movie);
		else{
			return "movieError.html";
		}
		return "admin/formUpdateMovie.html";
	}

	@GetMapping(value="/admin/indexMovie")
	public String indexMovie() {
		return "admin/indexMovie.html";
	}

	@GetMapping(value="/admin/manageMovies")
	public String manageMovies(Model model) {
		model.addAttribute("movies", this.movieService.findAllMovie());
		return "admin/manageMovies.html";
	}

	@GetMapping(value="/admin/setDirectorToMovie/{directorId}/{movieId}")
	public String setDirectorToMovie(@PathVariable("directorId") Long directorId, @PathVariable("movieId") Long movieId, Model model) {
		Movie movie= this.movieService.saveDirectorToMovie(movieId, directorId);
		if(movie!=null){
			model.addAttribute("movie", movie);
		return "admin/formUpdateMovie.html";
		}
		else{
			return "movieError.html";
		}
	}
	
	@GetMapping(value="/admin/addDirector/{id}")
	public String addDirector(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artists", artistService.findAllArtist());
		Movie movie= movieService.findMovieById(id);
		if(movie!=null){
	        model.addAttribute("movie", movie);
		    return "admin/directorsToAdd.html";
		}
		else{
			return "movieError.html";
		}
	}

	@PostMapping("/admin/movie")
	public String newMovie(Model model, @Valid @ModelAttribute("movie") Movie movie, BindingResult bindingResult,
            @RequestParam("file") MultipartFile image) throws IOException {
        this.movieValidator.validate(movie, bindingResult);
        if (!bindingResult.hasErrors()) {
			this.movieService.createMovie(movie, image);
            return this.movieService.function(model, movie, globalController.getUser());
        } else {
            return "/admin/formNewMovie.html";
        }
    }

	@GetMapping("/movie/{id}")
	public String getMovie(@PathVariable("id") Long id, Model model) {
		Movie movie= movieService.findMovieById(id);
		return this.movieService.function(model, movie, this.globalController.getUser());
	}

	@GetMapping("/movie")
	public String getMovies(Model model) {
    	model.addAttribute("movies", this.movieService.findAllMovie());
    	return "movies.html";
	}

	@GetMapping("/formSearchMovies")
	public String formSearchMovies() {
		return "formSearchMovies.html";
	}

	@PostMapping("/searchMovies")
	public String searchMovies(Model model, @RequestParam int year) {
		model.addAttribute("movies", this.movieService.findByYear(year));
		return "foundMovies.html";
	}
	
	@GetMapping("/admin/updateActors/{id}")
	public String updateActors(@PathVariable("id") Long id, Model model) {

		List<Artist> actorsToAdd = this.artistService.findActorsNotInMovie(id);
		model.addAttribute("actorsToAdd", actorsToAdd);
		Movie movie=this.movieService.findMovieById(id);
		if(movie!=null){
			model.addAttribute("movie", movie);
			return "admin/actorsToAdd.html";
		}
		else {
			return "movieError.html";
		}
	}

	@GetMapping(value="/admin/addActorToMovie/{actorId}/{movieId}")
	public String addActorToMovie(@PathVariable("actorId") Long actorId, @PathVariable("movieId") Long movieId, Model model) {
		Movie movie = this.movieService.saveActorToMovie(movieId, actorId);
		if(movie!=null){
			List<Artist> actorsToAdd = this.artistService.findActorsNotInMovie(movieId);
		    model.addAttribute("movie", movie);
		    model.addAttribute("actorsToAdd", actorsToAdd);
            return "admin/actorsToAdd.html";
		}
		else {
			return "movieError.html";
		}
	}
	
	@GetMapping(value="/admin/removeActorFromMovie/{actorId}/{movieId}")
	public String removeActorFromMovie(@PathVariable("actorId") Long actorId, @PathVariable("movieId") Long movieId, Model model) {
		Movie movie =movieService.removeActorFromMovie(movieId, actorId);
		if(movie !=null){
			List<Artist> actorsToAdd = this.artistService.findActorsNotInMovie(movieId);
		    model.addAttribute("movie", movie);
		    model.addAttribute("actorsToAdd", actorsToAdd);
            return "admin/actorsToAdd.html";
		}
		else {
			return "movieError.html";
		}
	}
}
