package com.sparta.boardsa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.boardsa.dto.BoardRequestDto;
import com.sparta.boardsa.entity.Board;
import com.sparta.boardsa.repository.BoardRepository;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EnableJpaAuditing
@SpringBootApplication
public class BoardSaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardSaApplication.class, args);
    }
}
