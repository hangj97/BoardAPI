package com.sparta.boardsa.entity;

import com.sparta.boardsa.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Board extends TimeStamp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String contents;
    private String title;
    private String password;

    public Board(BoardRequestDto boardRequestDto) {
        this.username = boardRequestDto.getUsername();
        this.contents = boardRequestDto.getContents();
        this.title = boardRequestDto.getTitle();
        this.password = boardRequestDto.getPassword();
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.username = boardRequestDto.getUsername();
        this.title = boardRequestDto.getTitle();
        this.contents = boardRequestDto.getContents();
    }
}
