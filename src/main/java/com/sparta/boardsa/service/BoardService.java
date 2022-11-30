package com.sparta.boardsa.service;

import com.sparta.boardsa.dto.BoardListResponseDto;
import com.sparta.boardsa.dto.BoardRequestDto;
import com.sparta.boardsa.dto.BoardResponseDto;
import com.sparta.boardsa.dto.ResponseDto;
import com.sparta.boardsa.entity.Board;
import com.sparta.boardsa.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor // db와 연결해야 하기 때문에 해당 어노테이션 추가

// ex) create 추가해야 하는 부분을 http에서 가져왔고, 컨트롤러에서 데이터 변환해서 객체 생성 -> 객체를 서비스 단으로 넘겨줌.
public class BoardService {

    // DB와 연결 역할 -> 레포지토리
    private final BoardRepository boardRepository;

    @Transactional
    public ResponseDto createBoard(BoardRequestDto requestDto) {
        // DB의 해당 테이블에 넣어줄 row를 객체 생성을 통해 만들어주었다고 생각하면 편함.
        // board 객체를 생성했는데, 데이터가 비어있다. 그래서 생성자를 생성해 requestDto 객체 넣어주고
        // get을 통해 board 클래스의 멤버 변수에 값을 초기화 시켜준다.
        Board board = new Board(requestDto);
        boardRepository.save(board);
        // 반환 타입이 ResponseDto 기 때문에 new 연산자를 사용해준다.
        // 왜? ct 에게 reponse 로 메시지를 넘겨줄 것이기 때문.
        return new ResponseDto("게시글 작성 성공", HttpStatus.OK.value());
    }

    @Transactional(readOnly = true)
    public List<Board> getBoards() {
        return boardRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional(readOnly = true)
    public BoardResponseDto getBoard(Long id) {
        // findById 는 Optional이기 때문에 null 체크를 해줘야 한다.
        Board board = checkBoard(id);
        return new BoardResponseDto(board);
    }

    @Transactional
    public ResponseEntity<?> update(Long id, BoardRequestDto requestDto) {
        Board board = checkBoard(id);

        if (requestDto.getPassword().equals(board.getPassword())) {
            board.update(requestDto);
            return ResponseEntity.ok(new BoardResponseDto(board));
        } else {
            // AOP 공부해보기, 제네릭스 공부해보기
            return ResponseEntity.badRequest().body(new ResponseDto("수정 권한이 없습니다.", HttpStatus.BAD_REQUEST.value()));
            // return new BoardResponseDto(new ResponseDto("게시글 수정 실패", HttpStatus.BAD_REQUEST.value()));
        }
    }

    @Transactional
    public ResponseDto deleteBoard(Long id, BoardRequestDto requestDto) {
        Board board = checkBoard(id);
        System.out.println(requestDto.getPassword());
        if (requestDto.getPassword().equals(board.getPassword())) {
            // jpa delete 메서드 존재
            boardRepository.delete(board);
            return new ResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
        } else {
            return new ResponseDto("삭제 권한이 없습니다.", HttpStatus.BAD_REQUEST.value());
        }
    }

    private Board checkBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 게시글이 존재하지 않습니다.")
        );
    }
}
