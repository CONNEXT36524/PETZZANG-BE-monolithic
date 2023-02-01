package gcu.connext.petzzang.community.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostingDTO {
    public Long postId;
    public Long userCode;
    public String titleName;
    public String content;
    public String thumbnail;
    public String boardType;
    public Timestamp createTime;
    public Timestamp updateTime;
    public Long views;
    public Long likeNum;
    public String pet;
    public String kind;
    public String sex;
}