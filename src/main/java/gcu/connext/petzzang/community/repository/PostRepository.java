package gcu.connext.petzzang.community.repository;

import gcu.connext.petzzang.community.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    // JPA findBy 규칙

    Post findByBoardType(Long boardType);

    Post findByPostId(Long postCode);
}