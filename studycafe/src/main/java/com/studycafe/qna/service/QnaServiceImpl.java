package com.studycafe.qna.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.studycafe.qna.entity.QnaEntity;
import com.studycafe.qna.repository.QnaRepository;

@Service
public class QnaServiceImpl implements QnaService {

	@Autowired
	private QnaRepository qnaRe;

	@Override
	public Page<QnaEntity> qnaList(Pageable pageable) {
		// TODO Auto-generated method stub
		return qnaRe.findAll(pageable);
	}

	@Override
	public Page<QnaEntity> qnaSearchList(String keyword, Pageable pageable) {
		// TODO Auto-generated method stub

		Page<QnaEntity> list = qnaRe.findByqnaTitleContaining(keyword, pageable);

		return list;
	}

	@Override
	public void qnaRegister(QnaEntity qnaEntity) {
		// TODO Auto-generated method stub

		qnaRe.save(qnaEntity);

	}

	// @Override
	// public Page<QnaEntity> qnaSearchList(String keyword, Pageable pageable, int isDeletedValue) {
	// // TODO Auto-generated method stub
	//
	// Page<QnaEntity> list = qnaRe.qnaSerchList(pageable, keyword, isDeletedValue);
	// return list;
	// }

	@Override
	public QnaEntity selectQna(Long qnaNum) {
		// TODO Auto-generated method stub

		return qnaRe.getById(qnaNum);
	}

	@Override
	public void qnaDelete(long qnaNum) {
		// TODO Auto-generated method stub

		qnaRe.deleteById(qnaNum);

	}

	// isdeleted가 0인것만 불러오기
	@Override
	public Page<QnaEntity> findByIsDeletedEquals(int isDeletedValue, Pageable pageable) {
		// TODO Auto-generated method stub
		Page<QnaEntity> list = qnaRe.findByIsDeletedEquals(isDeletedValue, pageable);

		return list;
	}

	// qna 게시판 인덱스용
	@Override
	@Transactional
	public List<QnaEntity> getQnaBoardListToIndex() {
		// TODO Auto-generated method stub
		return qnaRe.findAll(Sort.by(Sort.Direction.DESC, "qnaNum"));
	}

}
