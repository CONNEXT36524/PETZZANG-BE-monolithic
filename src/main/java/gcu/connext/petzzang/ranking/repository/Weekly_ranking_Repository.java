package gcu.connext.petzzang.ranking.repository;

import gcu.connext.petzzang.ranking.entity.Weekly_ranking_entity;
import gcu.connext.petzzang.ranking.ranking.dto.weeklyresponsedto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Weekly_ranking_Repository extends JpaRepository<Weekly_ranking_entity, String> {

    @Query(value = "select * from weekly_ranking_tb where week_ranking= :date", nativeQuery = true)
    public Weekly_ranking_entity findbydate(@Param(value = "date") String date);
}