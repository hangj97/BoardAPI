package com.sparta.boardsa.controller;


import com.sparta.boardsa.dto.CommentRequestDto;
import com.sparta.boardsa.dto.CommentResponseDto;
import com.sparta.boardsa.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{board_id}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long board_id, @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        return commentService.createComment(board_id, requestDto, request);
    }

    @PutMapping("{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        return commentService.update(id, requestDto, request);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, HttpServletRequest request) {
        return commentService.deleteComment(id, request);
    }

}
