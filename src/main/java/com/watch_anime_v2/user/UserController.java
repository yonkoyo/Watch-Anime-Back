package com.watch_anime_v2.user;

import com.watch_anime_v2.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/{userId}/anime/{animeId}")
    public UserResponseDto addAnime(@PathVariable Long userId, @PathVariable Long animeId) {
        return userService.addAnimeToCollection(userId, animeId);
    }

    @DeleteMapping("/{userId}/anime/{animeId}")
    public UserResponseDto removeAnime(@PathVariable Long userId, @PathVariable Long animeId) {
        return userService.removeAnimeFromCollection(userId, animeId);
    }

    @GetMapping("/{userId}/anime")
    public List<String> getAnimeCollection(@PathVariable Long userId) {
        return userService.getUserAnimeCollection(userId);
    }

    @GetMapping("/search")
    public UserResponseDto getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }
}
