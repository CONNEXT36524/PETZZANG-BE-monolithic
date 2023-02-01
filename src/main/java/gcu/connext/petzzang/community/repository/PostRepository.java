package gcu.connext.petzzang.community.repository;

import gcu.connext.petzzang.community.entity.Post;
import gcu.connext.petzzang.ranking.entity.Weekly_ranking_entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // JPA findBy 규칙

    Post findByBoardType(Long boardType);

    Post findByPostId(Long postCode);


        @Query(value = "select * from posts_tb where create_time>=DATE_SUB(NOW(),INTERVAL (DAYOFWEEK(NOW())-1) DAY) ORDER BY views DESC", nativeQuery = true)
        public List<Post> finddate();

}