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
public class MemberAuthInterceptor implements HandlerInterceptor {
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
			Map<String, Object> requestBodyMap = objectMapper.readValue(requestBodyString, new TypeReference<Map<String, Object>>() {});
			
			Principal principal = wrapper.getUserPrincipal();
			String principalMemberId = principal.getName();
			String inputMemberId = (String) requestBodyMap.get("memberId");
			String password = (String) requestBodyMap.get("password");
			String email = (String) requestBodyMap.get("email");
			
			System.out.println("principalMemberId : " + principalMemberId);
			System.out.println("inputMemberId : " + inputMemberId);
			if (!memberService.isMemberIdAuthenticForMember(principalMemberId, inputMemberId)) {
				System.out.println("멤버 Auth 인증 실패 : memberService.isMemberIdAuthenticForMember");
				throw new AuthException();
			}
			
			//인증이 끝난 사용자에 대해서 session 에 멤버 정보를 다시 담아야 함. request 는 여기서 소모 됨. 
			HttpSession session =  wrapper.getSession();
			session.setAttribute("memberId", principalMemberId);
			session.setAttribute("password", password);
			session.setAttribute("email", email);
			
			System.out.println("멤버 Auth 인증 성공");
			return true;
	      
//	      chain.doFilter(wrapper, response);
	    } catch (Exception e) {
    		return false;
//	      chain.doFilter(request, response);
	    }
		
		
		//===========================================================================================
//		//JacksonLibrary ContentCachingReqeustWrapper 사용한 부분 : 실패함 requestBody 안읽어와짐
//		ObjectMapper objectMapper = new ObjectMapper(); //Jackson ObjectMapper 생성
//		
//		
//		System.out.println("멤버 Auth 인터셉터 실행되었습니다.");
//		
//		ContentCachingRequestWrapper wrapper = new ContentCachingRequestWrapper(request); //reqeust 의 getReader() 메서드는 stream 의 최종 연산자로, stream 을 소모시킨다. 그래서 ContentCachingReqeustWrrapper 로 소모되지 않게 감싸준다.
//		Principal principal = wrapper.getUserPrincipal(); // principal 객체 꺼내기
//		System.out.println("wrapper로 principal 만들어서 getName 한 결과 : " + principal.getName());
//		System.out.println("wrapper.getParameger(\"memberId\")를 한 결과 : " + wrapper.getParameter("memberId"));
//		
//		
//		byte[] requestBody = wrapper.getContentAsByteArray(); //requestBody 를 ByteArray 로 가져오기
//		
//		// Check if the request body is not empty
//	    if (requestBody.length == 0) {
//	        System.out.println("request Body 로 넘어온 값이 없습니다.");
//	        return false; 
//	    }
//		
//		String requestBodyString = new String(requestBody, wrapper.getCharacterEncoding()); //reqeustBody 를 String 변환
//		Map<String, Object> requestBodyMap = objectMapper.readValue(requestBodyString, new TypeReference<Map<String, Object>>() {});
//		
//		String password = (String) requestBodyMap.get("password"); 
//		System.out.println("requestBodyMap 에서 꺼낸 'password' : " + password);
//		
//		HttpSession session = wrapper.getSession(); //session 생성
//		
//		 // Read the JSON payload from the request
//		 String jsonPayload = wrapper.getContentAsString();
//		 System.out.println("Interceptor - JSON payload : " + jsonPayload);
//
//		 
//		 
//		 String inputMemberId = "";
//		 
//		try {
//		    JsonNode rootNode = objectMapper.readTree(jsonPayload);
//		    inputMemberId = rootNode.path("memberId").asText();
//		    System.out.println("Interceptor - Parsed memberId: " + inputMemberId);
//		} catch (Exception e) {
//		    // Handle the parsing exception
//		    e.printStackTrace();
//		    System.out.println("Interceptor - Error parsing JSON payload");
//		}
		 

        // For simplicity, assuming the JSON structure is simple and does not require a custom class
//        String memberId = jsonPayload.contains("\"memberId\":\"") ? jsonPayload.split("\"memberId\":\"")[1].split("\"")[0] : null;

		
//		String principalMemberId = principal.getName();
//		if (!memberService.isMemberIdAuthenticForMember(principalMemberId, inputMemberId)) {
//			System.out.println("멤버 Auth 인증 실패 : memberService.isMemberIdAuthenticForMember");
//			throw new AuthException();
//		}
//		System.out.println("멤버 Auth 인증 성공");
//		return true;
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
