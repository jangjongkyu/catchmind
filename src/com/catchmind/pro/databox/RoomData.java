package com.catchmind.pro.databox;

import java.io.Serializable;

public class RoomData implements Serializable{
	private static final long serialVersionUID = 7708323940188375273L;
	private int room_number;
	private String room_name;
	private String room_password;
	private int memberNum;
	private boolean started;
	
	public boolean isStarted() {
		return started;
	}
	public void setStarted(boolean started) {
		this.started = started;
	}
	public int getRoom_number() {
		return room_number;
	}
	public void setRoom_number(int room_number) {
		this.room_number = room_number;
	}
	public int getMemberNum() {
		return memberNum;
	}
	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}
	public RoomData(String room_name, String room_password) {
		this.room_name = room_name;
		this.room_password = room_password;
	}
	public String getRoom_name() {
		return room_name;
	}
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
	public String getRoom_password() {
		return room_password;
	}
	public void setRoom_password(String room_password) {
		this.room_password = room_password;
	}
	
	
}
