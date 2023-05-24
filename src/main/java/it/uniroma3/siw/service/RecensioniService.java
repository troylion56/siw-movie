package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.repository.RecensioneRepository;

@Service
public class RecensioniService {

    @Autowired
    private RecensioneRepository recensioneRepository;

    public void salvaRecensione(Recensione recensione) {
        recensioneRepository.save(recensione);
    }
}
