package com.catchmind.pro.databox;

import java.io.Serializable;

public class MessageData implements Serializable{
	 private static final long serialVersionUID = -2592328607205109868L;
	private int playerNumber ; 
	private String msg;
	
	public MessageData(int playerNumber, String msg) {
		this.playerNumber = playerNumber;
		this.msg = msg;
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
