package gcu.connext.petzzang.community.controller;

import gcu.connext.petzzang.community.dto.PostDTO;
import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.community.service.PostingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/community")
public class PostingController {

    @Autowired
    private PostingService postingService;

    @Autowired
    private ModelMapper modelMapper;

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