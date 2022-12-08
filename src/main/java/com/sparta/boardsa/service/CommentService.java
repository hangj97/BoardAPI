package com.sparta.boardsa.service;

import com.sparta.boardsa.dto.CommentRequestDto;
import com.sparta.boardsa.dto.CommentResponseDto;
import com.sparta.boardsa.entity.Board;
import com.sparta.boardsa.entity.Comment;
import com.sparta.boardsa.entity.User;
import com.sparta.boardsa.entity.UserRoleEnum;
import com.sparta.boardsa.jwt.JwtUtil;
import com.sparta.boardsa.repository.BoardRepository;
import com.sparta.boardsa.repository.CommentRepository;
import com.sparta.boardsa.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;

    // 댓글 작성
    public ResponseEntity<CommentResponseDto> createComment(Long board_id, CommentRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Board board = boardRepository.findById(board_id).orElseThrow(
                    () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
            );

            Comment comment = new Comment(requestDto, board);
            commentRepository.saveAndFlush(comment);
            System.out.println("commentUsername = " + comment.getUsername());
            System.out.println("commentComment = " + comment.getComment());
            System.out.println("comment.getId() = " + comment.getId());

            return new ResponseEntity<>(new CommentResponseDto(comment), HttpStatus.OK);
        } else {
            return null;
        }
    }

    // 댓글 수정
    @Transactional
    public ResponseEntity<CommentResponseDto> update(Long id, CommentRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new NullPointerException("해당 댓글이 존재하지 않습니다.")
            );

            // 댓글 작성자와 현재 유저가 일치하는 지 확인
            if (!(comment.getUsername().equals(user.getUsername()) && user.getRole() != UserRoleEnum.ADMIN)) {
                throw new IllegalArgumentException("수정 권한이 없습니다.");
            }

            // USER, 작성자 일치 -> 수정, ADMIN -> 수정
            comment.update(requestDto);
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);

            return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
        } else {
            return null;
        }
    }

    // 댓글 삭제
    @Transactional
    public ResponseEntity<?> deleteComment(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new NullPointerException("해당 댓글이 존재하지 않습니다.")
            );

            // 댓글 작성자와 현재 유저가 일치하는 지 확인
            if (!(comment.getUsername().equals(user.getUsername()) && user.getRole() == UserRoleEnum.USER)) {
                throw new IllegalArgumentException("삭제 권한이 없습니다.");
            }

            // USER, 작성자 일치 -> 삭제, ADMIN -> 삭제
            commentRepository.deleteById(id);

            return new ResponseEntity<>("댓글 삭제 완료", HttpStatus.OK);
        } else {
            return null;
        }
    }
}
