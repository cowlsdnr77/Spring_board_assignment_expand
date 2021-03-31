package com.sparta.myblog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonBackReference
    @ManyToOne
    @JoinColumn(nullable = false) //name 설정 안하면 알아서 BOARD_ID로 DB에 반영
    private Board board;

    public Comment(CommentRequestDto requestDto, Long userId, Board board, String username) {
        this.userId = userId;
        this.board = board;
        this.username = username;
        this.content = requestDto.getContent();
    }

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
