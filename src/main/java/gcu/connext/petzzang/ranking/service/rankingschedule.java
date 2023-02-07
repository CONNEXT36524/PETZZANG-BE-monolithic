package gcu.connext.petzzang.ranking.service;

import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.community.repository.PostRepository;
import gcu.connext.petzzang.ranking.ranking.dto.weeklyrequestdto;
import gcu.connext.petzzang.ranking.ranking.dto.weeklyupdatedto;
import gcu.connext.petzzang.ranking.repository.Weekly_ranking_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class rankingschedule {

    setranking setranking=new setranking();
    @Autowired
    rankingload_service rankingload_service=new rankingload_service();
    @Autowired
    monthranking_service monthranking_service=new monthranking_service();
    @Autowired
    private Weekly_ranking_Repository weekly_ranking_repository;

    @Autowired
    private PostRepository postRepository;

    @Async
    @Scheduled(fixedRate = 3600000)
// @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")  // 문자열 milliseconds 사용 시
    public void scheduleFixedRateTask() throws InterruptedException {

        List<Post> weekpost=postRepository.findweekdate();
        List<Post> monthpost=postRepository.findmonthdate();
        ArrayList<Long> weekresult= setranking.calculate(weekpost);
        ArrayList<Long> monthresult= setranking.calculate(monthpost);
        System.out.println(weekresult);
        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();
        LocalDate standard= now.minusDays(now.getDayOfWeek().getValue()%7);
        // 포맷 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        // 포맷 적용
        String formatedstandard = standard.format(formatter);
        // 결과 출력
        System.out.println(formatedstandard); // 20211202

        if(rankingload_service.findById(formatedstandard)==null)
        {
            weeklyrequestdto data= new weeklyrequestdto(formatedstandard,weekresult.get(0),weekresult.get(1),weekresult.get(2),weekresult.get(3),weekresult.get(4),weekresult.get(5));
            rankingload_service.save(data);
        }
        else{
            weeklyupdatedto updatedata= new weeklyupdatedto(weekresult.get(0),weekresult.get(1),weekresult.get(2),weekresult.get(3),weekresult.get(4),weekresult.get(5));
            rankingload_service.update(formatedstandard,updatedata);
        }
        if(rankingload_service.findById(formatedstandard.substring(0,6))==null)
        {
            weeklyrequestdto monthdata= new weeklyrequestdto(formatedstandard.substring(0,6),monthresult.get(0),monthresult.get(1),monthresult.get(2),monthresult.get(3),monthresult.get(4),monthresult.get(5));
            monthranking_service.save(monthdata);
        }
        else{
            weeklyupdatedto monthupdatedata= new weeklyupdatedto(monthresult.get(0),monthresult.get(1),monthresult.get(2),monthresult.get(3),monthresult.get(4),monthresult.get(5));
            monthranking_service.update(formatedstandard.substring(0,6),monthupdatedata);
        }
    }
}
