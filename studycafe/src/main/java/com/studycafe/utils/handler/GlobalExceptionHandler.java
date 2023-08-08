package com.studycafe.utils.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = IllegalArgumentException.class)
	public String handleArgumentException(IllegalArgumentException ilEx, Model model) {

		model.addAttribute("error", ilEx.getMessage());

		return "/error/500";
	}

	@ExceptionHandler(value = AccessDeniedException.class)
	public String handleAccessDeniedException(AccessDeniedException ilEx, Model model) {

		model.addAttribute("error", ilEx.getMessage());

		return "/error/accessdenied";
	}

	@ExceptionHandler(value = NullPointerException.class)
	public String handleNullPointerException(NullPointerException ilEx, Model model) {

		model.addAttribute("error", "알 수 없는 원인이에요!");

		return "/error/500";
	}

	@ExceptionHandler(value = RuntimeException.class)
	public String handleRuntimeException(RuntimeException ilEx, Model model) {

		model.addAttribute("error", ilEx.getMessage());

		return "/error/500";
	}
}
