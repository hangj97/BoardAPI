package com.sparta.boardsa.controller;


import com.sparta.boardsa.dto.BoardListResponseDto;
import com.sparta.boardsa.dto.BoardRequestDto;
import com.sparta.boardsa.dto.BoardResponseDto;
import com.sparta.boardsa.dto.ResponseDto;
import com.sparta.boardsa.entity.Board;
import com.sparta.boardsa.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 컨트롤러 -> http 요청 받음 -> 데이터를 변환 후 가져옴 -> 서비스로 전달 -> 로직 수행 -> 수행 후 넘어오는 데이터 ct 에게 전달

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("api/board")
    public List<Board> getBoards() {
        return boardService.getBoards();
    }

    @GetMapping("api/board/read")
    public BoardResponseDto getBoard(@RequestParam Long id) {
        return boardService.getBoard(id);
    }

    @PostMapping("/api/board")
    public ResponseDto saveBoard(@RequestBody BoardRequestDto requestDto) {
        return boardService.createBoard(requestDto);
    }

    @PutMapping("/api/board/{id}")
    public ResponseEntity<?> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.update(id, requestDto);
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto deleteOne(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.deleteBoard(id, requestDto);
    }
}
