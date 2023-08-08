package com.studycafe.team.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
public class ResultAuthEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int resultAuthNum;
	
	@NotNull
	@Column(length = 50)
	private String resultAuthTitle;
	
	@NotNull
	@Lob
	private String resultAuthContent;
	
	@NotNull
	@Column(length = 20, updatable = false)
	private String resultAuthWriter;
	
	@CreationTimestamp
	@Column(updatable = false)
	private Timestamp createDate;
	
	@Column(length = 500)
	private String resultAuthFilePath;

	private int resultAuthScore;
	private int isDeleted;
}
