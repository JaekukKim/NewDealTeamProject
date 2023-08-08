package com.studycafe.member.entity;

import lombok.Getter;

@Getter
public enum Join {
	
	NORMAL("사이트가입"),
	GIT_HUB("깃허브"),
	KAKAO("카카오");
	
	private String joinDescription;
	
	// ROLE_ 출력을 위해
	Join(String joinDescription) {
		this.joinDescription = joinDescription;
	}
}
