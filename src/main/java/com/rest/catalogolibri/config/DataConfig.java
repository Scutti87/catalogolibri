package com.rest.catalogolibri.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataConfig {

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

}
