package it.uniroma3.siw.siw_movie_0.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.siw_movie_0.model.Artist;;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
    public boolean existByNameAndUsername(String name,String username);
}
