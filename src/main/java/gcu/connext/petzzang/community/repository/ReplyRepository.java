package gcu.connext.petzzang.community.repository;

import gcu.connext.petzzang.community.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // JPA findBy 규칙
    // select * from reply_master where kakao_email = ?


    List<Reply> findByPostId(Long postId);

    List<Reply> findByReplyId(Long replyId);

    List<Reply> findByBundleId(Long bundleId);

    List<Reply> findByBundleOrder(Long bundleOrder);

    List<Reply> findByPostIdAndBundleId(Long postId, Long bundleId);

}