package gcu.connext.petzzang.community.repository;

import gcu.connext.petzzang.community.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // JPA findBy 규칙
    // select * from reply_master where kakao_email = ?

    Reply findByBundleId(Long replyId);
}