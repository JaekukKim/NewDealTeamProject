package com.studycafe.utils.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ValidationHandler {

	// 유효성 검증
	public Map<String, String> validateHandling(BindingResult bindingResult) {
		Map<String, String> validatorResult = new HashMap<>();

		for (FieldError error : bindingResult.getFieldErrors()) {

			String validKeyName = String.format("valid_%s", error.getField());

			validatorResult.put(validKeyName, error.getDefaultMessage());

			log.info(validKeyName);

		}

		return validatorResult;
	}
}
