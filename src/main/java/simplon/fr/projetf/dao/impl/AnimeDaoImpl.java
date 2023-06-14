package simplon.fr.projetf.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import simplon.fr.projetf.dao.AnimeDAO;
import simplon.fr.projetf.entity.Anime;



import java.util.List;

@Repository
public class AnimeDaoImpl implements AnimeDAO {

    @Autowired
    private AnimeRepository animeRepository;

    @Override
    public Anime createAnime(String titre, int episodes, String auteur, String diffusion, String studio, String synopsis) {
        Anime anime = new Anime();
        anime.setTitre(titre);
        anime.setEpisodes(episodes);
        anime.setAuteur(auteur);
        anime.setDiffusion(diffusion);
        anime.setStudio(studio);
        anime.setSynopsis(synopsis);
        animeRepository.save(anime);
        return anime;


    }

    @Override
    public Anime updateAnime(Anime anime) {
        return animeRepository.save(anime);
    }

    @Override
    public Anime findAnimeById(long id) {

        return animeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Anime> findAllAnimes() {

        return animeRepository.findAll();
    }

    @Override
    public Anime supprimerAnime(Long id) {
        Anime anime = animeRepository.findById(id).orElse(null);
        if (anime != null) {
            animeRepository.delete(anime);
        }
        return anime;


    }






}


