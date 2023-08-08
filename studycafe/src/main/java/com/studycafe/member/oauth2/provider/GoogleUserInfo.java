package com.studycafe.member.oauth2.provider;

import java.util.Map;

public class GoogleUserInfo implements Oauth2UserInfo{
	
	private Map<String,Object> attributes; // oauth2User.getAttributes()
	
	public GoogleUserInfo(Map<String,Object> attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String getOAuth2Id() {
		
		return (String)attributes.get("sub");
	}

	@Override
	public String getOAuth2Path() {
		// TODO Auto-generated method stub
		return "google";
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return (String)attributes.get("email");
	}

	@Override
	public String getName() {
		
		return (String)attributes.get("name");
	}

	
}
