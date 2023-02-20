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
    public Post createPosting(
            @ModelAttribute PostDTO postDTO
    ) throws Exception {

        System.out.println(postDTO.thumbnail);


        //DTO to Entity
        Post post = modelMapper.map(postDTO, Post.class);

        // save in mysql database
        // postingService.uploadPosting(post);
        //URI uriLocation = new URI("/board/" + board.getID());
        return postingService.uploadPosting(post);
    }

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<byte[]> uploadImg(
            @RequestParam(name="imgFile") String key
    ) throws Exception {

        RestTemplate rt = new RestTemplate();

        String keyBase64 = key.substring(22);
        byte[] decodedBytes = Base64.getMimeDecoder().decode(keyBase64);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", "token");
        headers.add("Content-Type", "image/png");

        HttpEntity<byte[]> entity = new HttpEntity<>(decodedBytes, headers);

        String url = "https://objectstorage.kr-central-1.kakaoi.io/v1/cbfb40eb783145cbbc2fec56fd713fd3/pz-os/thumbnail/real2.png";

        return rt.exchange(url, HttpMethod.PUT, entity, byte[].class);

    }
}