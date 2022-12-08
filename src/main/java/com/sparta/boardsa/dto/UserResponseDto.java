package com.sparta.boardsa.dto;

import com.sparta.boardsa.entity.User;
import com.sparta.boardsa.entity.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String username;
    private UserRoleEnum role;

    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
