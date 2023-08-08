package com.studycafe.cs.service;

import java.util.List;

import com.studycafe.cs.entity.CsReplyEntity;

public interface CsReplyService {
	public void CsReplyInsert(CsReplyEntity csReplyEntity); // 댓글 입력
	public List<CsReplyEntity> CsReplyList(long idx); // 댓글 리스트
	public int CsReplyCount(long id); // 댓글 카운트
	public void CsReplyDelete(Long id); // 댓글 삭제
}
