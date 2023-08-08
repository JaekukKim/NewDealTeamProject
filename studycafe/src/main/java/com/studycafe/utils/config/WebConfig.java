package com.studycafe.utils.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
public class WebConfig {

	@Bean
	MappingJackson2JsonView jsonView() {
		// ckeditor 이미지 미리보기 가능하게
		return new MappingJackson2JsonView();
	}

}
