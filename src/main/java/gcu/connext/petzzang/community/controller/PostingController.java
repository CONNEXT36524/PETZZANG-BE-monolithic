package gcu.connext.petzzang.community.controller;

import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.community.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/community")
public class PostingController {

    @Autowired
    private PostingService postingService;

    // 게시글 리액트에서 받아오기
//    @PostMapping("/posting")
//    public Post createBoard(@RequestBody Post post) {
//        System.out.println("find me");
//        System.out.print(post);
//        //return postingService.uploadPosting(post);
//        return post;
//    }

    // 게시글 리액트에서 받아오기
    @PostMapping("/posting")
    public Post createBoard(@RequestBody Post post) {
        return postingService.uploadPosting(post);
    }
}
