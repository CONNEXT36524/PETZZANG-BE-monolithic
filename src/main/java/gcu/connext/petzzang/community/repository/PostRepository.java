package gcu.connext.petzzang.community.repository;

import gcu.connext.petzzang.community.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    // JPA findBy 규칙
    // select * from posts_db where postId = ?
    Post findByPostId(Long postId);
}