package gcu.connext.petzzang.community.repository;

import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.ranking.entity.Weekly_ranking_entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // JPA findBy 규칙
    // select * from posts_db where postId = ?

    //postId로 게시글 불러오기
    Post findByPostId(Long postId);

    //postId로 게시글 삭제하기
    @Transactional
    void deleteByPostId(Long postId);

    Post findByBoardType(Long boardType);

    @Query(value = "select * from posts_tb where DATE(create_time)>=DATE_SUB(NOW(),INTERVAL (DAYOFWEEK(NOW())) DAY) AND board_type='boast' ORDER BY views DESC", nativeQuery = true)
    public List<Post> findweekdate();

    @Query(value = "select * from posts_tb where month(create_time)=month(NOW()) AND board_type='boast' ORDER BY views DESC", nativeQuery = true)
    public List<Post> findmonthdate();

    @Transactional
    @Modifying
    @Query(value= "UPDATE posts_tb p SET p.likenum = :likeNum WHERE p.post_id = :postId", nativeQuery = true)
    int updateLikeNum(Long postId, Long likeNum);

    @Transactional
    @Modifying
    @Query(value= "UPDATE posts_tb p SET p.views = :view WHERE p.post_id = :postId", nativeQuery = true)
   int updateView(Long postId, Long view);
}