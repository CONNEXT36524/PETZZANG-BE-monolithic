package gcu.connext.petzzang.community.controller;

import gcu.connext.petzzang.community.dto.PostDTO;
import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.community.service.PostingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/community")
public class PostingController {

    @Autowired
    private PostingService postingService;

    @Autowired
    private ModelMapper modelMapper;

    // 게시글 리액트에서 받아오기
//    @PostMapping("/posting")
//    public Post createBoard(@RequestBody Post post) {
//        System.out.println("find me");
//        System.out.print(post);
//        //return postingService.uploadPosting(post);
//        return post;
//    }

    // 게시글 리액트에서 받아오기
//    @PostMapping("/posting")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Post createPosting(
//            @RequestParam("titleName") String titleName,
//            @RequestParam("content") String content
//            // @RequestParam("files") List<MultipartFile> files
//    ) throws Exception {
//        PostDTO postDTO = PostDTO.builder()
//                .titleName(titleName)
//                .content(content)
//                .build();
//
//        Post post = modelMapper.map(postDTO, Post.class);
//        postingService.uploadPosting(post);
//        //URI uriLocation = new URI("/board/" + board.getID());
//        return postingService.uploadPosting(post);
//    }

    @PostMapping("/posting")
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPosting(
            @ModelAttribute PostDTO postDTO
            // @RequestParam("files") List<MultipartFile> files
    ) throws Exception {

        System.out.println(postDTO.thumbnail);


        //DTO to Entity
        Post post = modelMapper.map(postDTO, Post.class);

        //save in mysql database
        postingService.uploadPosting(post);
        //URI uriLocation = new URI("/board/" + board.getID());
        return postingService.uploadPosting(post);
    }

}