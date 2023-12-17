package com.example.demo.member.service;

import java.util.List;

import com.example.demo.member.dto.MemberForUpdate;
import com.example.demo.member.model.Member;

public interface IMemberService {
	void regMember(Member member); //회원의 최상위 멤버 만들기 // - 공주님
	void updateMember(String memberId, String encodedPw, String email); //컨트롤러에서 비밀번호 평문이랑 암호화된거 비교 // - 공주님
	void deleteMember(String memberId); //컨트롤러에서 비밀번호 평문과 암호화 일치한지 비교 // - 공주님
	String getPasswordByMemberId(String memberId); // 패스워드 가져오기 - 주희
	
	Member showMemberInfo(String memberid); // - 석준
	List<String> getRoles(String memberid); //이거는 view 페이지 필요 없음. Security 인가처리할 때 쓰려고 만듦 // - 석준
//	String setGrantMember(int memberId, String dirId); //폴더 공유해주기 // - 석준
	
	boolean isMemberIdAuthenticForDrive(String memberId, String dirId);
	boolean isMemberIdAuthenticForMember(String principalMemberId, String inputMemberId);
}
