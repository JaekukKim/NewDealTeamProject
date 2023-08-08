package com.studycafe.member.oauth2.provider;

public interface Oauth2UserInfo {
	
	// 제공사의 PK
	String getOAuth2Id();
	// 제공사가 어디인지
	String getOAuth2Path();
	// 제공사에 저장된 email (github는 없어서 커스텀)
	String getEmail();
	// 제공사에 저장된 이름
	String getName();
}
