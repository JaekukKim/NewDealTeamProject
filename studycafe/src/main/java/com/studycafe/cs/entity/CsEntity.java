package com.studycafe.cs.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CsEntity implements Serializable{

	/**
	 * @serial CsEntity
	 */
	private static final long serialVersionUID = 5490166481439133586L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idx;

	@NotNull
	@Column(length = 50)
	private String csTitle;

	@Lob
	@NotNull
	private String csContent;

	@NotNull
	@Column(length = 20)
	private String csWriter;
	
	@NotNull
	@Column(length = 20, updatable = false)
	private String username;
	
	@Lob
	private String filePath;
	
	@Lob
	private String fileKey;
	
	@ColumnDefault("0")
	private boolean secret;

	@CreationTimestamp
	@Column(updatable = false)
	private Timestamp createDate;

	@LastModifiedDate
	private LocalDateTime modifiedDate;
	
	private int isDeleted; 
	
	@Builder
	public CsEntity(long idx, @NotNull String csTitle, @NotNull String csContent, @NotNull String csWriter, String filePath ,Timestamp createDate,
			LocalDateTime modifiedDate, String fileKey , boolean secret , String username) {
		
		this.idx = idx;
		this.csTitle = csTitle;
		this.csContent = csContent;
		this.csWriter = csWriter;
		this.filePath = filePath;
		this.secret = secret;
		this.username = username;
		this.fileKey = fileKey;
		this.createDate = createDate;
		this.modifiedDate = modifiedDate;
	}

}
