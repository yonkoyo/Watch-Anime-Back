package com.watch_anime_v2.user.dto;

import com.watch_anime_v2.anime.Anime;
import com.watch_anime_v2.user.Role;
import com.watch_anime_v2.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private String username;
    private Role role;
    private List<String> animeCollection;

    public static UserResponseDto fromEntity(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        dto.setAnimeCollection(
                user.getAnimeCollection()
                        .stream()
                        .map(Anime::getName)
                        .collect(Collectors.toList())
        );
        return dto;
    }
}
