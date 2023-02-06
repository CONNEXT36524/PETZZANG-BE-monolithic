package gcu.connext.petzzang.ranking.ranking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class weeklyupdatedto {
    private Long first_post_id;
    private Long second_post_id;
    private Long third_post_id;
    private Long fourth_post_id;
    private Long fifth_post_id;
    private Long sixth_post_id;

    @Builder
    public weeklyupdatedto(Long first_post_id,Long second_post_id,Long third_post_id,Long fourth_post_id,Long fifth_post_id, Long sixth_post_id){
        this.first_post_id=first_post_id;
        this.second_post_id=second_post_id;
        this.third_post_id=third_post_id;
        this.fourth_post_id=fourth_post_id;
        this.fifth_post_id=fifth_post_id;
        this.sixth_post_id=sixth_post_id;
    }
}
