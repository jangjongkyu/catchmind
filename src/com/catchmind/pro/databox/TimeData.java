package com.catchmind.pro.databox;

import java.io.Serializable;

public class TimeData implements Serializable {
	private static final long serialVersionUID = 5531675684792362636L;
	int minute;
	int second;
	
	public TimeData(int minute, int second) {
		this.minute = minute;
		this.second = second;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	
}
