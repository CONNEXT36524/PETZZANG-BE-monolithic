package gcu.connext.petzzang.community.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "replies_tb") //(1)
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //(2)
    @Column(name = "reply_id") //(3)
    private Long replyId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "board_type")
    private Long boardType;

    @Column(name = "bundle_id")
    private Long bundleId;

    @Column(name = "bundle_order")
    private Long bundleOrder;

    @Column(name = "user_code")
    private Long userCode;

    @Column(name = "content")
    private String content;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "delete_time")
    private Timestamp deleteTime;

}