package it.uniroma3.siw.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.repository.MovieRepository;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;


    
    @Transactional
    public void createNewMovie(Movie movie){
        this.movieRepository.save(movie);

    }

    public Movie findMovieById(Long id){
        return this.movieRepository.findById(id).orElse(null);
    }

    
}
