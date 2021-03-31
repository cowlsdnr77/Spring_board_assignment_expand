package com.sparta.myblog.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.myblog.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Long userId;

    @JsonManagedReference
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE) // 영속성 전이 (삭제)
    private List<Comment> comments;

    public Board(BoardRequestDto requestDto, Long userId, String username) {
        this.userId = userId;
        this.title = requestDto.getTitle();
        this.username = username;
        this.content = requestDto.getContent();
    }

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

}
