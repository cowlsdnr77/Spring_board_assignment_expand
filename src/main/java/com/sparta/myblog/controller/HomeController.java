package com.sparta.myblog.controller;

import com.sparta.myblog.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/") public String home() {
        return "index";
    }
    @GetMapping("board/create") public String show_create_board() {
        return "detail_create";
    }
}
