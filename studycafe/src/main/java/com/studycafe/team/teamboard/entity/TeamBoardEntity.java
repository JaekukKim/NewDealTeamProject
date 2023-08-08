package com.studycafe.team.teamboard.entity;

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
public class TeamBoardEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long teamBoardNum;

	@NotNull
	@Column(length = 50)
	private String teamBoardTitle;

	@Lob
	@NotNull
	private String teamBoardContent;

	@NotNull
	@Column(length = 20, updatable = false)
	private String teamBoardWriter;
	
	@NotNull
	private String teamName;
	
	@NotNull
	private String teamMember;

	@CreationTimestamp
	@Column(updatable = false)
	private Timestamp createDate;

	@LastModifiedDate
	private LocalDateTime modifiedDate;

	@Builder
	public TeamBoardEntity(long teamBoardNum, @NotNull String teamBoardTitle, @NotNull String teamBoardContent, @NotNull String teamBoardWriter, @NotNull String teamName,
			@NotNull String teamMember, Timestamp createDate, LocalDateTime modifiedDate) {
		super();
		this.teamBoardNum = teamBoardNum;
		this.teamBoardTitle = teamBoardTitle;
		this.teamBoardContent = teamBoardContent;
		this.teamBoardWriter = teamBoardWriter;
		this.teamName = teamName;
		this.teamMember = teamMember;
		this.createDate = createDate;
		this.modifiedDate = modifiedDate;
	}

}
