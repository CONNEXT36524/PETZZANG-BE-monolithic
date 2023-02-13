package gcu.connext.petzzang.community.controller;

import gcu.connext.petzzang.community.dto.PostDTO;
import gcu.connext.petzzang.community.entity.Post;
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
    private PostService PostService;

    @GetMapping("/posts")
    public Post getPost(@RequestParam(value="postId",required=false) Integer key
    ) throws Exception {

        Integer postId = Integer.valueOf(key);
        //save in mysql database
        PostService.downloadPost(postId);
        //URI uriLocation = new URI("/board/" + board.getID());
        return PostService.downloadPost(postId);
    }

}