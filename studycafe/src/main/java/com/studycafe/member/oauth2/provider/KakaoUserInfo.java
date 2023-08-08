package com.studycafe.member.oauth2.provider;

import java.util.Map;

public class KakaoUserInfo implements Oauth2UserInfo{
	
	private Map<String,Object> attributes; // oauth2User.getAttributes()
	
	public KakaoUserInfo(Map<String,Object> attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String getOAuth2Id() {
		
		return attributes.get("id").toString();
	}

	@Override
	public String getOAuth2Path() {
		// TODO Auto-generated method stub
		return "kakao";
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return (String)((Map)attributes.get("kakao_account")).get("email");
	}

	@Override
	public String getName() {
		
		return (String)((Map) attributes.get("properties")).get("nickname");
	}

	
}
