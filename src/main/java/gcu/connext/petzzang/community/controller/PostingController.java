package gcu.connext.petzzang.community.controller;

import gcu.connext.petzzang.community.dto.PostDTO;
import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.community.service.PostingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.math.BigInteger;
import java.util.Base64;


@RestController
@RequestMapping("/api/community")
public class PostingController {

    @Autowired
    private PostingService postingService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/posting")
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPosting( @RequestParam(name="imgName") String imgName,
            @ModelAttribute PostDTO postDTO
    ) throws Exception {
        System.out.println(imgName);
        //System.out.println(postDTO.thumbnail);

        //kic object storage 주소로 변환
        String imageSource = postDTO.getThumbnail();
        String postId = String.valueOf(postDTO.getPostId());
        String imageUrl = postingService.uploadThumbnail(postId, imageSource, imgName);
        postDTO.setThumbnail(imageUrl);

        //DTO to Entity
        Post post = modelMapper.map(postDTO, Post.class);

        // save in mysql database
        return postingService.uploadPosting(post);
    }

}