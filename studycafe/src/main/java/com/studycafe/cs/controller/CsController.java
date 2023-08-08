package com.studycafe.cs.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.studycafe.cs.dto.CsBoardDTO;
import com.studycafe.cs.dto.CsBoardPageDTO;
import com.studycafe.cs.entity.CsEntity;
import com.studycafe.cs.repository.CsRepository;
import com.studycafe.cs.service.CsService;
import com.studycafe.member.auth.PrincipalDetails;
import com.studycafe.utils.handler.ValidationHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CsController {
	@Autowired
	private CsService csService;

	@Autowired
	private CsRepository csRepository;

	@Autowired
	private ValidationHandler validationHandler;

	private final String identifier = "cs";

	// 게시판 접속 및 게시글 리스트 불러오기 및 페이징
	@GetMapping("/cs/csboard")
	public String csList(
			@PageableDefault(page = 0, size = 3, sort = "idx", direction = Sort.Direction.DESC) Pageable pageable,
			CsBoardPageDTO csBoardPage,
			Model model) {

		log.info("고객센터 접속");

		// 게시글 리스트
		Page<CsBoardDTO> csBoardDTOList = csService.getCsBoardList(pageable);

		// 페이징 로직
		CsBoardPageDTO page = csBoardPage.convertPageDTO(csBoardDTOList);

		page.paging();

		model.addAttribute("csBoardList", csBoardDTOList);
		model.addAttribute("page", page);

		return "/cs/csboard";
	}

	// 글등록 페이지
	@GetMapping("/cs/csregispage")
	public String csRegistPage() {
		log.info("고객센터 글 작성 페이지");

		return "/cs/csregis";
	}

	// 글 등록 로직
	@PostMapping("/cs/csregis")
	public String csRegist(
			@Valid CsBoardDTO csBoardDTO,
			BindingResult bindingResult,
			@RequestParam("file") MultipartFile file,
			Model model) {

		if (bindingResult.hasErrors()) {

			Map<String, String> validationResult = validationHandler.validateHandling(bindingResult);

			for (String errorKey : validationResult.keySet()) {

				model.addAttribute(errorKey, validationResult.get(errorKey));

			}

			return "/cs/csregis";
		}

		csService.csBoardRegis(csBoardDTO, identifier, file);

		return "redirect:/cs/csboard";
	}

	// 글 조회
	@GetMapping("/cs/csboard/{idx}")
	public String getCsBoardPost(@PathVariable("idx") int idx, Model model) {

		CsBoardDTO getPost = csService.getCsBoardPost(idx);

		log.info("게시글번호: {}", getPost.getIdx());
		log.info("파일키: {}", getPost.getFileKey());

		model.addAttribute("csPost", getPost);

		return "/cs/detailview";

	}

	// 글 수정 페이지 진입
	@GetMapping("/cs/modifyview/{idx}")
	public String modifyCsBoardPostPage(@PathVariable("idx") long idx, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {

		CsBoardDTO getPost = csService.getCsBoardPost(idx);

		CsEntity getWriter = csRepository.findById(idx).get();

		log.info("수정페이지 파일키 수정페이지 파일키 : {}", getWriter.getFileKey());

		if (principalDetails == null) {

			throw new AccessDeniedException("남의 게시글을 함부러 수정하려고 하시면 안돼요!!");

		} else {

			String csBoardWriter = getWriter.getCsWriter();

			String loginedUserNickName = principalDetails.getNickName();

			if (!csBoardWriter.equals(loginedUserNickName)) {

				throw new AccessDeniedException("남의 게시글을 함부러 수정하려고 하시면 안돼요!!");

			}
		}

		model.addAttribute("csPost", getPost);

		return "/cs/modifyview";

	}

	// 글 수정
	@PatchMapping("/cs/modifyview/{idx}")
	public String modifyCsBoard(CsBoardDTO csBoardDTO, @RequestParam("file") MultipartFile file) {

		log.info("수정시에 파일키 수정시에 파일키 : {}", csBoardDTO.getFileKey());

		csService.csBoardModify(csBoardDTO, identifier, file);

		return "redirect:/cs/csboard/{idx}";

	}

	// 글 삭제
	@ResponseBody
	@DeleteMapping("/cs/removepost")
	public boolean deleteCsBoard(@RequestParam("idx") long idx) {

		try {
			boolean remove = csService.deleteCsBoard(idx);

			return remove;

		} catch (Exception e) {
			throw new IllegalArgumentException("존재하지 않는 게시물입니다.");

		}
	}
}
