package com.studycafe.member.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.studycafe.member.entity.MemberEntity;

import lombok.Getter;

// 로그인이 완료되면 시큐리티 session 만들어줌. (Security ContextHolder)
// 오브젝트 => Authentication 타입의 객체만 들어갈 수 있음.
// Authentication 안에 user정보가 있어야함.
// User오브젝트 타입 -> UserDetails 타입 객체 (고정)

// 시큐리티 session에는 Authentication 객체 고정으로 들어감 => UserDetails타입으로 꺼내서 씀

// OAuth2User 와 UserDetails는 따로 놀기떄문에 하나로 묶어주어야만 한다. (매우중요)
// => OAuth2User를 MemberEntity로 묶어버릴예정.
@Getter
public class PrincipalDetails implements UserDetails, OAuth2User {

	/**
	 * @serial PrincipalDetails
	 */
	private static final long serialVersionUID = -6487352197708348338L;

	private MemberEntity memberEntity; // 컴포지션
	private Map<String,Object> attributes;

	// NORMAL 회원가입, 로그인
	public PrincipalDetails(MemberEntity memberEntity) {
		this.memberEntity = memberEntity;
	}
	
	// OAuth2 회원가입, 로그인
	public PrincipalDetails(MemberEntity memberEntity, Map<String,Object> attributes) {
		this.memberEntity = memberEntity;
		this.attributes = attributes;
	}

	// user 권한 리턴.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> userRoles = new ArrayList<>();
		userRoles.add(new GrantedAuthority() {
			/**
			 * @serial userRoles.add(new GrantedAuthority() { ... }
			 */
			private static final long serialVersionUID = 1956719127234299808L;

			@Override
			public String getAuthority() {
				return memberEntity.getRole().toString();
			}
		});

		return userRoles;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return memberEntity.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return memberEntity.getUsername();
	}

	// 계정 만료
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	// 계정 막힘
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	// 계정 비밀번호 기간만료
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	// 계정 비활성화여부
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		// 나중에 logindate 컬럼같은거 추가해서 만료설정 가능 취직하고 개인프로젝트로 해봅시다 우리
		return true;
	}

	// OAuth2User 메소드
	@Override
	public Map<String, Object> getAttributes() {
		// 이곳의 소셜로그인 한 회원의 정보가 들어옴. PK, email 등등
		
		return attributes;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return (String)attributes.get("sub");
	}

	// 커스텀필드
	// 닉네임 체크
	public String getNickName() {
		return memberEntity.getNickName();
	}

}
