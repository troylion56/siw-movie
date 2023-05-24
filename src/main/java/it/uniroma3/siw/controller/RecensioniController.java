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

    @GetMapping(value="/recensioni/formNewRecensione")
    public String formNewRecensione(Model model){
        model.addAttribute("recensione", new Recensione());
        return "formNewRecensione.html";
    }

    @GetMapping("/recensioni/tutteLeRecensioni")
    public String paginaTutteRecensioni(Model model){
        return "/recensioni/tutteLeRecensioni.html";
    }
}