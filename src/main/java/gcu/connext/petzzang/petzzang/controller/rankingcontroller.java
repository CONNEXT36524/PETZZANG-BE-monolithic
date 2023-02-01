package gcu.connext.petzzang.petzzang.controller;

import gcu.connext.petzzang.petzzang.entity.Weekly_ranking_entity;
import gcu.connext.petzzang.petzzang.repository.Monthly_ranking_Repository;
import gcu.connext.petzzang.petzzang.repository.Weekly_ranking_Repository;
import gcu.connext.petzzang.petzzang.service.rankingload_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@ResponseBody
@RequestMapping("/api")
public class rankingcontroller {

    @Autowired
    private Weekly_ranking_Repository weekly_ranking_repository;

    @Autowired
    private Monthly_ranking_Repository monthly_ranking_repository;

    private rankingload_service rankingload_service;

    @GetMapping("/ranking/load")
    public Weekly_ranking_entity getRanking(@RequestParam("date") String date){
        Weekly_ranking_entity ranking=weekly_ranking_repository.findbydate(date);
        System.out.println(ranking);

        return ranking;
    }

}
