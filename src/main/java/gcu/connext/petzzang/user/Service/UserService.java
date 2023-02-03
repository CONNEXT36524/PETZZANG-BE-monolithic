package gcu.connext.petzzang.user.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gcu.connext.petzzang.user.config.jwt.JwtProperties;
import gcu.connext.petzzang.user.dto.KakaoProfile;
import gcu.connext.petzzang.user.dto.OauthToken;
import gcu.connext.petzzang.user.entity.User;
import gcu.connext.petzzang.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.sql.Blob;
import java.util.Date;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository; //(1)

    public OauthToken getAccessToken(String code) {

        //(2)
        RestTemplate rt = new RestTemplate();

        //(3)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //(4)
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id","ca40c9bbb798dcd72cc61aac397e894a");
        params.add("redirect_uri", "http://localhost:3000/oauth/callback/kakao");
        params.add("code", code);
        //params.add("client_secret", "{시크릿 키}"); // 생략 가능!

        //(5)
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        //(6)
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        //(7)
        ObjectMapper objectMapper = new ObjectMapper();
        OauthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return oauthToken; //(8)
    }

    public String saveUserAndGetToken(String token) {

        KakaoProfile profile = findProfile(token);

        User user = userRepository.findByKakaoEmail(profile.getKakao_account().getEmail());
        if(user == null) {
            user = User.builder()
                    .kakaoId(profile.getId())
                    .kakaoProfileImg(profile.getKakao_account().getProfile().getProfile_image_url())
                    .kakaoNickname(profile.getKakao_account().getProfile().getNickname())
                    .kakaoEmail(profile.getKakao_account().getEmail())
                    .userRole("ROLE_USER").build();

            userRepository.save(user);
        }

        return createToken(user); //(2) string형의 JWT반환
    }
    public String createToken(User user) { //(2-1)

        //(2-2)
        String jwtToken = JWT.create()

                //(2-3) payload에 들어갈 등록된 클레임 설정, exp는 jwtproperties의 만료 시간 필드를 불러와서 넣음
                .withSubject(user.getKakaoemail())
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION_TIME))

                //(2-4) payload에 들어갈 개인 클레임 설정, .withClaim(이름, 내용)형식 사용자를 식별할 수 있는 값을 넣음
                .withClaim("id", user.getUsercode())
                .withClaim("nickname", user.getKakaonickname())

                //(2-5) signature 설정, HMAC512알고리즘 사용 jwtproperies의 비밀 키 불러와서 넣어줌
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return jwtToken; //(2-6) 만들어진 JWT반환
    }
    public KakaoProfile findProfile(String token) {

        //(1-2)
        RestTemplate rt = new RestTemplate();

        //(1-3)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token); //(1-4)
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //(1-5)
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        //(1-6)
        // Http 요청 (POST 방식) 후, response 변수에 응답을 받음
        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        //(1-7)
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfile;
    }
    public User getUser(HttpServletRequest request) { //(1)
        //(2) 해당 request에는 jwtRequestFilter를 거쳐 인증이 완료된 사용자의 userCode가 요소로 추가되어 있음
        Long userCode = (Long) request.getAttribute("userCode");
        System.out.println("#################usercode#############"+userCode);
        //(3) 가져온 userCode로 DB에서 사용자 정보를 가져와 user객체에 담는다
        User user = userRepository.findByUserCode(userCode);

        //(4) user객체 반환
        return user;
    }

    //kic object storage 사진 업로드
    public ResponseEntity uploadImg(HttpServletRequest request){
        RestTemplate rt = new RestTemplate();

        Blob content = (Blob) request.getAttribute("data");

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", "gAAAAABj2cWtX4ctnlFIaBZb-CVrjbEUXNkAs7Iz6sO6YeZ5tbvgwg5W0PGlnBev651VpQM_c1IK_EmK2LOqIQh_hHuYqlPi_4S0qHIXWYmS7vIqYzaKN0hrM4hNjnXxQGvtf3tKfOqoQjb9Gu_2zvMeLqJcYWtFyAjMX9DRxElmzrM0mJx4hJNY8UMB3Yfx6QBFjG723VJr" ); //(1-4)

        MultiValueMap<String, Blob> params = new LinkedMultiValueMap<>();
        params.add("Content", content);

        //(1-5)
        HttpEntity<MultiValueMap<String, String>> imgUploadRequest =
                new HttpEntity<>(headers);

        //(1-6)
        // Http 요청 (POST 방식) 후, response 변수에 응답을 받음
        ResponseEntity<String> imgResponse = rt.exchange(
                "https://objectstorage.kr-central-1.kakaoi.io/v1/cbfb40eb783145cbbc2fec56fd713fd3/pz-os/thumbnail/test.png",
                HttpMethod.PUT,
                imgUploadRequest,
                String.class
        );

        return new ResponseEntity(imgResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //사진 조회해서 url받아오기
    public String getImgUrl ()
    {
        return "주소";
    }

    //데이터베이스 userImg 필드 수정하기
    public void updateProfileImg()
    {

    }
}