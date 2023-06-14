package simplon.fr.projetf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import simplon.fr.projetf.dao.AnimeDAO;
import simplon.fr.projetf.entity.Anime;

import java.util.List;


/**
 * Contrôleur de la liste des animés.
 */
@Controller
public class HomePageController {

    @Autowired
    private AnimeDAO animeDAO;

    // Affiche la page d'accueil
    @GetMapping({"/", "index", "index.html"})
    public String afficherAccueil(Model model) {
        List<Anime> all = animeDAO.findAllAnimes();
        model.addAttribute("animes", all);
        return "accueil";
    }

    // Supprime un anime de la base de données
    @PostMapping("/supprimer_anime/{id}")
    public String supprimerAnime(@PathVariable("id") Long id) {
        animeDAO.supprimerAnime(id);
        return "redirect:/";
    }

}