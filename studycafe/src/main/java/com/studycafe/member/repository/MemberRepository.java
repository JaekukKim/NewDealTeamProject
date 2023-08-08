package com.studycafe.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studycafe.member.entity.MemberEntity;
import com.studycafe.team.entity.TeamEntity;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {

	public MemberEntity findByNickName(String nickName);

	@Query(value = "SELECT COUNT(*) FROM member_entity WHERE created_at >= NOW() - INTERVAL 3 DAY", nativeQuery = true)
	public int findNewUser();

	public boolean existsByUsername(String username);
	
	public boolean existsByNickName(String nickName);

	public boolean existsByEmail(String email);

	public MemberEntity findByEmail(String email);
	
	public MemberEntity findByUsername(String username);

	public MemberEntity findByUsernameAndEmail(String username, String email);

	@Query(value = "SELECT COUNT(1) FROM member_entity WHERE nick_name = :nickName", nativeQuery = true)
	public int checkNick(String nickName);

	public List<MemberEntity> findByTeamNumber(TeamEntity teamNumber);

	public List<MemberEntity> findByUsernameContainingIgnoreCase(String username);

	public MemberEntity findByUsernameAndTeamNumberTeamNumber(String username, long teamNumber);
	
}
