package com.sparta.myblog.controller;

import com.sparta.myblog.dto.CommentRequestDto;
import com.sparta.myblog.model.Comment;
import com.sparta.myblog.repository.CommentRepository;
import com.sparta.myblog.security.UserDetailsImpl;
import com.sparta.myblog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;


}
