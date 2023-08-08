package com.studycafe.cs.entity;

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
public class CsReplyEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long csReplyNum; 
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "idx")	
	private CsEntity csEntity; 

	@Column(length = 20)
	@NotNull
	private String csReplyWriter;
	
	@NotNull
	@Column(columnDefinition = "TEXT")
	private String csReplyContent;

	@Column(length = 10)
	private String isDeleted;	  
	
	private long csReplyRef; 
	
	@CreationTimestamp
	private Timestamp csReplyDate;  
	
	private int csReplyDepth; 
}
