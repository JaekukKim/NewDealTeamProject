package com.studycafe.qna.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.studycafe.qna.entity.QnaEntity;

public interface QnaService {
	public Page<QnaEntity> qnaList(Pageable pageable);
	
	public Page<QnaEntity> qnaSearchList(String keyword, Pageable pageable);
	
	//isDeleted가 0인것만 불러오
    public Page<QnaEntity> findByIsDeletedEquals(int isDeletedValue, Pageable pageable);
	
	public void qnaRegister(QnaEntity qnaEntity); 

	public QnaEntity selectQna(Long qnaNum);
	
	public void qnaDelete(long qnaNum);
}
