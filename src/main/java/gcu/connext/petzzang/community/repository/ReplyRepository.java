package gcu.connext.petzzang.community.repository;

import gcu.connext.petzzang.community.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // JPA findBy 규칙
    // select * from reply_master where kakao_email = ?


    List<Reply> findByPostId(Long postId);

    Reply findByReplyId(Long replyId);

    List<Reply> findByBundleId(Long bundleId);

    List<Reply> findByBundleOrder(Long bundleOrder);

    List<Reply> findByPostIdAndBundleId(Long postId, Long bundleId);

    @Transactional
    @Modifying
    @Query(value= "UPDATE replies_tb r SET r.is_deleted = :state, r.delete_time = :delete_time WHERE r.reply_id = :replyId", nativeQuery = true)
    int isDeleted(Long replyId, Boolean state, String delete_time);



}