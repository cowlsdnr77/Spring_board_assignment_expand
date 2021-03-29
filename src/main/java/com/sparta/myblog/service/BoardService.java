package com.sparta.myblog.service;


import com.sparta.myblog.model.Board;
import com.sparta.myblog.repository.BoardRepository;
import com.sparta.myblog.dto.BoardRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Board createBoard(BoardRequestDto requestDto, Long userId, String username) {
        Board board = new Board(requestDto, userId, username);
        boardRepository.save(board);
        return board;
    }

    @Transactional
    public Long update(Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        board.update(requestDto);
        return id;
    }
}
