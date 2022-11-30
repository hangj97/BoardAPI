package com.sparta.boardsa.dto;

import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.List;

public class BoardListResponseDto extends ResponseDto{
    // BoardResponseDto 로 변환된 데이터를 넣어줌.
    // BoardListResponseDto가 new 연산자로 객체 생성됐을 때 아래 boardList를 new로 생성하지 않으면 값을 넣을 수가 없다.
    List<BoardResponseDto> boardList = new ArrayList<>();

    // 초기 생성자를 통해 ResponseDto의 생성자도 같이 띄워버림.
    public BoardListResponseDto() {
        super("게시글 조회 성공", HttpStatus.OK.value());
    }

    // list에 값 넣어줘야함. -> 리스트 반환타입 BoardResponseDto기 때문에 매개변수로 넣어준다.
    public void addList(BoardResponseDto responseDto) {
        boardList.add(responseDto);
    }

}
