package gcu.connext.petzzang.user.repository;

import gcu.connext.petzzang.user.dto.Mypost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MypostRepository extends JpaRepository<Mypost, Long> {
    List<Mypost> findByUserCode(Long userCode);
}