package com.catchmind.pro.databox;

import java.io.Serializable;

public class DataBox implements Serializable{
	private static final long serialVersionUID = -4871184326969363654L;
	private int protocol ; 
	private Object data ;
	
	public DataBox(int protocol, Object data) {
		this.protocol = protocol;
		this.data = data;
	}
	public int getProtocol() {
		return protocol;
	}
	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
	
}
