package gcu.connext.petzzang.community.service;

import gcu.connext.petzzang.community.entity.Reply;
import gcu.connext.petzzang.community.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ReplyService {


    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public List<Reply> getReply(Integer postId) {
        List<Reply> replyList = replyRepository.findByPostId(Long.valueOf(postId));
        Collections.sort(replyList, new ReplyIdComparator());
        return replyList;
    }

    @Transactional
    public List<Reply> getNReply(Integer postId, Integer bundleId) {
        List<Reply> replyList = replyRepository.findByPostIdAndBundleId(Long.valueOf(postId), Long.valueOf(bundleId));
        Collections.sort(replyList, new ReplyIdComparator());
        return replyList;
    }

    //mysql에 댓글 저장하기
    public Reply uploadReply(Reply reply) {
        List<Reply> replyList = replyRepository.findByPostId(Long.valueOf(reply.getPostId()));
        if (replyList.size() != 0)
        {
            Collections.sort(replyList, new ReplyBundleIdComparator());
            Long latestBundleId = replyList.get(replyList.size() -1).getBundleId();
            reply.setBundleId(latestBundleId +  1);
        }
            return replyRepository.save(reply);
    }

    public Reply uploadNReply(Reply reply) {

        Long postId = reply.getPostId();
        Long bundleId = reply.getBundleId();
        List<Reply> replyList = replyRepository.findByPostIdAndBundleId(postId, bundleId);
        if (replyList.size() != 0)
        {
            Collections.sort(replyList, new ReplyBundleOrderComparator());
            Long latestBundleOrder = replyList.get(replyList.size() - 1).getBundleOrder();
            reply.setBundleOrder(latestBundleOrder + 1);
        }
            return replyRepository.save(reply);

    }

    class ReplyIdComparator implements Comparator<Reply> {
        @Override
        public int compare(Reply r1, Reply r2) {
            if (r1.getReplyId() > r2.getReplyId()) {
                return 1;
            } else if (r1.getReplyId() < r2.getReplyId()) {
                return -1;
            }
            return 0;
        }
    }

    class ReplyBundleIdComparator implements Comparator<Reply> {
        @Override
        public int compare(Reply r1, Reply r2) {
            if (r1.getBundleId() > r2.getBundleId()) {
                return 1;
            } else if (r1.getBundleId() < r2.getBundleId()) {
                return -1;
            }
            return 0;
        }
    }

    class ReplyBundleOrderComparator implements Comparator<Reply> {
        @Override
        public int compare(Reply r1, Reply r2) {
            if (r1.getBundleOrder() > r2.getBundleOrder()) {
                return 1;
            } else if (r1.getBundleOrder() < r2.getBundleOrder()) {
                return -1;
            }
            return 0;
        }
    }
}