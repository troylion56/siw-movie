package it.uniroma3.siw.repository;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Recensione;

public interface RecensioneRepository extends CrudRepository<Recensione, Long> {
    public boolean existsByAuthorAndTitleAndRatingAndText(String author,String title,Integer rating, String text);
}
