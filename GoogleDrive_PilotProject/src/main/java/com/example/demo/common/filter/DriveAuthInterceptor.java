package com.example.demo.common.filter;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.common.reader.ReadableRequestBodyWrapper;
import com.example.demo.member.service.IMemberService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class DriveAuthInterceptor implements HandlerInterceptor {
	@Autowired
	IMemberService memberService;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("드라이브 Auth 인터셉터 실행되었습니다.");
		
		ReadableRequestBodyWrapper wrapper = new ReadableRequestBodyWrapper((HttpServletRequest) request);
		wrapper.setAttribute("requestBody", wrapper.getRequestBody());
		System.out.println("requestBody 출력하기 : " + (String)request.getAttribute("requestBody"));
		
		String requestBodyString = (String)request.getAttribute("requestBody");
		
		//JacksonLibrary 
		ObjectMapper objectMapper = new ObjectMapper(); //Jackson ObjectMapper 생성
		Map<String, Object> requestBodyMap = 
				objectMapper.readValue(requestBodyString, new TypeReference<Map<String, Object>>() {});
		
		Principal principal = wrapper.getUserPrincipal();
		
		if (principal == null) {
			System.out.println("principal is null.");
			return false;
		}
		
		String principalMemberId = principal.getName();
		System.out.println("principalMemberId : " + principalMemberId);
		
//		String memberId = (String)requestBodyMap.get("memberId");
		
		String dirId = "";
		if (requestBodyMap.get("dirId") != null) {
			dirId = (String)requestBodyMap.get("dirId");
		}
		
		
		if (!memberService.isMemberIdAuthenticForDrive(principalMemberId, dirId)) {
			System.out.println("인증 실패 : DriveAuthInterceptor");
			throw new AuthException();
		}
		System.out.println("인증 성공 : DriveAuthInterceptor");
		//인증에 성공하면, session 에 정보 담고 컨트롤러로 넘기기.
		HttpSession session = wrapper.getSession();
		
		if (requestBodyMap.get("file") != null) {
			MultipartFile file = (MultipartFile)requestBodyMap.get("file");
			session.setAttribute("file", file);
		}
		
		session.setAttribute("dirId", dirId);
//		session.setAttribute("memberId", memberId);
		return true;
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
