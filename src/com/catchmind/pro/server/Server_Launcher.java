package com.catchmind.pro.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.catchmind.pro.dao.UserDAO;
import com.catchmind.pro.gui.Window_ServerInfo;
import com.catchmind.pro.vo.UserVO;

/*
 * 최종 업데이트 날짜 :4월 28일 
 */
public class Server_Launcher {
	private ServerSocket ss;
	private Socket soc;
	private PrintWriter pw;
	private BufferedReader br;
	private UserDAO dao;

	// 서버 게임룸
	public Server_Launcher() throws Exception {
		// chm = new ConcurrentHashMap<String, Socket>();
		dao = UserDAO.getUserDAO();
		server_start();
	}

	private void server_start() {
		try {
			ss = new ServerSocket(20000);
		         new Window_ServerInfo(ss.getLocalPort(),InetAddress.getLocalHost().getHostAddress());
		        // ss.setSoTimeout(50000);
		         
		        
			if (ss != null) {
				while (true) {
					soc = ss.accept();
					ChatClient cc = new ChatClient(soc);
					cc.start();
					System.out.println("서버를 실행합니다.\n");
				}
			}
		} catch (IOException e) {
			System.out.println("소켓이 이미 사용중입니다.\n");
			System.exit(0);
		}
	}

	class ChatClient extends Thread {
		private Socket soc = null;
		private UserManagement um = new UserManagement();
		private UserVO user = null;

		public ChatClient(Socket soc) {
			this.soc = soc;
		}

		public void run() {
			while (true) {
				try {
					br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
					// br.readLine();
					String msg = br.readLine();
					System.out.println(soc + " 접속");
					System.out.println(msg);

					if (msg == null)
						break;
					if (msg.charAt(0) == '!') { // 로그인
						UserVO user = um.login(soc, msg);
						if (user != null) {
							this.user = user;
							Server_ChatterAccepter chatterAccepter = new Server_ChatterAccepter(soc, user);
							Server_WaitingRoom.getWaitingRoom().addChatter(chatterAccepter);
							break;
						}
					} else if (msg.charAt(0) == '*') { // 회원가입
						um.join(msg);
					} else if (msg.charAt(0) == '#') { // 아이디 찾기
						um.idfind(msg);
					} else if (msg.charAt(0) == '$') { // 패스워드 찾기
						um.pwfind(msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("사용자 로그인화면에서 종료");
					return;
				}
			}
		}
	}

	class UserManagement {
		public UserVO login(Socket soc, String msg) throws Exception{
			UserVO vo = new UserVO();
			System.out.println("[login]");
			String msg2 = msg.substring(1);
			String msgArr1[] = msg2.split("!", 2);
			String id = msgArr1[0];
			String password = msgArr1[1];
			System.out.println("dao 들어가기전");
			vo.setUser_id(id);
			vo.setUser_password(password);
			System.out.println("dao 들어가기전2");
			UserVO user = dao.getUser(vo);
			System.out.println("[서버측 dao 확인]");
			System.out.println(user);
			if (user != null) {
				pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
				System.out.println("대기실 검사중");
				if (Server_WaitingRoom.getWaitingRoom().getChatters() != null
						&& !Server_WaitingRoom.getWaitingRoom().getChatters().isEmpty()) {
					for (Server_ChatterAccepter waitingChatter : Server_WaitingRoom.getWaitingRoom().getChatters()) {
						if (waitingChatter.getUser().getSeq() == user.getSeq()) {
							System.out.println("대기실 메시지보내기중");
							pw.println("@accepting");
							pw.flush();
							System.out.println("대기실 메시지보내기 다끝남");
							return null;
						}
					}
				}
				System.out.println("게임방 검사중");
				if (Server_WaitingRoom.getWaitingRoom().getRoom_list() != null
						&& !Server_WaitingRoom.getWaitingRoom().getRoom_list().isEmpty())
					for (Server_GameRoom room : Server_WaitingRoom.getWaitingRoom().getRoom_list()) {

						if (room.getChatters() != null && !room.getChatters().isEmpty()) {
							for (Server_ChatterAccepter chatter : room.getChatters()) {
								if (chatter.getUser().getSeq() == user.getSeq()) {
									pw.println("@accepting");
									pw.flush();
									return null;
								}
							}
						}

					}

				pw.println("@loginOK"); // 클라이언트에 로그인 성공 신호
				pw.flush();
				return user;
			} else {
				pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
				pw.println("@loginFail"); // 클라이언트에 로그인 실패 신호
				pw.flush();
				return null;
			}
		} // 로그인

		public void join(String msg) {
			try {
				UserVO vo = new UserVO();
				System.out.println("join()");
				String msg2 = msg.substring(1);
				System.out.println(msg2);
				String msgArr2[] = msg2.split("\\*", 9);

				String id = msgArr2[0];
				String password = msgArr2[1];
				String email = msgArr2[2];
				String nickname = msgArr2[3];
				String name = msgArr2[4];
				int year = Integer.parseInt(msgArr2[5]);
				int month = Integer.parseInt(msgArr2[6]);
				int day = Integer.parseInt(msgArr2[7]);
				int user_pokemon = Integer.parseInt(msgArr2[8]);

				System.out.println("데이터 잘랐음");

				vo.setUser_id(id);
				vo.setUser_password(password);
				vo.setEmail(email);
				vo.setNickName(nickname);
				vo.setPersonName(name);
				vo.setBirth_year(year);
				vo.setBirth_month(month);
				vo.setBirth_day(day);
				vo.setUser_pokemon(user_pokemon);
				System.out.println("dao 넣기전 : " + vo);
				boolean ok = dao.insertUser(vo);
				if (ok) {
					pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
					pw.println("@joinOK"); // 클라이언트에 로그인 성공 신호
					pw.flush();
					// enter(id, soc); // 입장메시지 전송
					Thread.sleep(5000);
				} else {
					pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
					pw.println("@joinFail"); // 클라이언트에 로그인 성공 신호
					pw.flush();
					Thread.sleep(5000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // 회원가입

		public void idfind(String msg) {
			try {
				UserVO vo = new UserVO();
				String msg2 = msg.substring(1);
				String msgArr3[] = msg2.split("#", 5);
				String email = msgArr3[0];
				String name = msgArr3[1];
				int year = Integer.parseInt(msgArr3[2]);
				int month = Integer.parseInt(msgArr3[3]);
				int day = Integer.parseInt(msgArr3[4]);

				vo.setEmail(email);
				vo.setPersonName(name);
				vo.setBirth_year(year);
				vo.setBirth_month(month);
				vo.setBirth_day(day);
				String response = dao.idFind(vo);

				if (response != null) {
					pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
					pw.println(response); // 클라이언트에 로그인 성공 신호
					pw.flush();
					// enter(id, soc); // 입장메시지 전송

				} else {
					pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
					pw.println("@idFindFail"); // 클라이언트에 로그인 성공 신호
					pw.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // 아이디 찾기

		public void pwfind(String msg) {
			try {
				UserVO vo = new UserVO();
				String msg2 = msg.substring(1);
				String msgArr4[] = msg2.split("\\$", 5);
				String id = msgArr4[0];
				String name = msgArr4[1];
				int year = Integer.parseInt(msgArr4[2]);
				int month = Integer.parseInt(msgArr4[3]);
				int day = Integer.parseInt(msgArr4[4]);

				vo.setUser_id(id);
				vo.setPersonName(name);
				vo.setBirth_year(year);
				vo.setBirth_month(month);
				vo.setBirth_day(day);

				String response = dao.pwFind(vo);
				System.out.println("response : " + response);

				if (response.equals("@pwFindOK")) {
					pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
					pw.println(response); // 클라이언트에 로그인 성공 신호
					pw.flush();
					// enter(id, soc); // 입장메시지 전송

				} else {
					pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
					pw.println("@pwFindFail"); // 클라이언트에 로그인 성공 신호
					pw.flush();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws Exception {
		new Server_Launcher();
	}
}