package com.watch_anime_v2.auth.dto;

import com.watch_anime_v2.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
    private String email;
    private String username;
    private Role role;
    private String accessToken;
}