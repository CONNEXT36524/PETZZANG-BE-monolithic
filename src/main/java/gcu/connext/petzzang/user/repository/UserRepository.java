package gcu.connext.petzzang.user.repository;

import gcu.connext.petzzang.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // JPA findBy 규칙
    // select * from user_master where kakao_email = ?
    User findByKakaoEmail(String kakaoEmail);

    User findByUserCode(Long userCode);

    User findByKakaoId(Long userCode);
}