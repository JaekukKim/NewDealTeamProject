package com.studycafe.member.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonView;
import com.studycafe.member.entity.Role;
import com.studycafe.utils.Views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberSafeDto {
	@JsonView(Views.Public.class)
	private String username;
	private String name;
	@JsonView(Views.Public.class)
	private String nickName;
    private String email;
    private Role role;
    @JsonView(Views.Public.class)
    private Long teamNumber;
    private Timestamp createdAt; 
  
}
