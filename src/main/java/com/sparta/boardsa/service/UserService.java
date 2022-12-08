package com.sparta.boardsa.service;

import com.sparta.boardsa.dto.LoginRequestDto;
import com.sparta.boardsa.dto.ResponseDto;
import com.sparta.boardsa.dto.SignupRequestDto;
import com.sparta.boardsa.dto.UserResponseDto;
import com.sparta.boardsa.entity.User;
import com.sparta.boardsa.entity.UserRoleEnum;
import com.sparta.boardsa.jwt.JwtUtil;
import com.sparta.boardsa.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public ResponseEntity<UserResponseDto> signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        UserRoleEnum role = UserRoleEnum.USER;

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록 불가합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // user Entity 생성
        User user = new User(username, password, role);
        // DB 저장
        userRepository.save(user);

        return new ResponseEntity<>(new UserResponseDto(user), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<UserResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return new ResponseEntity<>(new UserResponseDto(user), HttpStatus.OK);
    }
}
