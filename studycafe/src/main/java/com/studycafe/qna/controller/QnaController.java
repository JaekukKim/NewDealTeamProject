package com.studycafe.qna.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.studycafe.member.auth.PrincipalDetails;
import com.studycafe.member.entity.MemberEntity;
import com.studycafe.qna.entity.QnaEntity;
import com.studycafe.qna.service.QnaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class QnaController {
	
	@Autowired
	private QnaService qnaService;
	
	@GetMapping("/qna")
	public String qnaList() {
		return "/qna/qnaList";
	}
	
	//qna게시판
	@GetMapping("/qnalist")
	@ResponseBody
	public Page<QnaEntity> qnaListAjax(@PageableDefault(page = 0, size = 10, sort = "qnaNum", direction = Sort.Direction.DESC) Pageable pageable,
			@RequestParam(required = false) String keyword) {
		
		Page<QnaEntity> list = null;

		if (keyword == null) {
			list = qnaService.qnaList(pageable);
			System.out.println("누가실행됩니까.............."+list);
			
		} else {
			list = qnaService.qnaSearchList(keyword, pageable);

		}
	
		int nowPage = list.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage -4, 1);
		int endPage = Math.min(nowPage + 5,  list.getTotalPages());

		return list;
	}
	
	//qna등록폼
	@GetMapping("/qnaRegister")
	public String qnaRegister() {
		return "/qna/qnaRegister";
	}
	
	@PostMapping("/qnaRegisterPro")
	public String qnaInsert(QnaEntity qnaEntity) {

		qnaService.qnaRegister(qnaEntity); // 게시글 저장
			return "redirect:/qna";
	}
	
	@GetMapping("/qnaDetail/{qnaNum}")
	public String qnaDetail(@PathVariable("qnaNum") long qnaNum, Model model) {
		
		QnaEntity qnaEntity  = new QnaEntity();
		
		qnaEntity = qnaService.selectQna(qnaNum);
		
		model.addAttribute("qnaEntity", qnaEntity);
		
		return "/qna/qnaDetail";
	}
	
	@GetMapping("/qnaModify/{qnaNum}")
	public String qnaModifyForm(@PathVariable("qnaNum") long qnaNum, Model model) {
		
		/**
		 * 게시글 수정 아이디 체크 넣기
		 * */
		
		try {
			QnaEntity qnaEntity = new QnaEntity();
			
			qnaEntity = qnaService.selectQna(qnaNum);
	
			model.addAttribute("qnaEntity", qnaEntity);
			
			return "/qna/qnaModify";
		} catch(Exception e) {
			return "/qna";
		}
	}
	
	@PostMapping("/qnaModifyPro")
	public String qnaModify(QnaEntity qnaEntity) {
		try {
			long num = qnaEntity.getQnaNum();
			
			qnaService.qnaRegister(qnaEntity); // 게시글 저장
			return "redirect:/qnaDetail/" + num;
		} catch(Exception e) {
			return "/qna";
		}
	}
	
	@PostMapping("/qnaDelete")
	@ResponseBody
	public Map<String, Object> qnaDeleteAjax(@RequestBody Map<String, Object> map) {

		Map<String, Object> result = new HashMap<String, Object>();

		QnaEntity qnaEntity = new QnaEntity(); // 객체 생성

		Long qnaNum = Long.parseLong((String) map.get("id")); // 게시글 번호
		
		log.info("너 몇번??:" + qnaNum);

		qnaEntity = qnaService.selectQna(qnaNum); // 게시글 조회
		
		qnaEntity.setIsDeleted(1); // 게시글 삭제

		qnaService.qnaRegister(qnaEntity); // 게시글 수정
		
	

		try {
			result.put("status", "ok");
		}catch(Exception e) {
			e.printStackTrace();
			result.put("status", "fail");
		}
		return result;
	}
	
	//삭제시도 1
	

}
