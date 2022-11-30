package com.sparta.boardsa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardRequestDto {
    private String username;
    private String contents;
    private String password;
    private String title;
}
