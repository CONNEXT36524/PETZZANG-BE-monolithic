package gcu.connext.petzzang.user.controller;

import gcu.connext.petzzang.PetzzangApplication;
import gcu.connext.petzzang.community.repository.BoardRepository;
import gcu.connext.petzzang.user.Service.UserService;
import gcu.connext.petzzang.user.config.jwt.JwtProperties;
import gcu.connext.petzzang.user.dto.Mypost;
import gcu.connext.petzzang.user.dto.OauthToken;
import gcu.connext.petzzang.user.entity.User;
import gcu.connext.petzzang.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;

import static java.lang.Integer.parseInt;

@RestController
@ResponseBody
@RequestMapping(value="/api", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(PetzzangApplication.class);
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/get/username")
    public String getUserName(@RequestParam(name="userCode") String userCode) {
        System.out.println(userCode);

//        Integer intUserCode = 0;
//        try {
//            intUserCode = parseInt(userCode);
//        } catch (NumberFormatException e) {
//
//        } catch (Exception e) {
//
//        }

        String userName="";

        User user = userRepository.findByKakaoId(Long.valueOf(userCode));
        userName=user.getKakaonickname();

        return userName;
    }


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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<byte[]> putUserProfile( @RequestParam(name="imgName") String imgName, @RequestParam(name="uploadImg") String uploadImg
    ) throws Exception {

        RestTemplate rt = new RestTemplate();

        String keyBase64 = uploadImg.substring(22);
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


    @PutMapping("/get/profile")
    public ResponseEntity<Object> getUserProfile(@RequestParam String imgName) { //(1)
        System.out.println(imgName);

        try {
            // RestTemplate 객체를 생성합니다.
            RestTemplate restTemplate = new RestTemplate();

            // header 설정을 위해 HttpHeader 클래스를 생성한 후 HttpEntity 객체에 넣어줍니다.
            HttpHeaders headers  = new HttpHeaders(); // 담아줄 header
            headers.add("X-Auth-Token", "gAAAAABj9XzFg3mWBnPRj2umh8SLYZwYb1kDhJ-SFTXU28j3bRBw1PTHQmM3mi35S2VXWrlfxk6EFyRe6f7l6aFIQArv96yTMtLEdkLGbrZqGULHaAedr55lUsoBBiK0qLCV3E5TRUzh_lupK1taJ2dhlwljq4_z9ILAtRWq9jmP5dOugzHwv-C0KPxrTweH5V_fz_KgIIXY");

            HttpEntity<String> entity = new HttpEntity<String>(headers);

            // exchange() 메소드로 api를 호출합니다.
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    "https://objectstorage.kr-central-1.kakaoi.io/v1/cbfb40eb783145cbbc2fec56fd713fd3/pz-os/thumbnail/dog2.png",
                    HttpMethod.GET, entity, byte[].class);

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body("error");
        }// end catch

    }
}