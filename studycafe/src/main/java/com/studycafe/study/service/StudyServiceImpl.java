package com.studycafe.study.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.studycafe.study.dto.StudyByMonthDto;
import com.studycafe.study.entity.StudyEntity;
import com.studycafe.study.repository.StudyRepository;

@Service("studyService")
public class StudyServiceImpl implements StudyService {

	@Autowired
	private StudyRepository studyRepository;

	@Override
	public void studyInsert(StudyEntity studyEntity) {
		// TODO Auto-generated method stub

		studyRepository.save(studyEntity);
	}

	@Override
	public Page<StudyEntity> studyList(Pageable pageable) {
		// TODO Auto-generated method stub
		return studyRepository.findByisDeleted(pageable, 0);
	}

	@Override 
	@Transactional
	public Page<StudyEntity> studySearchList(String keyword, Pageable pageable) {
		// TODO Auto-generated method stub
		Page<StudyEntity> list = studyRepository.findBystudyTitleContainingAndIsDeleted(pageable, keyword, 0);
		return list;
	}

	@Override
	public StudyEntity studySelect(int id) {
		// TODO Auto-generated method stub

		return studyRepository.getById(id);
	}



	@Override
	public int getStudyReserve() {
		// TODO Auto-generated method stub
		return studyRepository.findStudyReserve();
	}

	@Override
	public int getStudyProg(LocalDateTime time) {
		// TODO Auto-generated method stub
		return studyRepository.findStudyProg(time);
	}

	@Override
	public int getStudyDone() {
		// TODO Auto-generated method stub
		return studyRepository.findStudyDone();
	}

	@Override
	public List<StudyByMonthDto> getStudyByMonth() {
		// TODO Auto-generated method stub
		return studyRepository.findStudyByMonth();
	}

	@Override
	public List<StudyEntity> getAllStudy() {
		// TODO Auto-generated method stub
		return studyRepository.findAll();
	}
	
	@Override
	public List<StudyEntity> getAllStudyToIndex() {
		// TODO Auto-generated method stub
		return studyRepository.findAll(Sort.by(Sort.Direction.DESC, "studyNum"));
	}

	@Override
	public List<StudyEntity> studySelectByMap(double lat, double lon, LocalDate date) {
		// TODO Auto-generated method stub
		return studyRepository.findByLatitudeAndLongitudeAndReserveDate(lat, lon, date);
	}




}
