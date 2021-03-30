package com.sparta.myblog.controller;


import com.sparta.myblog.model.Board;
import com.sparta.myblog.repository.BoardRepository;
import com.sparta.myblog.dto.BoardRequestDto;
import com.sparta.myblog.security.UserDetailsImpl;
import com.sparta.myblog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;

    // 게시판 목록 전체 조회
    @GetMapping("/api/boards")
    public @ResponseBody List<Board> getBoards() {
        return boardRepository.findAllByOrderByModifiedAtDesc();
    }

//    //게시물 한개 조회
//    @GetMapping("/api/boards/{id}")
//    public @ResponseBody Optional<Board> getOneBoard(@PathVariable Long id) {
//        return boardRepository.findById(id);
//    }

    @GetMapping("/boards")
    public String getOneBoard(@RequestParam(value = "id") Long id, Model model) {
        Optional<Board> board = boardRepository.findById(id);
        board.ifPresent(selectBoard -> {
            model.addAttribute("board", selectBoard);
        });
        return "detail_show";
    }

    // 게시글 등록
    @PostMapping("/api/boards")
    public @ResponseBody Board createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        String username = userDetails.getUser().getUsername();
        Board board = new Board(requestDto,userId,username);
        return boardRepository.save(board);
    }

    // 게시글 수정
    @PutMapping("/api/boards/{id}")
    public @ResponseBody Long updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.update(id, requestDto);
    }

    // 게시글 삭제
    @DeleteMapping("/api/boards/{id}")
    public @ResponseBody void deleteBoard(@PathVariable Long id) {
        boardRepository.deleteById(id);
    }
}
