package com.studycafe.study.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.studycafe.study.dto.StudyFormDto;
import com.studycafe.study.entity.StudyEntity;
import com.studycafe.study.service.StudyService;

@Controller
public class StudyController {

	/**
	 * @author 홍정수
	 */

	@Autowired
	private StudyService studyService;

	// 스터디 모집 게시판 폼
	@GetMapping("/study")
	public String studyList() {
		return "/study/studylist";
	}

	// 스터디 모집 게시판 리스트 호출
	@GetMapping("/studylist")
	@ResponseBody
	public Page<StudyEntity> studyListAjax(
			@PageableDefault(page = 0, size = 10, sort = "studyNum", direction = Sort.Direction.DESC) Pageable pageable,
			@RequestParam(required = false) String keyword) {

		Page<StudyEntity> list = null;

		if (keyword == null) {
			list = studyService.studyList(pageable);
		} else {
			list = studyService.studySearchList(keyword, pageable);
		}

		int nowPage = list.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, list.getTotalPages());

		return list;
	}

	// 스터디 모집 게시물 등록 폼
	@GetMapping("/studyregistration")
	public String studyRegis() {
		return "/study/studyregistration";
	}

	// 스터디 모집 게시물 등록
	@PostMapping("/studyregistrationpro")
	public String studyInsert(StudyFormDto studyFormDto) {
		StudyEntity studyEntity = StudyFormDto.toStudyEntity(studyFormDto);

		studyService.studyInsert(studyEntity); // 게시글 저장
		return "redirect:/study";
	}

	// 스터디 모집 게시물 수정 폼
	@GetMapping("/studymodify/{no}")
	public String studyModifyForm(@PathVariable("no") int id, Model model) {

		/**
		 * 게시글 수정 아이디 체크 넣기
		 */

		try {
			StudyEntity studyEntity = new StudyEntity();

			studyEntity = studyService.studySelect(id);

			model.addAttribute("studyEntity", studyEntity);

			return "/study/studymodify";
		} catch (Exception e) {
			return "/study";
		}
	}

	// 스터디 모집 게시물 수정
	@PostMapping("/studymodifypro")
	public String studyModify(StudyFormDto studyFormDto) {
		try {
			int num = studyFormDto.getStudyNum();
			StudyEntity study = StudyFormDto.toStudyEntity(studyFormDto);
			studyService.studyInsert(study); // 게시글 저장
			return "redirect:/studydetail/" + num;
		} catch (Exception e) {
			return "/study";
		}
	}

	// 스터디 모집 게시글 삭제 Ajax
	@PostMapping("/studydelete")
	@ResponseBody
	public Map<String, Object> studyDeleteAjax(@RequestBody Map<String, Object> map) {

		Map<String, Object> result = new HashMap<String, Object>();
		
		StudyEntity studyEntity = new StudyEntity(); // 객체 생성

		int id = Integer.parseInt((String)map.get("id")); // 게시글 번호

		studyEntity = studyService.studySelect(id); // 게시글 조회
		
		studyEntity.setIsDeleted(1); // 게시글 삭제

		studyService.studyInsert(studyEntity); // 게시글 수정
		
		try {
			
			result.put("status", "ok");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "fail");
		}
		return result;
	}

	// 스터디 모집 게시물 열람
	@GetMapping("/studydetail/{no}")
	public String studySelect(@PathVariable("no") int id, Model model) {

		StudyEntity studyEntity = new StudyEntity();

		studyEntity = studyService.studySelect(id);

		model.addAttribute("studyEntity", studyEntity);

		return "/study/studydetail";
	}

	@GetMapping("/studyTime")
	@ResponseBody
	public String studySelectTimeByMap(@RequestParam("lat") String lat, @RequestParam("long") String lon,
			@RequestParam("date") String date) {
		LocalDate localDate = LocalDate.parse(date);	
		List<StudyEntity> studyEntity = studyService.studySelectByMap(Double.parseDouble(lat), Double.parseDouble(lon), localDate);
		JSONArray times = new JSONArray();
		for(StudyEntity study: studyEntity) {
			JSONObject time = new JSONObject();
			time.put("time", study.getReserveTime().toString());
			times.add(time);
		}
		
		
		return times.toJSONString();
	}
}
