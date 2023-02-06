package gcu.connext.petzzang.community.service;

import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.community.entity.Reply;
import gcu.connext.petzzang.community.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ReplyService {


    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public List<Reply> getReply(Integer postId) {
        List<Reply> replyList = replyRepository.findByPostId(Long.valueOf(postId));

        return replyList;
    }
    //mysql에 댓글 저장하기
    public Reply uploadReply(Reply reply) {
        return replyRepository.save(reply);
    }
}