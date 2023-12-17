package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.common.filter.DriveAuthInterceptor;
import com.example.demo.common.filter.MemberAuthInterceptor;

import jakarta.servlet.http.HttpServletRequest;



@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Autowired
	DriveAuthInterceptor driveAuthInterceptor;
	
	@Autowired
	MemberAuthInterceptor memberAuthInterceptor;
	
	
	
//	@Bean
//	public MessageSource messageSource() {
//		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//		messageSource.setBasenames("i18n/message");
//		messageSource.setDefaultEncoding("UTF-8");
//		return messageSource;
//	}
//	
//	@Bean
//	public LocaleChangeInterceptor localeChangeInterceptor() {
//		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
//		lci.setParamName("lang"); //LocalChangeInterceptor 의 String paramname 을 set한다.
//		return lci;
//	}
//	
//	@Bean
//	public LocaleResolver localeResolver() {
//		SessionLocaleResolver slr = new SessionLocaleResolver();
//		slr.setDefaultLocale(Locale.KOREAN);
//		return slr;
//	}
	
//	@Bean
//	public LoginInterceptor loginInterceptor() {
//		return new LoginInterceptor();
//	}
//	
//
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(localeChangeInterceptor());
		registry.addInterceptor(driveAuthInterceptor)
				.addPathPatterns("/drive/**");
		registry.addInterceptor(memberAuthInterceptor)
				.addPathPatterns("/member/update");
	}
	
	
}
