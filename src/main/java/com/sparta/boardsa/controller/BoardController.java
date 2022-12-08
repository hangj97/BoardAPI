package com.sparta.boardsa.controller;

import com.sparta.boardsa.dto.BoardRequestDto;
import com.sparta.boardsa.dto.BoardResponseDto;
import com.sparta.boardsa.dto.ResponseDto;
import com.sparta.boardsa.entity.Board;
import com.sparta.boardsa.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 컨트롤러 -> http 요청 받음 -> 데이터를 변환 후 가져옴 -> 서비스로 전달 -> 로직 수행 -> 수행 후 넘어오는 데이터 ct 에게 전달

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public List<ResponseEntity<BoardResponseDto>> getBoards(HttpServletRequest request) {
        return boardService.getBoards(request);
    }

    @GetMapping("/board/{id}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long id, HttpServletRequest request) {
        return boardService.getBoard(id, request);
    }

    @PostMapping("/board")
    public ResponseEntity<BoardResponseDto> saveBoard(@RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        return boardService.createBoard(requestDto, request);
    }

    @PutMapping("/board/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        return boardService.update(id, requestDto, request);
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id, HttpServletRequest request) {
        return boardService.deleteBoard(id, request);
    }
}
