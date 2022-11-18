package com.ssafy.homes.user.model;

public class UserDto {
    private String userId;
    private String userPw;
    private String name;
    private String email;
    private String phone;
    private String rule;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	@Override
	public String toString() {
		return "UserDto [userId=" + userId + ", userPw=" + userPw + ", name=" + name + ", email=" + email + ", phone="
				+ phone + ", rule=" + rule + "]";
	}
    
    
}
