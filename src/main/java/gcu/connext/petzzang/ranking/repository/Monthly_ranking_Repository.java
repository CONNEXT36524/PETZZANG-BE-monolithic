package gcu.connext.petzzang.ranking.repository;


import gcu.connext.petzzang.ranking.entity.Monthly_ranking_tb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Monthly_ranking_Repository extends JpaRepository<Monthly_ranking_tb, String> {
}