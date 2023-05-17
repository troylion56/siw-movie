package it.uniroma3.siw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import antlr.collections.List;

@RestController
public class MovieRestController {

    @Autowired
    private MovieService movieService;

    @GetMapping(value="/rest/movie/{id}")
    public Movie getMovie(@PathVariable("id") Long id){
        return this.movieService.getMovie;
    }

    @GetMapping(value = "/rest/movie")
    public <Movie> getMovie(){
        return this.movieService.getMovie();
    }
    
}
