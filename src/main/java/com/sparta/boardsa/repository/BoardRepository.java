package com.sparta.boardsa.repository;

import com.sparta.boardsa.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository<연결할 테이블(클래스명), Long(id PK)>
public interface BoardRepository extends JpaRepository<Board, Long> {
    // 수정된 시간 가장 최근 순으로 조회(내림차순)
    List<Board> findAllByOrderByModifiedAtDesc();
}
