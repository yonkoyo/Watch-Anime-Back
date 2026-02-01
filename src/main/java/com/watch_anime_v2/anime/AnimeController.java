package com.watch_anime_v2.anime;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anime")
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeService animeService;

    @GetMapping("/search")
    public ResponseEntity<List<Anime>> searchAnime(@RequestParam String keyword) {
        return ResponseEntity.ok(animeService.searchAnimeByName(keyword));
    }

    @GetMapping
    public ResponseEntity<List<Anime>> getAll() {
        return ResponseEntity.ok(animeService.getAllAnime());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anime> getById(@PathVariable Long id) {
        return ResponseEntity.ok(animeService.getAnimeById(id));
    }

    @PostMapping
    public ResponseEntity<Anime> create(@RequestBody Anime anime) {
        return ResponseEntity.ok(animeService.createAnime(anime));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Anime> update(@PathVariable Long id, @RequestBody Anime anime) {
        return ResponseEntity.ok(animeService.updateAnime(id, anime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        animeService.deleteAnime(id);
        return ResponseEntity.noContent().build();
    }
}
