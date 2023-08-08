package com.studycafe.member.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import com.studycafe.team.entity.TeamEntity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"username", "nickName"}))
public class MemberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idx;

	// 시큐리티 필드
	@NotNull
	@Column(length = 500)
	private String username;

	@NotNull
	@Column(length = 300)
	private String password;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Role role;

	// 나머지
	@Column(length = 50)
	private String email;

	@NotNull
	@Column(length = 300)
	private String nickName;

	@NotNull
	@Column(length = 20)
	private String name;

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private Timestamp createdAt;

	// oauth2
	@Column(length = 30)
	private String oauth2Path; // provider

	@Column(length = 100)
	private String oauth2Id; // attributes의 sub

	@ManyToOne
	@JoinColumn(name = "teamNumber")
	private TeamEntity teamNumber;

	@Builder
	public MemberEntity(@NotNull String username, @NotNull String password, @NotNull Role role, String email, @NotNull String nickName,
			@NotNull String name, Timestamp createdAt, String oauth2Path, String oauth2Id, TeamEntity teamNumber) {

		this.username = username;
		this.password = password;
		this.role = role;
		this.email = email;
		this.nickName = nickName;
		this.name = name;
		this.createdAt = createdAt;
		this.oauth2Path = oauth2Path;
		this.oauth2Id = oauth2Id;
		this.teamNumber = teamNumber;
	}

}
