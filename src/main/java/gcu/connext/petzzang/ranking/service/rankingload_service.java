package gcu.connext.petzzang.ranking.service;

import gcu.connext.petzzang.ranking.entity.Weekly_ranking_entity;
import gcu.connext.petzzang.ranking.ranking.dto.weeklyrequestdto;
import gcu.connext.petzzang.ranking.ranking.dto.weeklyresponsedto;
import gcu.connext.petzzang.ranking.ranking.dto.weeklyupdatedto;
import gcu.connext.petzzang.ranking.repository.Weekly_ranking_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor    //final or NonNull 옵션 필드를 전부 포함한 생성자 만들어줌
@Service
public class rankingload_service {
    @Autowired
    public Weekly_ranking_Repository weekly_ranking_repository;


        @Transactional  //db 트랜잭션 자동으로 commit 해줌
        public void save(weeklyrequestdto requestDto) {
            //dto 를 entity 화 해서 repository 의 save 메소드를 통해 db 에 저장.
            //저장 후 생성한 id 반환해줌

                Weekly_ranking_entity savedata=requestDto.toEntity();
                weekly_ranking_repository.save(savedata);

        }

        @Transactional
        public String update(String id, weeklyupdatedto requestDto) {
            Weekly_ranking_entity ranking = weekly_ranking_repository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

            //JPA 의 영속성 컨텍스트 덕분에 entity 객체의 값만 변경하면 자동으로 변경사항 반영함!
            //따라서 repository.update 를 쓰지 않아도 됨.
            ranking.update(requestDto.getFirst_post_id(), requestDto.getSecond_post_id(),requestDto.getThird_post_id(),requestDto.getFourth_post_id(),requestDto.getFifth_post_id(),requestDto.getSixth_post_id());

            return id;
        }

        public Integer findById(String id) {
            Optional<Weekly_ranking_entity> entity = weekly_ranking_repository.findById(id);
            if(entity.isEmpty())return null;
            else return 1;
        }
    }


