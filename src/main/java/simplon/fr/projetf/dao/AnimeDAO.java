package simplon.fr.projetf.dao;

import simplon.fr.projetf.entity.Anime;

import java.util.List;

public interface AnimeDAO {
    Anime createAnime(String titre, int episodes, String auteur, String diffusion, String studio, String synopsis);
    Anime updateAnime(Anime anime);

    Anime findAnimeById(long id );

    List<Anime> findAllAnimes();
    Anime supprimerAnime(Long id);



}




