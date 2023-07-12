package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Review;


public interface ReviewRepository extends CrudRepository<Review,Long>{
    public boolean existsByAuthorAndTitleAndRatingAndText(String author,String title,Integer rating, String text);

    public Review findByAuthor(String author);
}