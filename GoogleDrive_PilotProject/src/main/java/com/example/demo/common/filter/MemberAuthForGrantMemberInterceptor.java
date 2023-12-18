package com.example.demo.common.filter;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
public class MemberAuthForGrantMemberInterceptor implements HandlerInterceptor {
	@Autowired
	IMemberService memberService;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//===========================================================================================
		try {
			ReadableRequestBodyWrapper wrapper = new ReadableRequestBodyWrapper((HttpServletRequest) request);
			wrapper.setAttribute("requestBody", wrapper.getRequestBody());
			System.out.println("requestBody 출력하기 : " + (String)request.getAttribute("requestBody"));
			
			String requestBodyString = (String)request.getAttribute("requestBody");
			
			//JacksonLibrary 
			ObjectMapper objectMapper = new ObjectMapper(); //Jackson ObjectMapper 생성
			Map<String, Object> requestBodyMap = 
					objectMapper.readValue(requestBodyString, new TypeReference<Map<String, Object>>() {});
			
			Principal principal = wrapper.getUserPrincipal();
			String principalMemberId = principal.getName();
			String inputMemberId = (String) requestBodyMap.get("inputMemberId");
			String dirId = (String) requestBodyMap.get("dirId");
			
			System.out.println("principalMemberId : " + principalMemberId);
			System.out.println("inputMemberId : " + inputMemberId);
			System.out.println("dirId : " + dirId);
			
			if (!memberService.isMemberIdAuthenticForGrantMember(dirId, principalMemberId)) {
				System.out.println("멤버 Auth 인증 실패 : memberService.isMemberIdAuthenticForMember");
				throw new AuthException();
			}
			
			//인증이 끝난 사용자에 대해서 session 에 멤버 정보를 다시 담아야 함. request 는 여기서 소모 됨. 
			HttpSession session =  wrapper.getSession();
			session.setAttribute("memberId", inputMemberId);
			session.setAttribute("dirId", dirId);
			
			System.out.println("멤버 Auth 인증 성공");
			return true;
	      
//	      chain.doFilter(wrapper, response);
	    } catch (Exception e) {
    		return false;
//	      chain.doFilter(request, response);
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

