package com.catchmind.pro.util;

public class Catch_Protocol {
	//접속프로토콜
	public static final int PROTOCOL_LOGIN = 1;
	public static final int PROTOCOL_JOIN = 2;
	public static final int PROTOCOL_IDFIND = 3;
	public static final int PROTOCOL_PWFIND = 4;
	
	//대기실 프로토콜
	public static final int PROTOCOL_WAITINGROOM_ACCEPT = 5;
	public static final int PROTOCOL_WAITINGROOM_MSG = 6;
	public static final int PROTOCOL_MAKEROOM = 7;
	public static final int PROTOCOL_ROOMREFRESH = 8;
	public static final int PROTOCOL_ROOMACCEPT = 9;
	
	//게임룸 프로토콜
	public static final int PROTOCOL_MSG = 11;
	public static final int PROTOCOL_CHANGEAREA = 12;
	public static final int PROTOCOL_TIME = 13;
	public static final int PROTOCOL_QUIZANSWER = 14;
	public static final int PROTOCOL_TESTER = 15;

	//그림 프로토콜
	public static final int PROTOCOL_DRAW = 101;
	
	//채널프로토콜
	public static final int PROTOCOL_EXITROOM = 201;
	public static final int PROTOCOL_ROOMMEMBERFIIL = 202;
	public static final int PROTOCOL_USERINFO = 203;
	
	
	
	//----------상태 
	public static final int STATE_PASSWORD_EMPTY = 99;
}
