package com.sparta.myblog.model;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String title;
    private String username;
    private String content;
}
