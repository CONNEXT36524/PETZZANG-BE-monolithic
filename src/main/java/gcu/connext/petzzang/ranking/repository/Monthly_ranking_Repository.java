package gcu.connext.petzzang.ranking.repository;


import gcu.connext.petzzang.ranking.entity.Monthly_ranking_tb;
import gcu.connext.petzzang.ranking.entity.Weekly_ranking_entity;
import gcu.connext.petzzang.ranking.ranking.dto.weeklyresponsedto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Monthly_ranking_Repository extends JpaRepository<Monthly_ranking_tb, String> {
    @Query(value = "select * from monthly_ranking_tb where month_ranking= :date", nativeQuery = true)
    public Monthly_ranking_tb findbydate(@Param(value = "date") String date);
}