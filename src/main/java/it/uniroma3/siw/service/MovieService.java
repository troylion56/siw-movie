package it.uniroma3.siw.service;

import java.util.List;
import java.util.Set;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.MovieRepository;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ArtistRepository artistRepository;


    
    @Transactional
    public void createNewMovie(Movie movie){
        this.movieRepository.save(movie);

    }

    public Movie findMovieById(Long id){
        return this.movieRepository.findById(id).orElse(null);
    }

    public Iterable<Movie> findAllMovie(){
        return this.movieRepository.findAll();
    }
    
    public Movie saveMovie(Movie movie){
        return this.movieRepository.save(movie);
    }

    public Movie saveDirectorToMovie(Long movieId, Long artistId){
        Movie res= null;
        Artist director = this.artistRepository.findById(artistId).orElse(null);
		Movie movie = this.findMovieById(movieId);
		if(movie!=null && director!=null){
			movie.setDirector(director);
		this.saveMovie(movie);
        res=movie;
		}
		return res;
    }

    public Movie saveActorToMovie(Long movieId, Long artistId){
        Movie movie = this.movieRepository.findById(movieId).orElse(null);
        Artist actor = this.artistRepository.findById(artistId).orElse(null);
        if(movie!=null && actor!=null){
            Set<Artist> actors = movie.getActors();
		    actors.add(actor);
		    return this.movieRepository.save(movie);
        }
        return movie;
    }

    public Movie removeActorFromMovie(Long movieId, Long artistId){
        Movie movie = this.movieRepository.findById(movieId).orElse(null);
		Artist actor = this.artistRepository.findById(artistId).orElse(null);
        if(movie!=null && actor!=null){
		    Set<Artist> actors = movie.getActors();
		    actors.remove(actor);
		    this.movieRepository.save(movie);
        }
        return movie;
    }


    public List<Movie> findByYear(Integer year){
        return this.movieRepository.findByYear(year);
    }
}
