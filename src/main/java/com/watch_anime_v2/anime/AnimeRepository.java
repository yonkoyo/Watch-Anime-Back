package com.watch_anime_v2.anime;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

    Optional<Anime> findByName(String name);

    List<Anime> findByNameContainingIgnoreCase(String keyword);
}
