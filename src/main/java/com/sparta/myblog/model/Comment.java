package com.sparta.myblog.model;

import com.sparta.myblog.dto.BoardRequestDto;
import com.sparta.myblog.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long boardId;

    public Comment(CommentRequestDto requestDto, Long userId, Long boardId, String username) {
        this.userId = userId;
        this.boardId = boardId;
        this.username = username;
        this.content = requestDto.getContent();
    }

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
