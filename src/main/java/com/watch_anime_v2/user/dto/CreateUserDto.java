package com.watch_anime_v2.user.dto;

import com.watch_anime_v2.user.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
    private String email;
    private String username;
    private String password;
    private Role role;
}
