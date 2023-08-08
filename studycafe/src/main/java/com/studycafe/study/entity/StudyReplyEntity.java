package com.studycafe.study.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;


@Entity
@Data
public class StudyReplyEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long studyReplyNum; // 스터디 댓글 번호
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "studyNum")	
	private StudyEntity studyEntity; // 스터디 번호 외래키

	@Column(length = 100)
	@NotNull
	private String studyReplyWriter; // 스터디 댓글 작성자
	
	@NotNull
	@Column(columnDefinition = "TEXT")
	private String studyReplyContent; // 스터디 댓글 내용

	@Column(length = 10)
	private String isDeleted;	 // 스터디 댓글 삭제 여부
	
	private long studyReplyRef; // 스터디 부모 댓글 번호
	
	@CreationTimestamp
	private Timestamp studyReplyDate;  // 스터디 댓글 등록 일자
	
	private int studyReplyDepth;  // 스터디 댓글 깊이
}
