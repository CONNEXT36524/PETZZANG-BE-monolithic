package gcu.connext.petzzang.community.controller;

import gcu.connext.petzzang.PetzzangApplication;
import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.community.repository.BoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@ResponseBody
@RequestMapping("/api/community")
public class BoardController {

    private final Logger log = LoggerFactory.getLogger(PetzzangApplication.class);

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/board/daily")
    public List<Post> getDailyPosts(@RequestParam List typeValue, @RequestParam List typeBtn, @RequestParam List sexValue) {

        log.info(typeValue.toString());
        log.info(typeBtn.toString());
        log.info(sexValue.toString());

        if(typeValue.toString()=="[]"){
            typeValue.add("강아지"); typeValue.add("고양이");
            typeValue.add("관상어"); typeValue.add("햄스터");
            typeValue.add("토끼"); typeValue.add("새");
            typeValue.add("거북이"); typeValue.add("기타");
        }

        if(typeValue.size()==1) {
            if(typeValue.contains("강아지") && typeBtn.size()==0) {
                typeBtn.add("시츄"); typeBtn.add("말티즈"); typeBtn.add("시바견");
                typeBtn.add("비숑"); typeBtn.add("포메라니안"); typeBtn.add("그레이하운드");
                typeBtn.add("푸들"); typeBtn.add("보더콜리"); typeBtn.add("웰시코기");
                typeBtn.add("리트리버"); typeBtn.add("진돗개"); typeBtn.add("귀한 종"); typeBtn.add("이외 견종");
            } else if(typeValue.contains("고양이") && typeBtn.size()==0){
                typeBtn.add("러시안 블루"); typeBtn.add("먼치킨"); typeBtn.add("터키시 앙고라");
                typeBtn.add("엑조틱"); typeBtn.add("메인쿤"); typeBtn.add("스핑크스");
                typeBtn.add("랙돌"); typeBtn.add("코리안 숏헤어"); typeBtn.add("아메리칸 숏헤어");
                typeBtn.add("브리티시 숏헤어"); typeBtn.add("페르시안"); typeBtn.add("귀한 종"); typeBtn.add("이외 묘종");
            }
        }

        if(sexValue.toString()=="[]"){
            sexValue.add("수컷");
            sexValue.add("암컷");
        }


        List<Post> posts;
        // 아무것도 선택안했을때 (ex. 페이지 처음 들어왔을때)
        if(typeValue.size()==8 && typeBtn.size()==0 && sexValue.size()==2) {
            posts = boardRepository.selectPosts("daily");
        }
        // 종 두개이상 선택했을때
        // (ex. 강아지, 햄스터 선택했을때는 typeBtn 상관없이 강아지의 모든 종을 보여줘야 함, sexValue는 고려해야 함)
        else if(typeValue.size()>1) {
            posts = boardRepository.findPostsIn1(typeValue, sexValue);
        }
        else {
            posts = boardRepository.findPostsIn2(typeValue, typeBtn, sexValue);
        }

        return posts;
    }


    @GetMapping("/board/boast")
    public List<Post> getBoastPosts(@RequestParam List typeValue, @RequestParam List typeBtn, @RequestParam List sexValue){

        if(typeValue.toString()=="[]"){
            typeValue.add("강아지"); typeValue.add("고양이");
            typeValue.add("관상어"); typeValue.add("햄스터");
            typeValue.add("토끼"); typeValue.add("새");
            typeValue.add("거북이"); typeValue.add("기타");
        }

        if(typeValue.size()==1) {
            if(typeValue.contains("강아지") && typeBtn.size()==0) {
                typeBtn.add("시츄"); typeBtn.add("말티즈"); typeBtn.add("시바견");
                typeBtn.add("비숑"); typeBtn.add("포메라니안"); typeBtn.add("그레이하운드");
                typeBtn.add("푸들"); typeBtn.add("보더콜리"); typeBtn.add("웰시코기");
                typeBtn.add("리트리버"); typeBtn.add("진돗개"); typeBtn.add("귀한 종"); typeBtn.add("이외 견종");
            } else if(typeValue.contains("고양이") && typeBtn.size()==0){
                typeBtn.add("러시안 블루"); typeBtn.add("먼치킨"); typeBtn.add("터키시 앙고라");
                typeBtn.add("엑조틱"); typeBtn.add("메인쿤"); typeBtn.add("스핑크스");
                typeBtn.add("랙돌"); typeBtn.add("코리안 숏헤어"); typeBtn.add("아메리칸 숏헤어");
                typeBtn.add("브리티시 숏헤어"); typeBtn.add("페르시안"); typeBtn.add("귀한 종"); typeBtn.add("이외 묘종");
            }
        }

        if(sexValue.toString()=="[]"){
            sexValue.add("수컷");
            sexValue.add("암컷");
        }


        List<Post> posts;
        // 아무것도 선택안했을때 (ex. 페이지 처음 들어왔을때)
        if(typeValue.size()==8 && typeBtn.size()==0 && sexValue.size()==2) {
            posts = boardRepository.selectPosts("boast");
        }
        // 종 두개이상 선택했을때
        // (ex. 강아지, 햄스터 선택했을때는 typeBtn 상관없이 강아지의 모든 종을 보여줘야 함, sexValue는 고려해야 함)
        else if(typeValue.size()>1) {
            posts = boardRepository.findPostsIn3(typeValue, sexValue);
        }
        else {
            posts = boardRepository.findPostsIn4(typeValue, typeBtn, sexValue);
        }

        return posts;
    }

    @GetMapping("/board/question")
    public List<Post> getQuestionPosts(){
        List<Post> posts = boardRepository.selectPosts("question");
        log.info(posts.toString());
        return posts;
    }

    @GetMapping("/board/recommendation")
    public List<Post> getRecommendationPosts(){
        List<Post> posts = boardRepository.selectPosts("recommendation");
        log.info(posts.toString());
        return posts;
    }

    // 프론트에서 받은 Image Url을 통해 KIC에서 이미지 받아오기
    @GetMapping("/get/img")
    public ResponseEntity<Object> getUserProfile(@RequestParam String imgUrl) { //(1)
        System.out.println(imgUrl);

        try {
            // RestTemplate 객체를 생성합니다.
            RestTemplate restTemplate = new RestTemplate();

            // header 설정을 위해 HttpHeader 클래스를 생성한 후 HttpEntity 객체에 넣어줍니다.
            HttpHeaders headers  = new HttpHeaders(); // 담아줄 header
            headers.add("X-Auth-Token", "gAAAAABj9ERb1ja9kfOAnLaEX6t-hwl4AwHbPW2bbLF6nZgyNSQ5oOGUDFWPP5xBZ6AAPVpK1oMt4C4-ZJFSS8qv8l5oUML7amQGRErZqDg9BoY7jwSK8rUuRfJCj5EGhqJb3wqceIRzt7U1AmwYcQ08wPKCAMLdwxTgwp71lpRmk4q5Yb9O0cDNR2CJhqUUSJUcbwTRzoMq");

            HttpEntity<String> entity = new HttpEntity<String>(headers);

            // exchange() 메소드로 api를 호출합니다.
            ResponseEntity<byte[]> response = restTemplate.exchange(imgUrl,HttpMethod.GET, entity, byte[].class);

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body("error");
        }// end catch

    }
}