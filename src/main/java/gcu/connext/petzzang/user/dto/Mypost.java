package gcu.connext.petzzang.user.dto;

import gcu.connext.petzzang.PetzzangApplication;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.ConnectionBuilder;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "posts_tb") //(1)
public class Mypost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //(2)
    @Column(name = "post_id") //(3)
    private Long postId;

    @Column(name = "user_code")
    private Long userCode;

    @Column(name = "title_name")
    private String titleName;

    @Column(name = "content")
    private String content;

    @LastModifiedDate
    @Column(name = "update_time")
    private Timestamp update_time;

    @Column(name = "views")
    private Long views;

    @Column(name = "likenum")
    private Long likeNum;

    @Column(name = "board_type")
    private String board_type;

}
