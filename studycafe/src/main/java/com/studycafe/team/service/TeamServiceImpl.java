package com.studycafe.team.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studycafe.team.dto.TeamMonthCountDto;
import com.studycafe.team.dto.TopTeamDto;
import com.studycafe.team.entity.TeamEntity;
import com.studycafe.team.repository.TeamRepository;

@Service("teamService")
public class TeamServiceImpl implements TeamService {

	@Autowired
	private TeamRepository teamRepository;

	@Override
	public TeamEntity teamInsert(TeamEntity teamEntity) {
		// TODO Auto-generated method stub
		return teamRepository.save(teamEntity);

	}

	@Override
	public List<TeamEntity> getAllTeam() {
		// TODO Auto-generated method stub

		return teamRepository.findAll();
	}

	@Override
	public List<TopTeamDto> getTopTeamByPoint() {
		// TODO Auto-generated method stub
		return teamRepository.findTopTeam();
	}

	@Override
	public List<TopTeamDto> getTopTeamByApproveCount() {
		// TODO Auto-generated method stub
		return teamRepository.findTopApproveTeam();
	}

	@Override
	public List<TeamMonthCountDto> getNewTeamByMonth() {
		// TODO Auto-generated method stub
		return teamRepository.findApproveTeamByMonth();
	}

	@Override
	public List<TeamEntity> getNotApprovedTeam() {
		// TODO Auto-generated method stub
		return teamRepository.findNotApprovedTeam();
	}

	@Override
	public void approveTeam(Long teamId) {
//		teamRepository.approveTeam(teamId);
		TeamEntity teamEntity = teamRepository.findById(teamId).orElseThrow(IllegalArgumentException::new);
		teamEntity.setApproveDate(LocalDate.now());
		teamRepository.saveAndFlush(teamEntity);
			
		
	}

	@Override
	public void disapproveTeam(Long teamId) {
//		teamRepository.disapproveTeam(teamId);
		TeamEntity teamEntity = teamRepository.findById(teamId).orElseThrow(IllegalArgumentException::new);
		teamEntity.setApproveDate(null);
		teamRepository.saveAndFlush(teamEntity);
		
	}

	@Override
	public boolean findTeamByName(String name) {
		// TODO Auto-generated method stub
		return teamRepository.existsByTeamName(name);
	}

	@Override
	public TeamEntity getMyTeam(long teamNumber) {
		TeamEntity team = teamRepository.findByTeamNumber(teamNumber);
		return team;
	}



}
