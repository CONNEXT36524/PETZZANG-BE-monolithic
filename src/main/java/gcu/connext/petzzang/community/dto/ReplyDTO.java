package gcu.connext.petzzang.community.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Builder
public class ReplyDTO {
    public Long replyId;

    public Long postId;

    public String boardType;

    public Long bundleId;

    public Long bundleOrder;

    public Long userCode;

    public String content;

    public Boolean isDeleted;

    public Timestamp createTime;

    public Timestamp updateTime;

    public Timestamp deleteTime;


}