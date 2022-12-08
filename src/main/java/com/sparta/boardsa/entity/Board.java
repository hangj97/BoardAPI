package com.sparta.boardsa.entity;

import com.sparta.boardsa.dto.BoardRequestDto;
import com.sparta.boardsa.dto.LoginRequestDto;
import com.sparta.boardsa.dto.ResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity (name = "board")
@Getter
@NoArgsConstructor
public class Board extends TimeStamp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column
    private String contents;
    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 댓글 넣어볼라 했는데 시간도 없고, 어떻게 구현할 지 감은 오는데.,
    // 생각한 것처럼 되지는 않는다..
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();


    public Board(BoardRequestDto boardRequestDto, User user) {
        this.username = user.getUsername();
        this.contents = boardRequestDto.getContents();
        this.title = boardRequestDto.getTitle();

        this.user = user;
        user.getBoardList().add(this);
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.contents = boardRequestDto.getContents();
    }
}
