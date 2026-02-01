package com.watch_anime_v2.user;

import com.watch_anime_v2.anime.Anime;
import com.watch_anime_v2.anime.AnimeRepository;
import com.watch_anime_v2.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AnimeRepository animeRepository;


    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
        return mapToDto(user);
    }

    public UserResponseDto updateUser(Long userId, User updatedUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

        user.setEmail(updatedUser.getEmail());
        user.setUsername(updatedUser.getUsername());
        user.setRole(updatedUser.getRole());

        userRepository.save(user);
        return mapToDto(user);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
        userRepository.delete(user);
    }



    public UserResponseDto addAnimeToCollection(Long userId, Long animeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Аниме не найдено"));

        user.getAnimeCollection().add(anime);
        userRepository.save(user);
        return mapToDto(user);
    }

    public UserResponseDto removeAnimeFromCollection(Long userId, Long animeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Аниме не найдено"));

        user.getAnimeCollection().remove(anime);
        userRepository.save(user);
        return mapToDto(user);
    }

    public List<String> getUserAnimeCollection(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
        return user.getAnimeCollection()
                .stream()
                .map(Anime::getName)
                .collect(Collectors.toList());
    }


    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
        return mapToDto(user);
    }


    private UserResponseDto mapToDto(User user) {
        List<String> animeNames = user.getAnimeCollection() != null
                ? user.getAnimeCollection().stream().map(Anime::getName).collect(Collectors.toList())
                : List.of();

        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole(),
                animeNames
        );
    }
}
