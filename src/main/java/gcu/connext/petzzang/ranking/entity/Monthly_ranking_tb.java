package gcu.connext.petzzang.ranking.entity;

import javax.persistence.*;

import gcu.connext.petzzang.community.entity.Post;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "monthly_ranking_tb")
public class Monthly_ranking_tb {

    @Id
    @Column(name = "month_ranking", length = 45)
    private String month_ranking;

    @OneToOne
    @JoinColumn(name = "first_post_id", referencedColumnName = "post_id")
    private Post first_post_id;

    @OneToOne
    @JoinColumn(name = "second_post_id" ,referencedColumnName = "post_id")
    private Post second_post_id;

    @OneToOne
    @JoinColumn(name = "third_post_id", referencedColumnName = "post_id")
    private Post third_post_id;

    @OneToOne
    @JoinColumn(name = "fourth_post_id", referencedColumnName = "post_id")
    private Post fourth_post_id;

    @OneToOne
    @JoinColumn(name = "fifth_post_id", referencedColumnName = "post_id")
    private Post fifth_post_id;

    @OneToOne
    @JoinColumn(name = "sixth_post_id", referencedColumnName = "post_id")
    private Post sixth_post_id;

    public void update(Post first_post_id,Post second_post_id,Post third_post_id,Post fourth_post_id,Post fifth_post_id, Post sixth_post_id){
        this.first_post_id=first_post_id;
        this.second_post_id=second_post_id;
        this.third_post_id=third_post_id;
        this.fourth_post_id=fourth_post_id;
        this.fifth_post_id=fifth_post_id;
        this.sixth_post_id=sixth_post_id;
    }
}