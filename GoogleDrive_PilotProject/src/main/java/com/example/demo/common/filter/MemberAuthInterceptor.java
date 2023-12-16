package com.example.demo.common.filter;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.example.demo.member.model.Member;
import com.example.demo.member.service.IMemberService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MemberAuthInterceptor implements HandlerInterceptor {
	@Autowired
	IMemberService memberService;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("멤버 Auth 인터셉터 실행되었습니다.");
		
		ContentCachingRequestWrapper wrapper = new ContentCachingRequestWrapper(request); //reqeust 의 getReader() 메서드는 stream 의 최종 연산자로, stream 을 소모시킨다. 그래서 ContentCachingReqeustWrrapper 로 소모되지 않게 감싸준다.
		Principal principal = wrapper.getUserPrincipal(); // principal 객체 꺼내기
		System.out.println("wrapper로 principal 만들어서 getName 한 결과 : " + principal.getName());
		System.out.println(wrapper.getParameter("memberId"));

		 // Read the JSON payload from the request
		 String jsonPayload = wrapper.getContentAsString();
		 System.out.println("Interceptor - JSON payload: " + jsonPayload);

		 ObjectMapper objectMapper = new ObjectMapper();
		 
		 String inputMemberId = "";
		 
		try {
		    JsonNode rootNode = objectMapper.readTree(jsonPayload);
		    inputMemberId = rootNode.path("memberId").asText();
		    System.out.println("Interceptor - Parsed memberId: " + inputMemberId);
		} catch (Exception e) {
		    // Handle the parsing exception
		    e.printStackTrace();
		    System.out.println("Interceptor - Error parsing JSON payload");
		}
		 

        // For simplicity, assuming the JSON structure is simple and does not require a custom class
//        String memberId = jsonPayload.contains("\"memberId\":\"") ? jsonPayload.split("\"memberId\":\"")[1].split("\"")[0] : null;


		if (principal != null) {
			String principalMemberId = principal.getName();
			if (!memberService.isMemberIdAuthenticForMember(principalMemberId, inputMemberId)) {
				System.out.println("멤버 Auth 인증 실패 : memberService.isMemberIdAuthenticForMember");
				throw new AuthException();
			}
			System.out.println("멤버 Auth 인증 성공");
			return true;
		} else {
			System.out.println("멤버 Auth 인증 실패 : principal null");
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
