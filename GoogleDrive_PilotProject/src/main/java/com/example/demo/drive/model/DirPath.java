package com.example.demo.drive.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
@ToString
public class DirPath {
	private String dirId;
	private String memberId;
	private String parentDirId;
	private String directory;
	private String isFolder;
	private Date createDate;
	private Date modifyDate;
	private String contentType;
	private int fileSize;
	private String fileName;
}
