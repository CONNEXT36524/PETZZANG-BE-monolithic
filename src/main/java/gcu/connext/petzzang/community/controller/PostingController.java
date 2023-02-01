package gcu.connext.petzzang.community.controller;

import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.community.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public Post createBoard(
            @RequestParam("titleName") String titleName
            // @RequestParam("content") String content,
            // @RequestParam("files") List<MultipartFile> files
    ) throws Exception {
        Post post = postingService.uploadPosting(Post.builder()
                .titleName(titleName)
                //.content(content)
                .build());

        //URI uriLocation = new URI("/board/" + board.getID());
        return postingService.uploadPosting(post);
    }

}