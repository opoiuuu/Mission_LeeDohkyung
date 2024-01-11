package com.example.boardproject.repository;


import com.example.boardproject.BoardService;
import com.example.boardproject.entity.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;
    @GetMapping("/board/write")
    public String boardWriteForm()
    {
        return "boardwrite";

    }
    @PostMapping("/board/writePro")
    public  String boardWritePro(Board board ){//데이터가 넘어오는 프로세스

        boardService.write(board);

        return "redirect:/board/list";

    }

    @GetMapping("/board/list")
    public String boardList(Model model, @PageableDefault(page = 0,size=10, sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword){//desc = 역순

        Page<Board> list = null;

        if(searchKeyword == null){
           list = boardService.boardList(pageable);
        }else{
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1; //페이져블은 0에서 시작하므로 1더해줌
        int startPage = Math.max(nowPage -4, 1); // 앞에꺼 보다 작아지면 무조건 1페이지로 옴
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage",startPage );
        model.addAttribute("endPage", endPage);


        return "boardlist";
    }

    @GetMapping("/board/view")// local host: 8080/board/view?id=1 // 1이면 id안으로 들어가고 게시글을 불러옴
    public String boardView(Model model, Integer id){

        model.addAttribute("board", boardService.boardView(id));

        return "boardview";
    }

    @GetMapping("/board/delete")

    public String boardDelete(Integer id) {

        boardService.boardDelete(id);

        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}") //path variable 사용
    public String boardModify(@PathVariable("id") Integer id, //수정할때 원래글 내용들을 불러옴
                              Model model){
        model.addAttribute("board", boardService.boardView(id));

        return  "boardmodify";
    }

    @PostMapping("/board/update/{id}") //수정 처 리
    public String boardUpdate(@PathVariable("id") Integer id,
                              Board board){
        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp);

        return  "redirect:/board/list";

    }


}
