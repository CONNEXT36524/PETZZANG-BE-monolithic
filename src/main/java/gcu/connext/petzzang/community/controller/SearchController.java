package gcu.connext.petzzang.community.controller;

import gcu.connext.petzzang.PetzzangApplication;
import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.community.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/api/community")
public class SearchController {

    private final Logger log = LoggerFactory.getLogger(PetzzangApplication.class);

    @Autowired
    private BoardService boardService;

    @GetMapping("/search")
    public List<Post> getSearchPosts(@RequestParam String keyword) {
        log.info(keyword);

        // keyword = 사용자가 입력한 검색어
        // keyword 들어가는 게시글 가져와서 프론트로 보내주기
        List<Post> posts = boardService.search(keyword);

        return posts;
    }
}
