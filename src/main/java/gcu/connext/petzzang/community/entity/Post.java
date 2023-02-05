package gcu.connext.petzzang.community.entity;

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
public class Post {

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

    @Column(name = "picture")
    private String thumbnail;

    @Column(name = "board_type")
    private String boardType;

    //@CreatedDate가 안되어서 다른 방법을 적용
    //@Column(name = "create_time", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Column(name = "create_time")
    @CreatedDate
    private Timestamp create_time;

    @LastModifiedDate
    @Column(name = "update_time")
    private Timestamp update_time;

    @Column(name = "views")
    private Long views;

    @Column(name = "likenum")
    private Long likeNum;

    @Column(name = "pet")
    private String pet;

    @Column(name = "kind")
    private String kind;

    @Column(name = "sex")
    private String sex;

}