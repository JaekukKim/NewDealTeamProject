package com.studycafe.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studycafe.member.auth.PrincipalDetails;
import com.studycafe.member.dto.JoinDto;
import com.studycafe.member.dto.MemberDto;
import com.studycafe.member.dto.MemberSafeDto;
import com.studycafe.member.entity.MemberAddressEntity;
import com.studycafe.member.entity.MemberEntity;
import com.studycafe.member.entity.Role;
import com.studycafe.member.service.MemberService;
import com.studycafe.utils.Views;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@GetMapping("/loginform")
	public String loginPage() {

		log.info("로그인 페이지 접속");

		return "/member/loginForm";
	}

	@PostMapping("/loginform")
	public String login() {

		log.info("로그인 로직 실행");

		return "redirect:/";
	}

	@GetMapping("/joinform")
	public String joinForm() {
		return "/member/joinForm";
	}

	@GetMapping("/logout")
	public String logout() {
		return "/";
	}

	@PostMapping("/joinPro")
	@ResponseBody
	public boolean joinPro(@RequestBody JoinDto joinVO, HttpServletRequest request) {

		MemberEntity memberEntity = joinVO.getMemberEntity();

		MemberAddressEntity memberAddressEntity = joinVO.getMemberAddressEntity();

		String rawPassword = memberEntity.getPassword();
		String encPassword = encoder.encode(rawPassword);

		memberEntity.setPassword(encPassword);
		memberEntity.setRole(Role.ROLE_MEMBER);
		boolean insert = memberService.insertMember(memberEntity, memberAddressEntity);

		log.info("가입한 회원의 정보 : {}", memberEntity);
		log.info("가입한 회원의 주소 : {}", memberAddressEntity);

		return insert;
	}

	// 아이디 중복체크
	@PostMapping("/idCheck")
	@ResponseBody
	public boolean idCheck(@RequestParam("username") String username) {

		boolean idcheck = memberService.idCheck(username);

		return idcheck;
	}

	// 닉네임 중복체크
	@PostMapping("/nickCheck")
	@ResponseBody
	public boolean nickCheck(@RequestParam("nickName") String nickName) {

		boolean nickCheck = memberService.nickCheck(nickName);

		return nickCheck;
	}

	// 이메일 중복체크
	@PostMapping("/emailCheck")
	@ResponseBody
	public boolean emailCheck(@RequestParam("email") String email) {

		boolean emailCheck = memberService.emailCheck(email);

		return emailCheck;
	}

	@GetMapping("/findAccount")
	public String findAccount() {
		return "/member/searchId";
	}

	// 아이디 찾기
	@PostMapping("/getUserId")
	@ResponseBody
	public MemberEntity getUsername(@RequestParam("email") String email) {
		try {
			MemberEntity memberEntity = memberService.getUsername(email);
			if (memberEntity != null) {
				log.info("memberEntity의 값은 :{}", memberEntity);
				return memberEntity;
			} else {
				log.info("memberEntity의 값은 :{}", memberEntity);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 아이디 찾기
	@GetMapping("/resultUsername")
	public String resultUsername() {

		return "/member/resultUsername";
	}

	// 비밀번호 찾기 1
	@GetMapping("/searchPwd1")
	public String searchPwd1() {
		return "/member/searchPwd1";
	}

	// 비밀번호 찾기1 (아이디 찾기)
	@PostMapping("/searchPwdFindUsername")
	@ResponseBody
	public MemberEntity searchPwdFindUsername(@RequestParam("username") String username) {
		MemberEntity memberEntity = memberService.findUsername(username);

		return memberEntity;
	}

	// 비밀번호 찾기 2
	@GetMapping("/searchPwd2")
	public String searchPwd2() {

		return "/member/searchPwd2";
	}

	// 비밀번호 찾기2 (아이디 찾기)
	@PostMapping("/searchPwdFindUsernameEmail")
	@ResponseBody
	public MemberEntity searchPwdFindUsernameEmail(@RequestParam("username") String username,
			@RequestParam("email") String email) {
		MemberEntity memberEntity = memberService.findUsernameByEmail(username, email);
		log.info("memberEntity 는 : {}", memberEntity);
		return memberEntity;
	}

	@GetMapping("/searchPwd3")
	public String searchPwd3() {
		return "/member/searchPwd3";
	}

	@PostMapping("/updatePassword")
	@ResponseBody
	public boolean updatePassword(@RequestParam("username") String username,
			@RequestParam("password") String password) {

		boolean result = memberService.updatePassword(username, password);

		return result;
	}

	// 회원정보 수정
	// 비밀번호 검증
	@GetMapping("/member/verificationpage")
	public String varificationPage() {

		return "/member/verificationpage";
	}
	@ResponseBody
	@PostMapping("/member/verification")
	public boolean varificationPassword(@AuthenticationPrincipal PrincipalDetails PrincipalDetails, @RequestParam("password") String inputPwd) {

		String dbPwd = PrincipalDetails.getPassword();

		if (encoder.matches(inputPwd, dbPwd)) {

			return true;
		} else {

			return false;
		}
	}

	// 검증 성공시 보내기
	@GetMapping("/member/modifyinfo")
	public String modifyUser(@AuthenticationPrincipal PrincipalDetails PrincipalDetails, Model model) {

		try {

			if (PrincipalDetails != null) {
				String username = PrincipalDetails.getUsername();

				MemberEntity memberInfo = PrincipalDetails.getMemberEntity();

				MemberAddressEntity address = memberService.getUserAddress(username);

				model.addAttribute("member", memberInfo);

				model.addAttribute("memberAddress", address);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new AccessDeniedException("잘못된 접근방식입니다.");
		}

		return "/member/modifymember";
	}

	@ResponseBody
	@PatchMapping("/member/updateinfo")
	public boolean updateInfo(@RequestBody MemberDto memberDto) {

		log.info("member : {}", memberDto);

		boolean result = memberService.updateInfo(memberDto);

		return result;
	}

	// 비밀번호 변경
	@GetMapping("/member/changepwd")
	public String pwdPage() {
		return "/member/verificationpage-changepwd";
	}

	// 비밀번호 변경 로직1
	@PostMapping("/checkPassword")
	@ResponseBody
	public boolean checkPassword(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam("oneraepassword") String oneraepassword) {

		boolean result = memberService.checkPassword(principalDetails.getUsername(), oneraepassword);

		return result;
	}

	// 비밀번호 변경 로직2
	@PostMapping("/changePassword")
	@ResponseBody
	public boolean changePwd(@AuthenticationPrincipal PrincipalDetails principalDetails,
			@RequestParam("password") String password) {

		boolean result = memberService.changePassword(principalDetails.getUsername(), password);

		return result;
	}
	
	@PostMapping("/member/searchUser")
	@ResponseBody
	public String searchUser( @RequestParam("username") String username) {
		List<MemberSafeDto> search =  memberService.searchMember(username);
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
	    try {
			String result = mapper
				      .writerWithView(Views.Public.class)
				      .writeValueAsString(search);
			return result;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}		
	}

}