package com.sparta.myblog.repository;

import com.sparta.myblog.model.Board;
import com.sparta.myblog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardOrderByModifiedAtDesc(Board board);
}
