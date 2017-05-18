package com.catchmind.pro.vo;

import java.io.Serializable;

public class UserVO implements Serializable{
	//DataBase 데이터
	private int seq;
	private String user_id;
	private String user_password;
	private String email;
	private String nickName;
	private String personName;
	private int birth_year;
	private int birth_month;
	private int birth_day;
	private int user_pokemon;
	
	//동적데이터
	private int correctNum;
	// 0은 대기실 1은 게임방
	private int state;
	
	public void addCorrectNum(){
		correctNum++;
	}
	public void clearCorrect(){
		correctNum = 0;
	}
	public int getCorrectNum() {
		return correctNum;
	}
	public int getUser_pokemon() {
		return user_pokemon;
	}
	public void setUser_pokemon(int user_pokemon) {
		this.user_pokemon = user_pokemon;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getBirth_year() {
		return birth_year;
	}
	public void setBirth_year(int birth_year) {
		this.birth_year = birth_year;
	}
	public int getBirth_month() {
		return birth_month;
	}
	public void setBirth_month(int birth_month) {
		this.birth_month = birth_month;
	}
	public int getBirth_day() {
		return birth_day;
	}
	public void setBirth_day(int birth_day) {
		this.birth_day = birth_day;
	}
	

	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return "UserVO [seq=" + seq + ", user_id=" + user_id + ", user_password=" + user_password + ", email=" + email
				+ ", nickName=" + nickName + ", personName=" + personName + ", birth_year=" + birth_year
				+ ", birth_month=" + birth_month + ", birth_day=" + birth_day + ", user_pokemon=" + user_pokemon
				+ ", correctNum=" + correctNum + "]";
	}

}
