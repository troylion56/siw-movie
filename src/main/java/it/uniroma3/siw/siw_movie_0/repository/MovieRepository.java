package it.uniroma3.siw.siw_movie_0.repository;

import java.util.List;
import javax.print.event.PrintJobListener;
import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.siw_movie_0.model.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long>{
    public List<Movie> findByYear(Integer year);
    public boolean existsByTitleAndYear(String title, Integer year);
}
