package com.example.demo.common.filter;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.member.service.IMemberService;

import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class DriveAuthInterceptor implements HandlerInterceptor {
	@Autowired
	IMemberService memberService;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("드라이브 Auth 인터셉터 실행되었습니다.");
		Principal principal = request.getUserPrincipal();

		if (principal != null) {
			if (!memberService.isMemberIdAuthenticForDrive(principal.getName(), request.getPathInfo())) {
				System.out.println("인증 실패 : DriveAuthInterceptor");
				throw new AuthException();
			}
			System.out.println("인증 성공 : DriveAuthInterceptor");
			return true;
		} else {
			System.out.println("인증 실패 : DriveAuthInterceptor");
			throw new AuthException();
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
