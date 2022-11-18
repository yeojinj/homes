package com.ssafy.homes.qna.model;

public class QnaDto {

	
	private int no;
	private String userId;
	private String subject;
	private String content;
	private String regTime;
	private String comment ;
	private String comTime;
	private String state;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getComTime() {
		return comTime;
	}
	public void setComTime(String comTime) {
		this.comTime = comTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "QnaDto [no=" + no + ", userId=" + userId + ", subject=" + subject + ", content=" + content
				+ ", regTime=" + regTime + ", comment=" + comment + ", comTime=" + comTime + ", state=" + state + "]";
	}
	
	
	
	
	
}