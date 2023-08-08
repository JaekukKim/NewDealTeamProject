package com.studycafe.study.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studycafe.study.dto.StudyByMonthDto;
import com.studycafe.study.entity.StudyEntity;

@Repository
public interface StudyRepository extends JpaRepository<StudyEntity, Integer> {


	Page<StudyEntity> findBystudyTitleContainingAndIsDeleted(Pageable pageable, String keyword, int isDeleted); 
	Page<StudyEntity> findByisDeleted(Pageable pageable, int isDeleted);

	List<StudyEntity> findByLatitudeAndLongitudeAndReserveDate(double latitude, double longitude, LocalDate reserveDate);

	@Query(value = "SELECT COUNT(*) FROM study_entity WHERE reserve_date >= NOW() + INTERVAL 1 DAY", nativeQuery = true)
	int findStudyReserve();

	@Query(value = "SELECT COUNT(*) FROM study_entity WHERE CAST(reserve_date AS DATE) = CAST(NOW() AS DATE) and reserve_time=:time", nativeQuery = true)
	int findStudyProg(@Param("time") LocalDateTime reserveTime);

	@Query(value = "SELECT COUNT(*) FROM study_entity WHERE reserve_date <= NOW() - INTERVAL 1 DAY AND reserve_date >= NOW() - INTERVAL 5 DAY ", nativeQuery = true)
	int findStudyDone();

	@Query(value = "SELECT MONTHNAME(reserve_date) as month, COUNT(*) as count FROM study_entity WHERE reserve_date <= NOW() AND reserve_time <= NOW() - INTERVAL 4 HOUR GROUP BY MONTH(reserve_date)", nativeQuery = true)
	List<StudyByMonthDto> findStudyByMonth();

}
