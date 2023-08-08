package com.studycafe.study.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.studycafe.study.dto.StudyByMonthDto;
import com.studycafe.study.entity.StudyEntity;

public interface StudyService {

	public void studyInsert(StudyEntity studyEntity); // 스터디 모집 게시글 등록
	public StudyEntity studySelect(int id); // 스터디 모집 게시글 검색
	public List<StudyEntity> studySelectByMap(double lat, double lon, LocalDate date);
	public Page<StudyEntity> studyList(Pageable pageable);
	public Page<StudyEntity> studySearchList(String keyword, Pageable pageable);
	
	// 어드민 스터디 서비스
	public List<StudyEntity> getAllStudy();
	public int getStudyReserve();
	public int getStudyProg(LocalDateTime time);
	public int getStudyDone();
	public List<StudyByMonthDto> getStudyByMonth();
	public List<StudyEntity> getAllStudyToIndex();

}
