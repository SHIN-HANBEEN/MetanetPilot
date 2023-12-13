package com.example.demo.member.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.member.model.Member;

@Mapper
@Repository
public interface IMemberRepository {
	Member regMember(Member member);
	Member updateMember(Member member);
	int deleteMember(int memberId);
	Member showMemberInfo(int memberId);
	List<String> getRoles(int memeberId);
	boolean setGrantMember(int memberId, String dirId);
}
