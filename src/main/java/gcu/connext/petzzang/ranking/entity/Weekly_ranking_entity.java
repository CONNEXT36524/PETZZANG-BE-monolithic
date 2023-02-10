package gcu.connext.petzzang.ranking.entity;

import javax.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "weekly_ranking_tb")
public class Weekly_ranking_entity {
    @Id
    @Column(name = "week_ranking", length = 45)
    private String week_ranking;

    @Column(name = "first_post_id")
    private Long first_post_id;

    @Column(name = "second_post_id")
    private Long second_post_id;

    @Column(name = "third_post_id")
    private Long third_post_id;

    @Column(name = "fourth_post_id")
    private Long fourth_post_id;

    @Column(name = "fifth_post_id")
    private Long fifth_post_id;

    @Column(name = "sixth_post_id")
    private Long sixth_post_id;

    public void update(Long first_post_id,Long second_post_id,Long third_post_id,Long fourth_post_id,Long fifth_post_id, Long sixth_post_id){
        this.first_post_id=first_post_id;
        this.second_post_id=second_post_id;
        this.third_post_id=third_post_id;
        this.fourth_post_id=fourth_post_id;
        this.fifth_post_id=fifth_post_id;
        this.sixth_post_id=sixth_post_id;
    }

}