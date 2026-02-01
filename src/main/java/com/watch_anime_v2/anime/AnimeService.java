package com.watch_anime_v2.anime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    public List<Anime> searchAnimeByName(String keyword) {
        return animeRepository.findByNameContainingIgnoreCase(keyword);
    }

    public List<Anime> getAllAnime() {
        return animeRepository.findAll();
    }

    public Anime getAnimeById(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Аниме не найдено"));
    }

    public Anime createAnime(Anime anime) {
        if(animeRepository.findByName(anime.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Аниме с таким именем уже существует");
        }
        return animeRepository.save(anime);
    }

    public Anime updateAnime(Long id, Anime updatedAnime) {
        Anime anime = getAnimeById(id);
        anime.setName(updatedAnime.getName());
        anime.setGenre(updatedAnime.getGenre());
        anime.setFormat(updatedAnime.getFormat());
        anime.setStatus(updatedAnime.getStatus());
        anime.setEpisodes(updatedAnime.getEpisodes());
        anime.setStudio(updatedAnime.getStudio());
        anime.setStartDate(updatedAnime.getStartDate());
        return animeRepository.save(anime);
    }

    public void deleteAnime(Long id) {
        Anime anime = getAnimeById(id);
        animeRepository.delete(anime);
    }
}
