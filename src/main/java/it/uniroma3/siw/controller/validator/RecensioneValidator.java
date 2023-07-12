package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.repository.RecensioneRepository;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RecensioneValidator implements Validator {

    @Autowired
    private RecensioneRepository recensioneRepository;
    @Override
    public boolean supports(Class<?> clazz) {
        return Recensione.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Recensione recensione = (Recensione) target;
        if(recensione.getTitle() != null && recensione.getText() != null && recensione.getRating() != null && recensione.getAuthor() != null
            && this.recensioneRepository.existsByAuthorAndTitleAndRatingAndText(recensione.getAuthor(),recensione.getTitle(),recensione.getRating(),recensione.getText())){
            errors.reject("review.duplicate");
        }
    }
}
