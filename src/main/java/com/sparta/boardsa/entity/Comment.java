package com.sparta.boardsa.entity;

import com.sparta.boardsa.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@Entity(name = "comment")
public class Comment extends TimeStamp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String comment;
    @Column(nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public Comment(CommentRequestDto requestDto, Board board) {
        this.username = board.getUsername();
        this.comment = requestDto.getComment();

        // board, comment 연관관계 설정 -> 양방향
        this.board = board;
        board.getCommentList().add(this);
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}
