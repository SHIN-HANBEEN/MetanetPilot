package com.example.demo.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
@ToString
public class Member {
	private String memberId;
	private String password;
	private String email;
	private String role;
}
