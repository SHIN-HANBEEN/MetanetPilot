package com.example.demo.member.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.member.model.Member;

@Mapper
@Repository
public interface IMemberRepository {
	Member showMemberInfo(String memberid);
	List<String> getRoles(String memberid);
	String getMemberIdByDirIdInDirPath(String dirId);
	String getMemberIdByDirIdInSharedTable(String dirId);
	
	void regMember(Member member);
	void regMemberRole(String memberId); // regMember에서 role 설정 위해 추가
	void updateMember(@Param("memberId") String memberId, @Param("encodedPw") String encodedPw, @Param("email") String email);
	
	String getPasswordByMemberId(String memberId);
	
	void deleteMemberRole(String memberId);
	void deleteMember(String memberId);
	void deleteSharedTable(String memberId);
	
	List<Map<String, String>> getCascadeMemberIdByDirIdInDirPathAndSharedTable(String dirId); //DriveInterceptor 에서 폴더 안의 폴더 등에 대한 권한 인증 확인을 위한 리포지토리
	
	void setGrantMember(@Param("memberId") String memberId, @Param("dirId") String dirId);
	
	
	
//	Member regMember(Member member);
//	Member updateMember(Member member);
//	int deleteMember(int memberId);
//	boolean setGrantMember(int memberId, String dirId);
}
