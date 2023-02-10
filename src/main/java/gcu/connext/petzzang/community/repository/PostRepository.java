package gcu.connext.petzzang.community.repository;

import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.ranking.entity.Weekly_ranking_entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // JPA findBy 규칙
    // select * from posts_db where postId = ?
    Post findByPostId(Long postId);

    @Transactional
    @Modifying
    @Query(value= "UPDATE posts_tb p SET p.likenum = :likeNum WHERE p.post_id = :postId", nativeQuery = true)
    int updateLikeNum(Long postId, Long likeNum);
    
    Post findByBoardType(Long boardType);

    Post findByPostId(Long postCode);


        @Query(value = "select * from posts_tb where DATE(create_time)>=DATE_SUB(NOW(),INTERVAL (DAYOFWEEK(NOW())) DAY) ORDER BY views DESC", nativeQuery = true)
        public List<Post> findweekdate();

    @Query(value = "select * from posts_tb where month(create_time)=month(NOW()) ORDER BY views DESC", nativeQuery = true)
    public List<Post> findmonthdate();


}