package com.studycafe.study.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class StudyEntity {

	@Id
	// @NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int studyNum; // 스터디 게시글 번호

	// @NotNull
	private String studyContent; // 게시글 내용

	// @NotNull
	@Column(columnDefinition = "TEXT", length = 100)
	private String studyWriter; // 게시글 작성자

	// @NotNull
	@Column(length = 50)
	private String studyTitle; // 게시글 제목

	@Column(length = 500)
	private String studyFilePath; // 파일 첨부 경로

	// @NotNull
	private double latitude; // 카카오 맵 위도

	// @NotNull
	private double longitude; // 카카오 맵 경도

	// @NotNull
	private LocalDate reserveDate; // 예약 날짜

	// @NotNull
	private LocalDateTime reserveTime; // 예약 시간

	@CreatedDate
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime dateTime; // 등록일자
	
	private int isDeleted; // 댓글 삭제 여부

	@Builder
	public StudyEntity(int studyNum, String studyContent, String studyWriter, String studyTitle, String studyFilePath, double latitude,
			double longitude, LocalDate reserveDate, LocalDateTime reserveTime, LocalDateTime dateTime, int isDeleted) {

		this.studyNum = studyNum;
		this.studyContent = studyContent;
		this.studyWriter = studyWriter;
		this.studyTitle = studyTitle;
		this.studyFilePath = studyFilePath;
		this.latitude = latitude;
		this.longitude = longitude;
		this.reserveDate = reserveDate;
		this.reserveTime = reserveTime;
		this.dateTime = dateTime;
		this.isDeleted = isDeleted;
	}

}
