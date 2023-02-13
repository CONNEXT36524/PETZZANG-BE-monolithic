package gcu.connext.petzzang.user.controller;

import com.nimbusds.jose.shaded.json.JSONArray;
import gcu.connext.petzzang.PetzzangApplication;
import gcu.connext.petzzang.user.Service.UserService;
import gcu.connext.petzzang.user.config.jwt.JwtProperties;
import gcu.connext.petzzang.user.dto.OauthToken;
import gcu.connext.petzzang.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@ResponseBody
@RequestMapping(value="/api", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(PetzzangApplication.class);
    @Autowired
    private UserService userService;


    // 프론트에서 인가코드 받아오는 url

    @GetMapping("/kakao") // (3)
    public ResponseEntity getLogin(@RequestParam("code") String code) { //(4)
        log.info(code);

        // 넘어온 인가 코드를 통해 access_token 발급
        OauthToken oauthToken = userService.getAccessToken(code);

        //(2)
        // 발급 받은 accessToken 으로 카카오 회원 정보 DB 저장 후 JWT 를 생성
        String jwtToken = userService.saveUserAndGetToken(oauthToken.getAccess_token());

        //(3) 응답 헤더의 authorization 항목에 JWT 넣어줌
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

        //(4) JWT가 담긴 헤더와 2000k 스테이터스 값, "success" body값을 ResponseEntity에 담아 프론트에 전달
        return ResponseEntity.ok().headers(headers).body("success");
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getCurrentUser(HttpServletRequest request) { //(1)
        log.info("여기 리퀘스트");
        log.info(String.valueOf(request));
        //(2)
        User user = userService.getUser(request);

        //(3)
        return ResponseEntity.ok().body(user);
    }


    @PutMapping("/upload/profile")
    public ResponseEntity<Object> putUserProfile(MultipartHttpServletRequest request) { //(1)

        //(2)

        try {
            Mono<String> result = userService.uploadImg(request);
            return ResponseEntity.ok().body(result);
        }
        catch (IOException ex){
            return ResponseEntity.ok().body(ex);
        }
    }

    @PutMapping("/get/profile")
    public ResponseEntity<Object> getUserProfile(@RequestParam List imgName) { //(1)

        System.out.println(imgName);

        try {

            // RestTemplate 객체를 생성합니다.
            RestTemplate restTemplate = new RestTemplate();

            // header 설정을 위해 HttpHeader 클래스를 생성한 후 HttpEntity 객체에 넣어줍니다.
            HttpHeaders headers  = new HttpHeaders(); // 담아줄 header
            headers.add("X-Auth-Token", "gAAAAABj6YqyozXWIgykcaTiKJaHnyCi5BarBX3Mcmtlg6FUhMXfZCysHiDnK_3FzB1FXwQU4yOtmwXlVMBlltx3UXhn1vwIj5YvKLXNCFtfY73nZGdqKQ-x5xbIDCLmLXOZ3gzieYFKh7UL56EDB4auCmf7vrUf3ZYw4RVvFXCYo1x-RjKg-LSIUGINZgaCdGZ9NYQMM1Gx");

            HttpEntity<String> entity = new HttpEntity<String>(headers);

            // exchange() 메소드로 api를 호출합니다.
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://objectstorage.kr-central-1.kakaoi.io/v1/cbfb40eb783145cbbc2fec56fd713fd3/pz-os/thumbnail/pz-cat.png",
                    HttpMethod.GET, entity, String.class);

            System.out.println(response.getBody());

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body("error");
        }// end catch

    }
}