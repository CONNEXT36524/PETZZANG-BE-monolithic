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

    //postId로 게시글 삭제하기
    @Transactional
    void deleteByPostId(Long postId);

    Post findByBoardType(Long boardType);

    Post findByPostId(Long postCode);


        @Query(value = "select * from posts_tb where DATE(create_time)>=DATE_SUB(NOW(),INTERVAL (DAYOFWEEK(NOW())) DAY) ORDER BY views DESC", nativeQuery = true)
        public List<Post> findweekdate();

    @Query(value = "select * from posts_tb where month(create_time)=month(NOW()) ORDER BY views DESC", nativeQuery = true)
    public List<Post> findmonthdate();

}