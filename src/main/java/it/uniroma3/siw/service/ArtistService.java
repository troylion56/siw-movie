package it.uniroma3.siw.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.repository.ArtistRepository;

@Service
public class ArtistService {

    @Autowired 
	private ArtistRepository artistRepository;


    @Transactional
    public boolean createNewArtist(Artist artist){
        boolean res=true;
        if(!artistRepository.existsByNameAndSurname(artist.getName(), artist.getSurname())) {
            res=true;
            artistRepository.save(artist);
        }
        return res;
    }
}
