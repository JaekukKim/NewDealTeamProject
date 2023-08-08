package com.studycafe.team.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.studycafe.team.dto.TeamMonthCountDto;
import com.studycafe.team.dto.TopTeamDto;
import com.studycafe.team.entity.TeamEntity;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

	@Query(value = "SELECT team_name as teamName, point FROM team_entity ORDER BY point DESC LIMIT 6", nativeQuery = true)
	List<TopTeamDto> findTopTeam();

	@Query(value = "SELECT team_name as teamName, point FROM team_entity ORDER BY approve_count DESC LIMIT 6", nativeQuery = true)
	List<TopTeamDto> findTopApproveTeam();

	@Query(value = "SELECT MONTHNAME(create_date) as month, COUNT(*) as count FROM team_entity GROUP BY MONTH(create_date)", nativeQuery = true)
	List<TeamMonthCountDto> findApproveTeamByMonth();

	@Query(value = "SELECT * FROM team_entity WHERE approve_date IS NULL", nativeQuery = true)
	List<TeamEntity> findNotApprovedTeam();

	public boolean existsByTeamName(String teamName);

	public TeamEntity findByTeamNumber(long TeamNumber);

}
