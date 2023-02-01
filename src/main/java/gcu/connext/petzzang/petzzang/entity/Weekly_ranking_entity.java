package gcu.connext.petzzang.petzzang.entity;

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

}