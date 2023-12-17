package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.common.filter.DriveAuthInterceptor;
import com.example.demo.common.filter.MemberAuthInterceptor;



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
				.addPathPatterns("/member/update") //이것도 엄밀히 말하면, 사용할 필요가 없긴 했음,, 
				.order(Ordered.HIGHEST_PRECEDENCE); //interceptor 가 동작한 다음에, true 가 반환되면, 원래 컨트롤러로 이어서 동작하게 하기.
	}
	
	
}
