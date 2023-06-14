package simplon.fr.projetf.entity;



import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "anime")
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titre;
    private int episodes;
    private String auteur;
    private String diffusion;
    private String studio;

    @Column(length = 1000 )
    private String synopsis;


    public Anime(long id, String titre, int episodes, String auteur, String diffusion, String studio, String synopsis ) {
        this.id = id;
        this.titre = titre;
        this.episodes = episodes;
        this.auteur = auteur;
        this.diffusion = diffusion;
        this.studio = studio;
        this.synopsis = synopsis;

    }

    public Anime() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getDiffusion() {
        return diffusion;
    }

    public void setDiffusion(String diffusion) {
        this.diffusion = diffusion;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

}


