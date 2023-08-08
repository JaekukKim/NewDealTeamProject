package com.studycafe.cs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.studycafe.cs.entity.CsReplyEntity;

@Repository
public interface CsReplyRepository extends JpaRepository<CsReplyEntity, Long>{
	@Query("SELECT COUNT(DISTINCT csReplyRef) FROM CsReplyEntity WHERE idx = :id AND csReplyDepth = 0")
	public int replycount(long id);
	
	@Query("SELECT sr FROM CsReplyEntity sr WHERE idx = :idx ORDER BY csReplyRef ASC, Idx ASC")
	public List<CsReplyEntity> findByCsEntity(long idx);
}
