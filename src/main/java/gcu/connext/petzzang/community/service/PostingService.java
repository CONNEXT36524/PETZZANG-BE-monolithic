package gcu.connext.petzzang.community.service;

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

    public String uploadThumbnail(String postId, String imageSource, String imgName) {

        RestTemplate rt = new RestTemplate();

        String keyBase64 = imageSource.substring(22);
        byte[] decodedBytes = Base64.getMimeDecoder().decode(keyBase64);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", "gAAAAABj9XzFg3mWBnPRj2umh8SLYZwYb1kDhJ-SFTXU28j3bRBw1PTHQmM3mi35S2VXWrlfxk6EFyRe6f7l6aFIQArv96yTMtLEdkLGbrZqGULHaAedr55lUsoBBiK0qLCV3E5TRUzh_lupK1taJ2dhlwljq4_z9ILAtRWq9jmP5dOugzHwv-C0KPxrTweH5V_fz_KgIIXY");
        if(imgName.contains(".png")) {
            System.out.println("png");
            headers.add("Content-Type", "image/png");
        } else if(imgName.contains(".jpg")) {
            System.out.println("jpg");
            headers.add("Content-Type", "image/jpeg");
        }

        HttpEntity<byte[]> entity = new HttpEntity<>(decodedBytes, headers);

        String url = "https://objectstorage.kr-central-1.kakaoi.io/"
                +"v1/cbfb40eb783145cbbc2fec56fd713fd3/pz-os/thumbnail/"
                +imgName;

        ResponseEntity<byte[]> imgResposne = rt.exchange(url, HttpMethod.PUT, entity, byte[].class);
        System.out.println(imgResposne);

        return url;
    }
}
