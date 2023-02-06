package gcu.connext.petzzang.ranking.ranking.dto;


import gcu.connext.petzzang.ranking.entity.Monthly_ranking_tb;
import gcu.connext.petzzang.ranking.entity.Weekly_ranking_entity;
import lombok.Getter;

@Getter
public class weeklyresponsedto {
    private String week_ranking;
    private Long first_post_id;
    private Long second_post_id;
    private Long third_post_id;
    private Long fourth_post_id;
    private Long fifth_post_id;
    private Long sixth_post_id;

    public weeklyresponsedto(Weekly_ranking_entity entity){
        this.week_ranking= entity.getWeek_ranking();
        this.first_post_id= entity.getFirst_post_id();
        this.second_post_id= entity.getSecond_post_id();
        this.third_post_id= entity.getThird_post_id();
        this.fourth_post_id= entity.getFourth_post_id();
        this.fifth_post_id= entity.getFifth_post_id();
        this.sixth_post_id= entity.getSixth_post_id();
    }

    public weeklyresponsedto(Monthly_ranking_tb entity){
        this.week_ranking= entity.getMonth_ranking();
        this.first_post_id= entity.getFirst_post_id();
        this.second_post_id= entity.getSecond_post_id();
        this.third_post_id= entity.getThird_post_id();
        this.fourth_post_id= entity.getFourth_post_id();
        this.fifth_post_id= entity.getFifth_post_id();
        this.sixth_post_id= entity.getSixth_post_id();
    }

}
