package com.catchmind.pro.databox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.catchmind.pro.drawing.DrawInfo;

public class DrawListData implements Serializable {
	private static final long serialVersionUID = 8370623033105269846L;
	private List<DrawInfo> list = new ArrayList<DrawInfo>();
	
	public DrawListData(List<DrawInfo> list) {
		this.list = list;
	}

	public List<DrawInfo> getList() {
		return list;
	}

	public void setList(List<DrawInfo> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "DrawListData [listsize : " + list.size() + "]";
	}
	
	
	
}