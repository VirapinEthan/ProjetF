package simplon.fr.projetf.controller.users.admin;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import simplon.fr.projetf.dao.AnimeDAO;

import simplon.fr.projetf.entity.Anime;


/**
 * Contrôleur CRUD pour les animés.
 */

@Controller
public class AnimeController {


    @Autowired
    private AnimeDAO animeDAO;

    @GetMapping("/Ajout")
    public String afficherAjout(Model model) {
        model.addAttribute("anime", new Anime());
        return "/admin/ajout";

    }

    @PostMapping("/ajout")
    public String ajouterAnime(@ModelAttribute("anime") Anime anime) {
        Anime saveAnime = animeDAO.createAnime(anime.getTitre(), anime.getEpisodes(), anime.getAuteur(),anime.getDiffusion(),anime.getStudio(), anime.getSynopsis());
        return "redirect:/page_detail/"+ saveAnime.getId();
    }


    /**
     * Page des details d'un animés.
     *
     * @param id L'identifiant de l'anime.
     * @param model     Modèle Thymeleaf.
     * @return La page de details d'un anime.
     */
    @GetMapping({"/page_detail/{id}"})
    public String afficherAnime (@PathVariable long id , Model model) {
       Anime anime = animeDAO.findAnimeById(id);
        model.addAttribute("anime", anime);
        return "page_detail";

    }

    @GetMapping("/admin/updateAnime/{id}")
    public String updateAnimePage(@PathVariable("id") Long id, Model model) {
        Anime anime = animeDAO.findAnimeById(id);

        if (anime == null) {
            return "error_page";
        }

        model.addAttribute("anime", anime);
        return "/admin/updateAnime";
    }

@PostMapping("/admin/updateAnime/{id}")
    public String updateAnime(@PathVariable("id") Long id, @ModelAttribute("anime") Anime updatedAnime, Model model) {
        Anime anime = animeDAO.findAnimeById(id);

        if (anime == null) {
            return "error_page";
        }

        anime.setTitre(updatedAnime.getTitre());
        anime.setEpisodes(updatedAnime.getEpisodes());
        anime.setAuteur(updatedAnime.getAuteur());
        anime.setDiffusion(updatedAnime.getDiffusion());
        anime.setStudio(updatedAnime.getStudio());
        anime.setSynopsis(updatedAnime.getSynopsis());

        animeDAO.updateAnime(anime);


        model.addAttribute("anime", anime);
        model.addAttribute("succes",true);

        return "/admin/updateAnime";
    }


}

