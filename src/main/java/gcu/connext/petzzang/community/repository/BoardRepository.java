package gcu.connext.petzzang.community.repository;

import gcu.connext.petzzang.community.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Post, Long> {

    @Query(value = "select * from posts_tb where board_type=?", nativeQuery = true)
    List<Post> selectPosts(String boardName);

    @Query(value = "select * from posts_tb where board_type='daily' AND pet in :typeValue AND sex in :sexValue", nativeQuery = true)
    List<Post> findPostsIn1(List typeValue, List sexValue);

    @Query(value = "select * from posts_tb where board_type='daily' AND pet in :typeValue AND kind in :typeBtn AND sex in :sexValue", nativeQuery = true)
    List<Post> findPostsIn2(List typeValue, List typeBtn, List sexValue);

    @Query(value = "select * from posts_tb where board_type='boast' AND pet in :typeValue AND sex in :sexValue", nativeQuery = true)
    List<Post> findPostsIn3(List typeValue, List sexValue);

    @Query(value = "select * from posts_tb where board_type='boast' AND pet in :typeValue AND kind in :typeBtn AND sex in :sexValue", nativeQuery = true)
    List<Post> findPostsIn4(List typeValue, List typeBtn, List sexValue);

    //search
    List<Post> findByPetContainingOrKindContainingOrTitleNameContainingOrContentContaining(String keyword1, String keyword2, String keyword3, String keyword4);

}
