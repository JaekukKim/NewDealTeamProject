package com.studycafe.team.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studycafe.team.entity.ResultAuthEntity;
import com.studycafe.team.repository.ResultAuthRepository;

@Service("resultAuthService")
public class ResultAuthServiceImpl implements ResultAuthService{
	
	@Autowired
	private ResultAuthRepository resultAuthRepository;

	@Override
	public void resultAuthInsert(ResultAuthEntity resultAuthEntity) {
		// TODO Auto-generated method stub
		resultAuthRepository.save(resultAuthEntity);
	}
}
