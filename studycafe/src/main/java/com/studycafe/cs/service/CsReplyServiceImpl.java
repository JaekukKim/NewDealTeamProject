package com.studycafe.cs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studycafe.cs.entity.CsReplyEntity;
import com.studycafe.cs.repository.CsReplyRepository;

@Service
public class CsReplyServiceImpl implements CsReplyService {
	
	@Autowired
	private CsReplyRepository csReplyRepository;
	
	@Override
	public void CsReplyInsert(CsReplyEntity csReplyEntity) {
		// TODO Auto-generated method stub
		csReplyRepository.save(csReplyEntity);
	}

	@Override
	public List<CsReplyEntity> CsReplyList(long idx) {
		// TODO Auto-generated method stub
		return csReplyRepository.findByCsEntity(idx);
	}

	@Override
	public int CsReplyCount(long id) {
		// TODO Auto-generated method stub
		return csReplyRepository.replycount(id);
	}

	@Override
	public void CsReplyDelete(Long id) {
		// TODO Auto-generated method stub
		csReplyRepository.deleteById(id);		
	}

}
