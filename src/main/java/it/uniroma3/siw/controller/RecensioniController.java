package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.repository.MovieRepository;
import it.uniroma3.siw.service.RecensioniService;

@Controller
public class RecensioniController {
    

    @Autowired
    private RecensioniService recensioneService;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/tutteLeRecensioni")
    public String paginaTutteRecensioni(Model model){
        System.out.println("RECENSIONI");
        return "/recensioni/tutteLeRecensioni.html";
    }

    @GetMapping("/formNewRecensione")
    public String formNewRecensione(Model model){
        model.addAttribute("recensione", new Recensione());
        model.addAttribute("movies", this.movieRepository.findAll());
        return "/recensioni/formNewRecensione.html";
    }

    @PostMapping("/aggiungiRecensione")
    public String aggiungiRecensione(@ModelAttribute("recensione") Recensione recensione) {
        recensioneService.salvaRecensione(recensione);
        return "test.html";
    }

}