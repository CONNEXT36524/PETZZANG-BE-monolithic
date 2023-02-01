package gcu.connext.petzzang.ranking.service;

import gcu.connext.petzzang.ranking.repository.Weekly_ranking_Repository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class rankingschedule {

    private Weekly_ranking_Repository weekly_ranking_repository;

    @Async
    @Scheduled(fixedRate = 1000)
// @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")  // 문자열 milliseconds 사용 시
    public void scheduleFixedRateTask() throws InterruptedException {

    }
}
