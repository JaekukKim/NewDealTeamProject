package com.studycafe.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studycafe.team.entity.ResultAuthEntity;

@Repository
public interface ResultAuthRepository extends JpaRepository<ResultAuthEntity, Integer> {
	
}
