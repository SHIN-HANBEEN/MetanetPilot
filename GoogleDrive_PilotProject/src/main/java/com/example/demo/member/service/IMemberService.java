package com.example.demo.member.service;

import com.example.demo.member.dto.MemberForUpdate;
import com.example.demo.member.model.Member;

public interface IMemberService {
	int regMember(Member member);
	int updateMember(MemberForUpdate member);
	int deleteMember(int memberId, String password);
	
	Member showMemberInfo(int memberId);
	
}
