package com.sparta.boardsa.dto;

import com.sparta.boardsa.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private String username;
    private String contents;
    private String password;
    private String title;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public BoardResponseDto(Board board) {
        this.username = board.getUsername();
        this.contents = board.getContents();
        this.password = board.getPassword();
        this.title = board.getTitle();
        this.createAt = board.getCreateAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
