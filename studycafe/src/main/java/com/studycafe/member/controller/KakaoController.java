package com.studycafe.member.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studycafe.member.service.KakaoAPI;
import com.studycafe.member.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class KakaoController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private KakaoAPI kakao;

	
	
	
	
	@RequestMapping(value="/kakaoLoginCallback1")
    public String indexkakao() {
        
        return "/member/kakaotest";
    }
    
//    @RequestMapping(value="/kakao")
//    public String loginkakao() {
//        
//        return "/member/kakaotest";
//    }
    @RequestMapping(value="/kakaoLoginCallback")
//	@PostMapping("/kakaoLoginCallback")
    public String loginkakao(@RequestParam("code") String code, HttpSession session) {
        String access_Token = kakao.getAccessToken(code);
        HashMap<String, Object> userInfo = kakao.getUserInfo(access_Token);
        System.out.println("login Controller : " + userInfo);
        
        //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("access_Token", access_Token);
            
            System.out.println("aasdasdsd"+userInfo.get("email"));
        }
        
       
        return "redirect:/";
    }
    
    @RequestMapping(value="/logout")
    public String logout(HttpSession session) {
        kakao.kakaoLogout((String)session.getAttribute("access_Token"));
        session.removeAttribute("access_Token");
        session.removeAttribute("userId");
        return "index";
    }


}
