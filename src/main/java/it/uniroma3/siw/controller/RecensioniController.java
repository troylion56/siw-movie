package it.uniroma3.siw.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.repository.MovieRepository;
import it.uniroma3.siw.service.MovieService;
import it.uniroma3.siw.service.RecensioniService;

@Controller
public class RecensioniController {
    

    @Autowired
    private RecensioniService recensioneService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieService movieService;

    @GetMapping("/tutteLeRecensioni")
    public String paginaTutteRecensioni(Model model){
        return "/recensioni/tutteLeRecensioni.html";
    }

    @GetMapping("/formNewRecensione")
    public String formNewRecensione(Model model){
        model.addAttribute("recensione", new Recensione());
        model.addAttribute("movies", this.movieRepository.findAllOrderByIdAsc());
        return "/recensioni/formNewRecensione.html";
    }

    @PostMapping("/aggiungiRecensione")
    public String aggiungiRecensione(@ModelAttribute("recensione") Recensione recensione,HttpServletRequest request) {

        Map<String, String[]> parameterMap = request.getParameterMap();

        String idTendina = parameterMap.get("movie")[0];

        Movie movie = movieService.findMovieById(Long.parseLong(idTendina));
        recensione.setMovie(movie);
        recensioneService.salvaRecensione(recensione);


        return "tutteLeRecensioni.html";
    }
} 