package gcu.connext.petzzang.user.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gcu.connext.petzzang.user.config.jwt.JwtProperties;
import gcu.connext.petzzang.user.dto.KakaoProfile;
import gcu.connext.petzzang.user.dto.Mypost;
import gcu.connext.petzzang.user.dto.OauthToken;
import gcu.connext.petzzang.user.entity.User;
import gcu.connext.petzzang.user.repository.MypostRepository;
import gcu.connext.petzzang.user.repository.UserRepository;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository; //(1)

    @Autowired
    MypostRepository mypostRepository;

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

    public List<Mypost> getMyposts(HttpServletRequest request) { //(1)
        //(2) 해당 request에는 jwtRequestFilter를 거쳐 인증이 완료된 사용자의 userCode가 요소로 추가되어 있음
        Long userCode = (Long) request.getAttribute("userCode");
        System.out.println("#################usercode#############"+userCode);
        //(3) 가져온 userCode로 DB에서 사용자 정보를 가져와 user객체에 담는다
        List<Mypost> mypost = mypostRepository.findByUserCode(userCode);

        //(4) user객체 반환
        return mypost;
    }

    //kic object storage 사진 업로드
    public Mono<String>  uploadImg(MultipartHttpServletRequest request) throws IOException{

        MultipartFile multpartFile = (MultipartFile) request.getFile("img");
//        String contentType = request.getContentType();
        File uploadFile1 = fileconvert(multpartFile).orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File fail"));
        byte[] uploadFile2 = convert(multpartFile);
//        InputStream asdf = multpartFile.getInputStream();

//        RestTemplate rt = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("X-Auth-Token", "gAAAAABj4uyNfvgl2lPl8e2Q7MEqsS51prENB5MmF2f0yNb4iaN7LjiltwOnc_kdsO2RPOG6siqcwtaVJq_GSfa5pi0Dc_vBI6I8j9_d62MZV-8__kfoYP2GWBjK-9jULCtBeghD0tEAiw4oIbdWI_DUSyt7dmP-yI9MbBt6WIPp7IjYH4HFW1NJ8cU4lo4fQrDq9G1IkDm9" ); //(1-4)
//        headers.setContentType(MediaType.IMAGE_PNG);
//        headers.set("Content-Type", "image/png");
//
//        log.info(contentType);
//
//
//        MultiValueMap<String, byte[]> params = new LinkedMultiValueMap<>();
//        params.add("Content", uploadFile);
//
//        //(1-5)
//        HttpEntity<MultiValueMap<String, byte[]>>  imgUploadRequest =
//                new HttpEntity<>(params, headers);
//
//        //(1-6)
//        // Http 요청 (POST 방식) 후, response 변수에 응답을 받음
//        ResponseEntity<String> imgResponse = rt.exchange(
//                "https://objectstorage.kr-central-1.kakaoi.io/v1/cbfb40eb783145cbbc2fec56fd713fd3/pz-os/thumbnail/test.png",
//                HttpMethod.PUT,
//                imgUploadRequest,
//                String.class
//        );
//        removeNewFile(uploadFile);

        WebClient webClient = WebClient.builder()
                .defaultHeader("X-Auth-Token", "gAAAAABj5asWzIDyUabjJHcFt0XtONghDo7TOa7YaZUvNHHgmCpTy8eR1wuo7HWzL_wMGyHU8QmqPKroh1uOhK0aRwg5t_U6up7i4RyrmiXWOkgwamkr-DzpqaJRlR4flVuPThdT_ny2DMTsROQSRV2oLvCtxqRPT7Kqi2__HZtobdqDz3MCIbRSwh1P5NfpCgo_-rj2EC7U")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                .build();


        Mono<String> result = webClient.put()
                .uri("https://objectstorage.kr-central-1.kakaoi.io/v1/cbfb40eb783145cbbc2fec56fd713fd3/pz-os/thumbnail/test3.png")
                .bodyValue(uploadFile1)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus != HttpStatus.OK,
                        clientResponse -> {
                            return clientResponse.createException()
                                    .flatMap(it -> Mono.error(new RuntimeException("code : " + clientResponse.statusCode())));
                        })
                .bodyToMono(String.class)
                .onErrorResume(throwable -> {
                    return Mono.error(new RuntimeException(throwable));
                });


        return result;

    }
//    public String upload (File uploadFile, String dirName){
//        String fileName = dirName + "/" + uploadFile.getName();
//        String uploadImageUrl = putS3(uploadFile, fileName);
//
//        removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)
//
//        return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
//    }
    //사진 조회해서 url받아오기
    public String getImgUrl ()
    {
        return "주소";
    }

    //데이터베이스 userImg 필드 수정하기
    public void updateProfileImg()
    {

    }
    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            log.info("delete success");
        }else {
            log.info("delete fail");
        }
    }
    private Optional<File> fileconvert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    private byte[] convert(MultipartFile file) throws IOException {

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] photoEncode = encoder.encode(file.getBytes());
        String photoImg = new String(photoEncode, "UTF8");

        return photoEncode;
    }


}