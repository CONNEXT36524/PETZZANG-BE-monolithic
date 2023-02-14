package gcu.connext.petzzang.ranking.ranking.dto;


import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.ranking.entity.Monthly_ranking_tb;
import gcu.connext.petzzang.ranking.entity.Weekly_ranking_entity;
import lombok.Getter;

@Getter
public class weeklyresponsedto {
    private String week_ranking;
    private Post first_post_id;
    private Post second_post_id;
    private Post third_post_id;
    private Post fourth_post_id;
    private Post fifth_post_id;
    private Post sixth_post_id;

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
