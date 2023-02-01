package gcu.connext.petzzang.ranking.service;

import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.community.repository.PostRepository;
import gcu.connext.petzzang.ranking.repository.Weekly_ranking_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class rankingschedule {

    @Autowired
    private Weekly_ranking_Repository weekly_ranking_repository;

    @Autowired
    private PostRepository postRepository;

    @Async
    @Scheduled(fixedRate = 3600000)
// @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")  // 문자열 milliseconds 사용 시
    public void scheduleFixedRateTask() throws InterruptedException {
        List<Post> post=postRepository.finddate();
        System.out.println(post);
    }
}
