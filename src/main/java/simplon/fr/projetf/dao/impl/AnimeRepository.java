package simplon.fr.projetf.dao.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import simplon.fr.projetf.entity.Anime;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
}
