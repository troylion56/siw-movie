package it.uniroma3.siw.siw_movie_0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import it.uniroma3.siw.siw_movie_0.model.Artist;
import it.uniroma3.siw.siw_movie_0.repository.*;

@Controller
public class ArtistController {
    @Autowired ArtistRepository artistRepository;
    

    @GetMapping("/artistS/{id}")
	public String getMovie(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artist", this.artistRepository.findById(id).get());
		return "artists.html";
	}

    @PostMapping("/artist")
    public String newArtist(@ModelAttribute("artist")Artist artist, Model model){
        if(!artistRepository.existsByNameAndUsername(artist.getName(), artist.getUsername())) {
            this.artistRepository.save(artist);
            model.addAttribute("artist", artist);
            return "artist.html";
        }
        else{
            model.addAttribute("messaggioErrore", "Questo artista esiste gia");
            return "formNewArtist.html";
        }
    }

    @GetMapping("/formNewArtist")
    public String forrmNewArtist(Model model) {
        model.addAttribute("artist", new Artist());
        return "formNewArtist.html";
    }
}
