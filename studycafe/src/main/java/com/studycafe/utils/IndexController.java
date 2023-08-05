package com.studycafe.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.studycafe.member.auth.PrincipalDetails;
import com.studycafe.study.service.StudyService;
import com.studycafe.team.teamboard.service.TeamBoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexController {
	
	@Autowired
	private StudyService studyService;
	
	@Autowired
	private TeamBoardService teamBoardService;
	
	@GetMapping("/")
	public String 접속컨트롤러(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model, Pageable pageable, HttpServletRequest request) {
		
		log.info("메인페이지 접속");
		
		if (principalDetails != null) {
			log.info("회원 접속");
			
			log.info(principalDetails.getUsername());
			log.info(principalDetails.getPassword()); // 시큐리티로 인해 null출력
			log.info("현재 유저의 권한 : {}" , principalDetails.getAuthorities());
			log.info("principalDetails.getMember() : {}" , principalDetails.getMemberEntity());
			
		} else {
			log.info("비회원 접속");
			
		}
		
		model.addAttribute("studyList", studyService.getAllStudyToIndex());
		model.addAttribute("teamBoardList", teamBoardService.getTeamBoardListToIndex());
		
		return "index";
	}
}

