package gcu.connext.petzzang.community.service;

import gcu.connext.petzzang.community.dto.PostDTO;
import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.community.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class PostingService {

    @Autowired
    private PostRepository postRepository;

    //mysql에 게시글 저장하기
    public Post uploadPosting(Post post) {
        return postRepository.save(post);
    }

    public ResponseEntity<byte[]> uploadThumbnail(String key) {

        RestTemplate rt = new RestTemplate();

        String keyBase64 = key.substring(22);
        byte[] decodedBytes = Base64.getMimeDecoder().decode(keyBase64);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", "gAAAAABj8z-LRWmmLnduDGQcTKFOH--_Rv8msEbtTLzxVIL_1MoX211bx0XbW3VjWHVMchg6i8ydGdOkfm22RVy6DYqq8BMk1E-kFypmAfU3UObwMe9USHrWkVT2VcGy-3lw29sl5WQTc_pvt-EkDDOYhL8UyxoLYsaVkywqMoBpuud8pIJgDUJLhD8iQZCujTSrKy8Tqjdu");
        headers.add("Content-Type", "image/png");

        HttpEntity<byte[]> entity = new HttpEntity<>(decodedBytes, headers);

        String url = "https://objectstorage.kr-central-1.kakaoi.io/v1/cbfb40eb783145cbbc2fec56fd713fd3/pz-os/thumbnail/real2.png";

        return rt.exchange(url, HttpMethod.PUT, entity, byte[].class);
    }
}
