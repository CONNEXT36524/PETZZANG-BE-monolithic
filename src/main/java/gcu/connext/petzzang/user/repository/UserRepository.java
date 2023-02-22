package gcu.connext.petzzang.user.repository;

import gcu.connext.petzzang.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    // JPA findBy 규칙
    // select * from user_master where kakao_email = ?
    User findByKakaoEmail(String kakaoEmail);

    User findByUserCode(Long userCode);

    User findByKakaoNickname(String nickName);

    boolean existsBykakaoNickname (String nameCheck);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user set kakaoNickname = :kakaoNickname where userCode = :userCode", nativeQuery = true)
    int updateUser (Long kakaoNickname, String userCode);

    User findByKakaoId(Long userCode);

}