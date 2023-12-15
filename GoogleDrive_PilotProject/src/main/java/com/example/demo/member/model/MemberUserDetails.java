package com.example.demo.member.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MemberUserDetails extends User {

	private static final long serialVersionUID = 1L;
	//serialVersionUID 는 나중에 직렬화가 가능합니다. 클래스 객체 자체를 파일에 저장하거나 네트워크를 보낼 수 있는 직렬화인데요.
	//다른 언어에서는 다른 언어에서는 못읽습니다. 자바까리는 서로 직렬화 한 다음에, 
	//원본 클래스가 바뀌면 못 읽기 때문에, serialVersionUID 라는 것을 만들게 됩니다. 
	
	private String email;
	
	public MemberUserDetails(String memberid, String password, Collection<? extends GrantedAuthority> authorities,
			String email) {
		super(memberid, password, authorities);
		this.email = email; //이런 식으로 userEmail 을 추가해줄 수 있다.
	}
	
	
	public String getUserEmail() {
		return this.email;
	}


}
