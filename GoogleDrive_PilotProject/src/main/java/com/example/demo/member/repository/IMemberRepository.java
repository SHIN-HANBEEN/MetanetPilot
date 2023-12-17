package com.example.demo.member.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.member.model.Member;

@Mapper
@Repository
public interface IMemberRepository {
	Member showMemberInfo(String memberid);
	List<String> getRoles(String memberid);
	String getMemberIdByDirId(String dirId);
	
	void regMember(Member member);
	void regMemberRole(String memberId); // regMember에서 role 설정 위해 추가
	void updateMember(@Param("memberId") String memberId, @Param("encodedPw") String encodedPw, @Param("email") String email);
	
	String getPasswordByMemberId(String memberId);
	
	void deleteMemberRole(String memberId);
	void deleteMember(String memberId);
	void deleteSharedTable(String memberId);
	
	
	
	
//	Member regMember(Member member);
//	Member updateMember(Member member);
//	int deleteMember(int memberId);
//	boolean setGrantMember(int memberId, String dirId);
}
