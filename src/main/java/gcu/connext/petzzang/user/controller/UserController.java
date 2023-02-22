package gcu.connext.petzzang.user.controller;

import gcu.connext.petzzang.PetzzangApplication;
import gcu.connext.petzzang.user.Service.UserService;
import gcu.connext.petzzang.user.config.jwt.JwtProperties;
import gcu.connext.petzzang.user.dto.Mypost;
import gcu.connext.petzzang.user.dto.OauthToken;
import gcu.connext.petzzang.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;

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

    //이미지만 변경
    @PutMapping("/profile")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<byte[]> putUserProfile( @RequestParam(name="imgName") String imgName, @RequestParam(name="uploadImg") String uploadImg
    ) throws Exception {

        RestTemplate rt = new RestTemplate();

        String keyBase64 = uploadImg.substring(22);
        byte[] decodedBytes = Base64.getMimeDecoder().decode(keyBase64);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", "gAAAAABj9ERb1ja9kfOAnLaEX6t-hwl4AwHbPW2bbLF6nZgyNSQ5oOGUDFWPP5xBZ6AAPVpK1oMt4C4-ZJFSS8qv8l5oUML7amQGRErZqDg9BoY7jwSK8rUuRfJCj5EGhqJb3wqceIRzt7U1AmwYcQ08wPKCAMLdwxTgwp71lpRmk4q5Yb9O0cDNR2CJhqUUSJUcbwTRzoMq");
        if(imgName.contains(".png")) {
            System.out.println("png");
            headers.add("Content-Type", "image/png");
        } else if(imgName.contains(".jpg")) {
            System.out.println("jpg");
            headers.add("Content-Type", "image/jpeg");
        }

        HttpEntity<byte[]> entity = new HttpEntity<>(decodedBytes, headers);

        String url = "https://objectstorage.kr-central-1.kakaoi.io/v1/cbfb40eb783145cbbc2fec56fd713fd3/pz-os/thumbnail/"+imgName;

        return rt.exchange(url, HttpMethod.PUT, entity, byte[].class);
    }

    //이미지+닉네임 변경
    @PutMapping("/updateProfile")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<byte[]> putUserProfile( @RequestParam(name="imgName") String imgName, @RequestParam(name="uploadImg") String uploadImg, @RequestParam(name="nameChg") String nameChg
    ) throws Exception {

        RestTemplate rt = new RestTemplate();

        String keyBase64 = uploadImg.substring(22);
        byte[] decodedBytes = Base64.getMimeDecoder().decode(keyBase64);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", "gAAAAABj9ERb1ja9kfOAnLaEX6t-hwl4AwHbPW2bbLF6nZgyNSQ5oOGUDFWPP5xBZ6AAPVpK1oMt4C4-ZJFSS8qv8l5oUML7amQGRErZqDg9BoY7jwSK8rUuRfJCj5EGhqJb3wqceIRzt7U1AmwYcQ08wPKCAMLdwxTgwp71lpRmk4q5Yb9O0cDNR2CJhqUUSJUcbwTRzoMq");
        if(imgName.contains(".png")) {
            System.out.println("png");
            headers.add("Content-Type", "image/png");
        } else if(imgName.contains(".jpg")) {
            System.out.println("jpg");
            headers.add("Content-Type", "image/jpeg");
        }

        HttpEntity<byte[]> entity = new HttpEntity<>(decodedBytes, headers);

        String url = "https://objectstorage.kr-central-1.kakaoi.io/v1/cbfb40eb783145cbbc2fec56fd713fd3/pz-os/thumbnail/"+imgName;

        return rt.exchange(url, HttpMethod.PUT, entity, byte[].class);
    }
    @GetMapping("/me/posts")
    public ResponseEntity<Object> getMyposts(HttpServletRequest request) { //(1)

        //(2)
        List<Mypost> mypost = userService.getMyposts(request);

        //(3)
        return ResponseEntity.ok().body(mypost);
    }

    //중복확인
    @PutMapping("/duplicationcheck")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Boolean> dupleCheck(@RequestParam String name)
    {
        return ResponseEntity.ok(userService.checkNicknameDuplicate(name));
    }

    @PostMapping("/updateNickname")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> updateProfile(HttpServletRequest request, @RequestParam String name)
    {
        log.info("hi");
        return ResponseEntity.ok(userService.updateNickname(request, name));
    }

}
