package com.studycafe.qna.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.studycafe.qna.entity.QnaEntity;
import com.studycafe.study.entity.StudyEntity;

/*@Repository*/
public interface QnaRepository  extends JpaRepository<QnaEntity, Long>  {
	
	Page<QnaEntity> findByqnaTitleContaining(String keyword, Pageable pageable);

	Page<QnaEntity> findByisDeleted(Pageable pageable, int isDeleted);
	
//	이게 되나?
	@Query("select sr from QnaEntity sr where is_deleted=:isDeletedValue")
	Page<QnaEntity> findByIsDeletedEquals(int isDeletedValue, Pageable pageable);
//	
//	@Query(value = "SELECT sr FROM QnaEntity sr WHERE is_deleted=:isDeletedValue AND qna_title LIKE %:keyword%", nativeQuery = true)
//	public Page<QnaEntity> qnaSerchList(Pageable pageable, String keyword, int isDeletedValue);
}