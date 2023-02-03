package gcu.connext.petzzang.user.controller;

import gcu.connext.petzzang.PetzzangApplication;
import gcu.connext.petzzang.user.Service.UserService;
import gcu.connext.petzzang.user.config.jwt.JwtProperties;
import gcu.connext.petzzang.user.dto.OauthToken;
import gcu.connext.petzzang.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@ResponseBody
@RequestMapping("/api")
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
    @PutMapping("/profile")
    public ResponseEntity<Object> putUserProfile(HttpServletRequest request) { //(1)


        //(2)
        log.info(String.valueOf(request));
//        userService.uploadImg(request);
        //(3)
        return userService.uploadImg(request);
    }
}