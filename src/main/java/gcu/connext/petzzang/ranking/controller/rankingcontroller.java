package gcu.connext.petzzang.ranking.controller;


import gcu.connext.petzzang.ranking.entity.Monthly_ranking_tb;
import gcu.connext.petzzang.ranking.entity.Weekly_ranking_entity;
import gcu.connext.petzzang.ranking.ranking.dto.weeklyresponsedto;
import gcu.connext.petzzang.ranking.repository.Monthly_ranking_Repository;
import gcu.connext.petzzang.ranking.repository.Weekly_ranking_Repository;
import gcu.connext.petzzang.ranking.service.rankingload_service;
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

    private gcu.connext.petzzang.ranking.service.rankingload_service rankingload_service;

    @GetMapping("/ranking/load")
    public weeklyresponsedto getRanking(@RequestParam("date") String date, @RequestParam("type") String type){
        if(type.equals("week")) {
            Weekly_ranking_entity ranking = weekly_ranking_repository.findbydate(date);
            System.out.println(ranking);
            if(ranking==null){
                return null;
            }
            else return new weeklyresponsedto(ranking);
        }
        else if(type.equals("month"))
        {
            Monthly_ranking_tb ranking=monthly_ranking_repository.findbydate(date.substring(0,6));
            if(ranking==null){
                return null;
            }
            else return new weeklyresponsedto(ranking);
        }
        else{System.out.println("error");
        return null;}

    }

}
