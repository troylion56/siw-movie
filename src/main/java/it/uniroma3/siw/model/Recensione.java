
package it.uniroma3.siw.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
public class Recensione {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String author; 

    @NotBlank
    private String title;

    @Min(1)
    @Max(5)
    private Integer rating;

    @NotBlank
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recensione recensione = (Recensione) o;
        return Objects.equals(id, recensione.id) && Objects.equals(author, recensione.author) && Objects.equals(title, recensione.title) && Objects.equals(rating, recensione.rating) && Objects.equals(text, recensione.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, title, rating, text);
    }
}
