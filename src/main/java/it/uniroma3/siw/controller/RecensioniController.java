package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.repository.MovieRepository;
import it.uniroma3.siw.repository.RecensioneRepository;

public class RecensioniController {
    

    @Autowired
    private RecensioneRepository recensioneRepository;
    /* 
    @GetMapping(value="/recensioni/formNewRecensione")
    public String formNewRecensione(Model model){
        model.addAttribute("recensione", new Recensione());
        return "recensioni/formNewRecensione.html";
    }*/

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/formNewRecensione")
    public String showNewRecensioneForm(Model model) {
        List<Movie> movies = movieRepository.findAll();
        model.addAttribute("movies", movies);
        model.addAttribute("recensione", new Recensione());
        return "formNewRecensione";
    }
}