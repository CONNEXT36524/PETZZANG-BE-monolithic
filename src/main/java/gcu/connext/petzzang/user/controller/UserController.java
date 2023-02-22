package gcu.connext.petzzang.user.controller;

import gcu.connext.petzzang.PetzzangApplication;
import gcu.connext.petzzang.community.dto.PostDTO;
import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.community.repository.BoardRepository;
import gcu.connext.petzzang.community.service.PostingService;
import gcu.connext.petzzang.user.Service.UserService;
import gcu.connext.petzzang.user.config.jwt.JwtProperties;
import gcu.connext.petzzang.user.dto.Mypost;
import gcu.connext.petzzang.user.dto.OauthToken;
import gcu.connext.petzzang.user.dto.UpdateDTO;
import gcu.connext.petzzang.user.entity.User;
import gcu.connext.petzzang.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private PostingService postingService;

    @Autowired
    private ModelMapper modelMapper;

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

    //이미지만 변경

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
    public ResponseEntity<Boolean> dupleCheck(@RequestParam String name) {
        return ResponseEntity.ok(userService.checkNicknameDuplicate(name));
    }


    @GetMapping("/updateNickname")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> updateNickname(HttpServletRequest request, @RequestParam String name)
    {
        System.out.println("이름변경");
        return ResponseEntity.ok(userService.updateNickname(request, name));
    }

    @GetMapping("/updateImg")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> updateImg(HttpServletRequest request,@RequestParam(name = "imgName") String imgName, @ModelAttribute UpdateDTO updateDTO
    ) throws Exception {
        String uploadImg = updateDTO.getUploadImg();


        //kic object storage 주소로 변환
        String imgUrl = userService.uploadImg(uploadImg, imgName);
        System.out.println(imgUrl);
        // save in mysql database
        return ResponseEntity.ok(userService.updateImg(request, imgUrl));
    }

    @GetMapping("/updateProfile")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<byte[]> updateProfile(HttpServletRequest request, @ModelAttribute UpdateDTO updateDTO, @RequestParam(name = "imgName") String imgName
    ) throws Exception {
        String uploadImg = updateDTO.getUploadImg();
        String name = updateDTO.getChgName();

        //kic object storage 주소로 변환
        String imgUrl = userService.uploadImg(uploadImg, imgName);
        System.out.println(imgUrl);

        // save in mysql database
        return userService.updateProfile(request, imgUrl, name);
    }
}
