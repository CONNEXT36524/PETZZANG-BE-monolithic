package gcu.connext.petzzang.community.repository;

import gcu.connext.petzzang.community.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // JPA findBy 규칙
    // select * from posts_db where postId = ?
    Post findByPostId(Long postId);

    @Transactional
    @Modifying
    @Query(value= "UPDATE posts_tb p SET p.likenum = :likeNum WHERE p.post_id = :postId", nativeQuery = true)
    int updateLikeNum(Long postId, Long likeNum);
}