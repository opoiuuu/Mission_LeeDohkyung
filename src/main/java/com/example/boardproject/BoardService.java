package com.example.boardproject;


import com.example.boardproject.entity.Board;
import com.example.boardproject.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository; //주입

    //글 작성 처리
    public void write(Board board){

        boardRepository.save(board);

    }
    //게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable){

        return boardRepository.findAll(pageable);
    }
    public  Page<Board> boardSearchList(String searchKeyword, Pageable pageable){
        return  boardRepository.findByTitleContaining(searchKeyword, pageable);
    }
    //특정 게시글 불러오기
    public Board boardView(Integer id){

        return boardRepository.findById(id).get();
    }
    //특정 게시글 삭제
    public void boardDelete(Integer id){

        boardRepository.deleteById(id);
    }
}
