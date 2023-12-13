package com.example.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //Spring 6 부터는 이 Annotation 을 사용하면서, Adapter 상속 안받게 바뀌었다.
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable(); //나중에 취약 처리 한 다음에, 지울 거기 때문에 분리를 해줌.
		http.formLogin()
				.loginPage("/member/login")
				.usernameParameter("memberName") //userid 라는 이름으로 username 을 parameter 에 담아서 아래의 주소로 request를 보냄
				.defaultSuccessUrl("/")
			.and()
			.logout()
				.logoutUrl("/member/logout")
				.logoutSuccessUrl("/member/login")
				.invalidateHttpSession(true);
		http.authorizeHttpRequests()
			.requestMatchers("/file/**").hasRole("ADMIN")
			.requestMatchers("/board/**").hasAnyRole("USER", "ADMIN")
			.requestMatchers("/**", "/css/**", "/js/**", "/images/**").permitAll()
			.requestMatchers("/member/insert", "/member/login").permitAll();
			
				
		return http.build(); //보통은 이렇게 마지막에 build 해서 반환한 것을 사용한다.
	}
	
//	@Bean
//	@ConditionalOnMissingBean(UserDetailsService.class) //이 빈이 없으면 빈으로 만들어주세요!
//	public InMemoryUserDetailsManager userDetailsService() {
//		List<UserDetails> userDetailsList = new ArrayList<>();
//		userDetailsList.add(User.withUsername("foo")
//				.password("{noop}demo")
//				.roles("ADMIN").build());
//		userDetailsList.add(User.withUsername("bar")
//				.password("{noop}demo")
//				.roles("USER").build());
//		userDetailsList.add(User.withUsername("ted")
//				.password("{noop}demo")
//				.roles("ADMIN", "USER").build());
//		
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(userDetailsList); 
//		return manager;
//	}
	
	@Bean
	PasswordEncoder passwordEncoder() { 
		//패스워드 인코더를 빈으로 등록을 시켜줘야 스프링 시큐리티가 DB에서 암호화된 것과 비교를
		//잘 하여, 동작할 수 있게 됩니다. 
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
}

































