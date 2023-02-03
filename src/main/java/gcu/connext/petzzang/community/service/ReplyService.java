package gcu.connext.petzzang.community.service;

import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.community.entity.Reply;
import gcu.connext.petzzang.community.repository.PostRepository;
import gcu.connext.petzzang.community.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {


    @Autowired
    private ReplyRepository replyRepository;

    //mysql에 댓글 저장하기
    public Reply uploadReply(Reply reply) {
        return replyRepository.save(reply);
    }
}