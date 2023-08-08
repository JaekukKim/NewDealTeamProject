package com.studycafe.member.oauth2.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.studycafe.member.auth.PrincipalDetails;
import com.studycafe.member.entity.MemberEntity;
import com.studycafe.member.entity.Role;
import com.studycafe.member.oauth2.provider.GithubUserInfo;
import com.studycafe.member.oauth2.provider.GoogleUserInfo;
import com.studycafe.member.oauth2.provider.KakaoUserInfo;
import com.studycafe.member.oauth2.provider.Oauth2UserInfo;
import com.studycafe.member.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Oauth2MemberAdaptor extends DefaultOAuth2UserService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// 구글은 액세스토큰 + 사용자 프로필정보를 같이 받음.

	// 소셜로그인으로 받은 데이터 후처리 메소드.
	// 해당 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다. (중요)
	@Override
	@Transactional
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		// 들어온 정보
		log.info("userRequest getClientRegistration : {}", userRequest.getClientRegistration());
		log.info("userRequest getAccessToken().getTokenValue() : {}", userRequest.getAccessToken().getTokenValue());

		// 로그인 버튼 클릭 -> 로그인창 -> 로그인완료 -> code리턴(Oauth2-Client 라이브러리) -> Accesstoken요청
		// userRequest에 회원 프로필 정보가 담김 -> 이때 loadUser메소드로 추출가능.
		OAuth2User oauth2User = super.loadUser(userRequest);
		log.info("소셜 로그인 정보 : {}", oauth2User.getAttributes());

		// 회원가입
		String oauth2Path = userRequest.getClientRegistration().getRegistrationId(); // 들어온 경로(중요)

		Oauth2UserInfo oauth2UserInfo = null;

		if (oauth2Path.equals("google")) {
			log.info("구글 로그인 요청");

			oauth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());

		} else if (oauth2Path.equals("github")) {
			log.info("깃허브 로그인 요청");

			oauth2UserInfo = new GithubUserInfo(oauth2User.getAttributes());

		} else if (oauth2Path.equals("kakao")) {
			log.info("카카오 로그인 요청");

			oauth2UserInfo = new KakaoUserInfo(oauth2User.getAttributes());
		} else {
			log.info("지원하지 않는 소셜 로그인");
			
			throw new IllegalArgumentException("지원하지 않는 소셜로그인입니다.");
		}

		String oauth2Id = oauth2UserInfo.getOAuth2Id();
		String name = oauth2UserInfo.getName();
		String username = oauth2Path + "_" + oauth2Id; // 실제 db에 저장될 username
		String nickName = name + "_" + oauth2Id;
		String password = bCryptPasswordEncoder.encode(username + "DSSEAD");
		String email = oauth2UserInfo.getEmail();

		MemberEntity memberEntity = memberRepository.findByUsername(username);

		if (memberEntity == null) {

			log.info(oauth2Path, "{} 로그인");

			memberEntity = MemberEntity.builder()
					.username(username)
					.password(password)
					.role(Role.ROLE_MEMBER)
					.email(email)
					.nickName(nickName)
					.name(name)
					.oauth2Id(oauth2Id)
					.oauth2Path(oauth2Path)
					.build();

			memberRepository.save(memberEntity);

		} else {

			log.info("자동회원가입으로 인한 자동로그인");

		}

		return new PrincipalDetails(memberEntity, oauth2User.getAttributes());
	}

}
