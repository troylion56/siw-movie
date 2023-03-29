package it.uniroma3.siw.siw_movie_0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import it.uniroma3.siw.siw_movie_0.repository.*;

@Controller
public class ArtistController {
    @Autowired ArtistRepository artistRepository;
    
}
