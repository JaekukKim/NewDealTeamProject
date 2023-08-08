package com.studycafe.team.entity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.studycafe.team.teamboard.dto.TeamBoardDTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class TeamEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long teamNumber;
	
	@NotNull
	@Column(unique=true)
	@Size(min=3)
	private String teamName;
	
	@NotNull
	private String teamHead;
	
	@NotNull
	@Min(value = 2,message = "팀 인원은 최소 2명 이상만 가능합니다.")
	private int memberCount;
	
	@ColumnDefault("0")
	private int point; 
	
	@ColumnDefault("0")
	private int approveCount; 
	
	@CreationTimestamp
	private Timestamp createDate;
	
	private LocalDate approveDate; // 팀 승인 날짜
	
	@Builder
	public TeamEntity( String teamName, String teamHead, int memberCount) {
		this.teamName = teamName;
		this.teamHead = teamHead;
		this.memberCount = memberCount;
	}
	
}
