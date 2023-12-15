package com.example.demo.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.example.demo.common.filter.DriveAuthInterceptor;



@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Autowired
	DriveAuthInterceptor driveAuthInterceptor;
	
	
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
		System.err.println(driveAuthInterceptor + "driveAuthInterceptor add complete");
		registry.addInterceptor(driveAuthInterceptor)
				.addPathPatterns("/drive/**");
	}
}
