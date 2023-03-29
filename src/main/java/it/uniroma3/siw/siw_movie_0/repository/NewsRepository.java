package it.uniroma3.siw.siw_movie_0.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.siw_movie_0.model.News;

public interface NewsRepository extends CrudRepository<News, Long> {
    
}
