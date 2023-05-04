package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.News;

public interface NewsRepository extends CrudRepository<News, Long> {
    
}
