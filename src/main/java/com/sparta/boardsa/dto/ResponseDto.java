package com.sparta.boardsa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

// 역직렬화, 직렬화 공부하기 -> json 변환해주는 것임.
// json을 한 번 변환해줘야 함.
// ct에게는 json.stringify 를 통해 변환해줌.
// getter, 생성자 필요.
@Getter
@NoArgsConstructor
public class ResponseDto {
    private String message;
    private int statusCode;

    public ResponseDto(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
