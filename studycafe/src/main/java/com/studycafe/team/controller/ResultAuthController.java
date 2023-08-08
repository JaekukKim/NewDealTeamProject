package com.studycafe.team.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.studycafe.team.entity.ResultAuthEntity;
import com.studycafe.team.service.ResultAuthService;

@Controller
public class ResultAuthController {

	@Autowired
	private ResultAuthService resultAuthService;
	
	@GetMapping("/auth")
	public String resultAuthList() {
		
		return "/resultauth/resultauthlist";
	}
	
	// 인증 게시글 폼
	@GetMapping("/auth/registration")
	public String resultAuthRegistraion() {
		
		return "/resultauth/resultauthregistration";
	}
	
	// 인증 게시글 작성
	@PostMapping("/auth/registrationpro")
	public String resultAuthRegistraionPro(ResultAuthEntity resultAuthEntity) throws Exception {
		
		System.out.println("resultAuthEntity : " + resultAuthEntity);
		resultAuthService.resultAuthInsert(resultAuthEntity);
		
		return "/resultauth/resultauthlist";
	}
	
	// 인증 게시글 디테일
	@GetMapping("/auth/detail")
	public String resultAuthDetail() {
		
		return "/resultauth/resultauthdetail";
	}
}
