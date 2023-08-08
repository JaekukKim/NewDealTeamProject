package com.studycafe.qna.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.studycafe.qna.entity.QnaEntity;

public interface QnaService {
	public Page<QnaEntity> qnaList(Pageable pageable);

	// public Page<QnaEntity> qnaSearchList(String keyword, Pageable pageable, int isDeletedValue);

	// isDeleted가 0인것만 불러오
	public Page<QnaEntity> findByIsDeletedEquals(int isDeletedValue, Pageable pageable);

	public void qnaRegister(QnaEntity qnaEntity);

	public QnaEntity selectQna(Long qnaNum);

	public void qnaDelete(long qnaNum);

	public Page<QnaEntity> qnaSearchList(String keyword, Pageable pageable);

	// qna 게시판 리스트 인덱스 페이지
	public List<QnaEntity> getQnaBoardListToIndex();
}
