package com.studycafe.member.oauth2.provider;

import java.util.Map;

public class GithubUserInfo implements Oauth2UserInfo{
	
	private Map<String,Object> attributes; // oauth2User.getAttributes()
	
	public GithubUserInfo(Map<String,Object> attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String getOAuth2Id() {
		
		return String.valueOf(attributes.get("id"));
	}

	@Override
	public String getOAuth2Path() {
		// TODO Auto-generated method stub
		return "github";
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return (String)attributes.get("login") + "@github.com";
	}

	@Override
	public String getName() {
		
		return (String)attributes.get("name");
	}

	
}
