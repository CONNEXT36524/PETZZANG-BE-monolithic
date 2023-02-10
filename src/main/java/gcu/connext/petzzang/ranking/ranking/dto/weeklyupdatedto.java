package gcu.connext.petzzang.ranking.ranking.dto;

import gcu.connext.petzzang.community.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class weeklyupdatedto {
    private Post first_post_id;
    private Post second_post_id;
    private Post third_post_id;
    private Post fourth_post_id;
    private Post fifth_post_id;
    private Post sixth_post_id;

    @Builder
    public weeklyupdatedto(Post first_post_id,Post second_post_id,Post third_post_id,Post fourth_post_id,Post fifth_post_id, Post sixth_post_id){
        this.first_post_id=first_post_id;
        this.second_post_id=second_post_id;
        this.third_post_id=third_post_id;
        this.fourth_post_id=fourth_post_id;
        this.fifth_post_id=fifth_post_id;
        this.sixth_post_id=sixth_post_id;
    }
}
