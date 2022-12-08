package com.sparta.boardsa.service;

import com.sparta.boardsa.dto.BoardRequestDto;
import com.sparta.boardsa.dto.BoardResponseDto;
import com.sparta.boardsa.dto.ResponseDto;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor // db와 연결해야 하기 때문에 해당 어노테이션 추가

// ex) create 추가해야 하는 부분을 http에서 가져왔고, 컨트롤러에서 데이터 변환해서 객체 생성 -> 객체를 서비스 단으로 넘겨줌.
public class BoardService {

    // DB와 연결 역할 -> 레포지토리
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseEntity<BoardResponseDto> createBoard(BoardRequestDto requestDto, HttpServletRequest request) {
        // request에서 token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");

            }

            // 토큰에서 가져온 사용자 정보 -> DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );


            // 요청받은 DTO로 DB에 저장할 객체 만들기
            Board board = boardRepository.saveAndFlush(new Board(requestDto, user));
            return new ResponseEntity<>(new BoardResponseDto(board), HttpStatus.OK);
        } else {
            return null;
        }
    }

    // DB의 해당 테이블에 넣어줄 row를 객체 생성을 통해 만들어주었다고 생각하면 편함.
    // board 객체를 생성했는데, 데이터가 비어있다. 그래서 생성자를 생성해 requestDto 객체 넣어주고
    // get을 통해 board 클래스의 멤버 변수에 값을 초기화 시켜준다.
//        Board board = new Board(requestDto);
//        boardRepository.save(board);
//        // 반환 타입이 ResponseDto 기 때문에 new 연산자를 사용해준다.
//        // 왜? ct 에게 reponse 로 메시지를 넘겨줄 것이기 때문.
//        return new ResponseDto("게시글 작성 성공", HttpStatus.OK.value());

    @Transactional(readOnly = true)
    public List<ResponseEntity<BoardResponseDto>> getBoards(HttpServletRequest request) {

        List<ResponseEntity<BoardResponseDto>> boardList = new ArrayList<>();
        List<Board> boards = boardRepository.findAllByOrderByModifiedAtDesc();

        for (Board board : boards) {
            boardList.add(new ResponseEntity<>(new BoardResponseDto(board), HttpStatus.OK));
        }

        return boardList;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<BoardResponseDto> getBoard(Long id, HttpServletRequest request) {
        // request에서 token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰 -> 사용자 정보 확인
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보 -> DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 댓글가져오기 못 했음.. -> 머리가 돌아가지 않는다..
//            Board board = new Board();
//            if (user.getRole() == UserRoleEnum.ADMIN) {
//                board.getCommentList() = commentRepository.findAllByBoardId(board.getId());
//
//            }

            Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
            );

            BoardResponseDto boardResponseDto = new BoardResponseDto(board);

            // 요청받은 DTO로 DB에 저장할 객체 만들기
            return new ResponseEntity<>(boardResponseDto, HttpStatus.OK);
        } else {
            return null;
        }
    }


    @Transactional
    public ResponseEntity<BoardResponseDto> update(Long id, BoardRequestDto requestDto, HttpServletRequest request) {
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

            Board board = checkBoard(id, user);

            // 댓글 작성자와 현재 유저가 일치하는 지 확인
            if (!(board.getUsername().equals(user.getUsername()) && user.getRole() != UserRoleEnum.ADMIN)) {
                throw new IllegalArgumentException("수정 권한이 없습니다.");
            }

            // USER, 작성자 일치 -> 수정, ADMIN -> 수정
            board.update(requestDto);

            return new ResponseEntity<>(new BoardResponseDto(board), HttpStatus.OK);
        } else {
            return null;
        }
    }


    @Transactional
    public ResponseEntity<?> deleteBoard(Long id, HttpServletRequest request) {
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

            Board board = checkBoard(id, user);

            if (!(board.getUsername().equals(user.getUsername()) && user.getRole() != UserRoleEnum.ADMIN)) {
                throw new IllegalArgumentException("삭제 권한이 없습니다.");
            }

            // USER, 작성자 일치 -> 삭제, ADMIN -> 삭제
            boardRepository.delete(board);

            return new ResponseEntity<>("게시글 삭제 완료", HttpStatus.OK);
        } else {
            return null;
        }
    }

    private Board checkBoard(Long id, User user) {
        return boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                () -> new RuntimeException("해당 게시글에 권한이 없습니다.")
        );
    }
}
