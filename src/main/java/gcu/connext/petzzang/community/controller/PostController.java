package gcu.connext.petzzang.community.controller;

import gcu.connext.petzzang.community.dto.PostDTO;
import gcu.connext.petzzang.community.dto.ReplyDTO;
import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.community.entity.Reply;
import gcu.connext.petzzang.community.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public Post getPost(@RequestParam(value="postId",required=false) Integer key
    ) throws Exception {

        Integer postId = Integer.valueOf(key);
        //save in mysql database
        postService.downloadPost(postId);
        //URI uriLocation = new URI("/board/" + board.getID());
        return postService.downloadPost(postId);
    }

    @PostMapping("/pluslikeNum")
    @ResponseStatus(HttpStatus.OK)
    public Post plusLikeNum( @RequestParam(name="postId") Integer postIdKey

    ) throws Exception {

        Long postId = Long.valueOf(postIdKey);
        return postService.plusLikeNum(postId);
    }

    @PostMapping("/minuslikeNum")
    @ResponseStatus(HttpStatus.OK)
    public Post minusLikeNum( @RequestParam(name="postId") Integer postIdKey

    ) throws Exception {

        Long postId = Long.valueOf(postIdKey);
        return postService.minusLikeNum(postId);
    }

    @PostMapping("/view")
    @ResponseStatus(HttpStatus.OK)
    public Post updateView( @RequestParam(name="postId") Integer postIdKey

    ) throws Exception {

        Long postId = Long.valueOf(postIdKey);
        return postService.updateView(postId);
    }

}