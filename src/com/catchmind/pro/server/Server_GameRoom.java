package com.catchmind.pro.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.catchmind.pro.dao.AnswerDAO;
import com.catchmind.pro.databox.DataBox;
import com.catchmind.pro.databox.DrawListData;
import com.catchmind.pro.databox.MessageData;
import com.catchmind.pro.databox.TimeData;
import com.catchmind.pro.drawing.DrawInfo;
import com.catchmind.pro.util.Answer;
import com.catchmind.pro.util.Catch_Protocol;
import com.catchmind.pro.vo.UserVO;

import test.QuizTable;

public class Server_GameRoom {
	private Server_GameRoom server_GameRoom = null;
	private int room_number;
	private String room_name;
	private String room_password;
	private List<Server_ChatterAccepter> chatters = null;
	private DrawListData drawList = null;
	private String quiz;
	private boolean started;

	public String getQuiz() {
		return quiz;
	}

	public String getRoom_password() {
		return room_password;
	}

	public synchronized void setRoom_password(String room_password) {
		this.room_password = room_password;
	}

	public int getRoom_number() {
		return room_number;
	}

	public synchronized void setRoom_number(int roon_number) {
		this.room_number = roon_number;
	}

	public String getRoom_name() {
		return room_name;
	}

	public synchronized void setRoom_name(String room_name) {
		this.room_name = room_name;
	}

	public synchronized void setQuiz(String quiz) {
		this.quiz = quiz;
	}

	public boolean isStarted() {
		return started;
	}

	public synchronized void setStarted(boolean started) {
		this.started = started;
	}

	public DrawListData getDrawList() {
		return drawList;
	}

	public synchronized void setDrawList(DrawListData drawList) {
		this.drawList = drawList;
	}

	public Server_GameRoom(int room_number, String room_name, String room_password) {
		this.server_GameRoom = this;
		this.room_number = room_number;
		this.room_name = room_name;
		this.room_password = room_password;
	}

	public Server_GameRoom addChatter(Server_ChatterAccepter chatter) {
		if (chatters == null) {
			chatters = new ArrayList<Server_ChatterAccepter>();
		}
		if (chatters.size() >= 8) {
			roomMemberFill(chatter);
			Server_WaitingRoom.getWaitingRoom().addChatter(chatter);
			return null;
		}

		chatter.getUser().setState(1);
		chatters.add(chatter);
		initAccepter(chatter);

		return this;
	}

	private synchronized void roomMemberFill(Server_ChatterAccepter chatter) {
		try {
			ObjectOutputStream objectOut2 = chatter.getObjectOut();
			objectOut2.writeObject(new DataBox(Catch_Protocol.PROTOCOL_ROOMMEMBERFIIL, "@RoomMemberFill"));
			objectOut2.flush();
		//	objectOut2.reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initAccepter(Server_ChatterAccepter accepter) {
		// ------------------------[옮겨옴]
		try {
			accepter.setChatterListAdmin(this);
			List<UserVO> members = new ArrayList<UserVO>();
			for (Server_ChatterAccepter chatter : chatters) {
				members.add(chatter.getUser());
			}

			for (Server_ChatterAccepter chatter : chatters) {
				synchronized (server_GameRoom) {
					ObjectOutputStream objectOut2 = chatter.getObjectOut();
					// 처음들어온 클라이언트에게 기존의 그림을 그려준다.
					if (chatter == accepter && this.getDrawList() != null) {
						objectOut2.writeObject(new DataBox(Catch_Protocol.PROTOCOL_DRAW, getDrawList()));
						objectOut2.flush();
					}
				}
			}
			sendNotice(accepter.getUser().getNickName() + " 님이 입장하셨습니다 .");

			if (chatters.size() >= 3 && !isStarted()) {
				gameStart();
			}

			if (isStarted()) {
				synchronized (server_GameRoom) {
					accepter.getObjectOut().writeObject(new DataBox(Catch_Protocol.PROTOCOL_TESTER, null));
					accepter.getObjectOut().flush();
				}
			}
			refreshArea();
			System.out.println("ChatterAccepter()2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// -----

	}

	public void removeChatter(Server_ChatterAccepter chatter) {
		if (chatters != null) {
			chatters.remove(chatter);
		}
		System.out.println("removeChatter()");
		chatter.getUser().setState(0);
		chatter.setChatterListAdmin(null);
		Server_WaitingRoom.getWaitingRoom().addChatter(chatter);
		refreshArea();
		sendNotice(chatter.getUser().getNickName() + " 님께서 퇴장하셨습니다.");
		if (chatters == null || chatters.isEmpty()) {
			Server_WaitingRoom.getWaitingRoom().removeRoom(this);
		}
	}

	public List<Server_ChatterAccepter> getChatters() {
		return chatters;
	}

	public void sendMsg(Server_ChatterAccepter chatter, DataBox data) {
		try {

			String msg = (String) data.getData();
			data.setData(new MessageData(chatters.indexOf(chatter) + 1, msg));
			synchronized (server_GameRoom) {
				if (isStarted() && msg.equals(getQuiz())) {
					correct(chatter);
					sendNotice(chatter.getUser().getNickName() + "님의 정답. 정답은 " + quiz + ".");
					quiz = UUID.randomUUID().toString();
				}
			}

			for (int i = 0; i < chatters.size(); i++) {
				System.out.println("데이터 보내는중 " + i);
				synchronized (server_GameRoom) {
					ObjectOutputStream objectOut2 = chatters.get(i).getObjectOut();
					objectOut2.writeObject(data);
					objectOut2.flush();
				//	objectOut2.reset();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			removeChatter(chatter);
		}
	}

	public void sendPaint(Server_ChatterAccepter chatter, DataBox data) {
		try {
			DrawListData draw = (DrawListData) data.getData();
			setDrawList(draw);
			System.out.println("통신되는 클수 : " + chatters.size());
			System.out.println("drawingList size : " + draw.getList().size());

			for (int i = 0; i < chatters.size(); i++) {
				if (chatters.get(i) != chatter) {
					synchronized (server_GameRoom) {
						System.out.println("데이터 보내는중 " + i);
						ObjectOutputStream objectOut2 = chatters.get(i).getObjectOut();

						objectOut2.writeObject(data);
						objectOut2.flush();
						//objectOut2.reset();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			chatters.remove(chatter);
		}
	}

	public void sendNotice(String msg) {
		System.out.println("클에게 보낼 exitMsg : " + msg);
		DataBox data = new DataBox(Catch_Protocol.PROTOCOL_MSG, new MessageData(99, msg));

		for (int i = 0; i < chatters.size(); i++) {
			try {
				synchronized (server_GameRoom) {
					System.out.println("데이터 보내는중 " + i);
					ObjectOutputStream objectOut2 = chatters.get(i).getObjectOut();
					objectOut2.writeObject(data);
					objectOut2.flush();
					//objectOut2.reset();
				}
			} catch (Exception e) {
				e.printStackTrace();
				removeChatter(chatters.get(i));
			}
		}
	}

	public void refreshArea() {
		// 접속한멤버포함한 UserVO 객체들 현재 클라이언트들에게 뿌려주기
		List<UserVO> members = new ArrayList<UserVO>();
		for (int i = 0; i < chatters.size(); i++) {
			members.add(chatters.get(i).getUser());
		}
		for (int i = 0; i < chatters.size(); i++) {
			try {
				synchronized (server_GameRoom) {
					System.out.println(chatters.get(i).getUser());
					ObjectOutputStream objectOut2 = chatters.get(i).getObjectOut();
					objectOut2.writeObject(new DataBox(Catch_Protocol.PROTOCOL_CHANGEAREA, members));
					objectOut2.flush();
					objectOut2.reset();
				}
			} catch (IOException e) {
				e.printStackTrace();
				removeChatter(chatters.get(i));
			}
		}

	}

	public synchronized void gameStart() {
		tr = new GameStart();
		tr.start();
		started = true;
		// tr.setDaemon(true);
	}

	private GameStart tr;

	class GameStart extends Thread {
		private int minute = 0;
		private int second = 0;
		private boolean correct = false;
		private Server_ChatterAccepter examiner;

		public GameStart() {
		}

		@Override
		public void run() {
			int examinerNum = 0;
			init();
			try {
				Thread.sleep(6000);
				sendNotice("지금부터 게임이 시작됩니다");
				for (int i = 0; i < 20; i++) {

					this.examiner = chatters.get(examinerNum);
					correct = false;

					quiz = Answer.getAnswer();
					Thread.sleep(5000);
					sendNotice("ROUND(" + (i + 1) + ")" + examiner.getUser().getNickName() + " 님이 문제를 낼차례입니다.");
					for (Server_ChatterAccepter chatter : chatters) {
						try {
							synchronized (server_GameRoom) {
								if (chatter == examiner) {
									ObjectOutputStream objectOut = chatter.getObjectOut();
									objectOut.writeObject(new DataBox(Catch_Protocol.PROTOCOL_QUIZANSWER, quiz));
									objectOut.flush();
									//objectOut.reset();
								} else {
									ObjectOutputStream objectOut = chatter.getObjectOut();
									objectOut.writeObject(new DataBox(Catch_Protocol.PROTOCOL_TESTER, null));
									objectOut.flush();
								//	objectOut.reset();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							removeChatter(chatter);
						}
					}

					minute = 2;
					second = 30;
					Thread.sleep(4000);
					resetBoard();
					while (true) {
						Thread.sleep(1000);
						if (correct || !chatters.contains(examiner)) {

							break;
						}
						if (second == 0) {
							second = 60;
							minute--;
						}

						second--;

						for (Server_ChatterAccepter chatter : chatters) {
							try {
								synchronized (server_GameRoom) {
									ObjectOutputStream objectOut2 = chatter.getObjectOut();
									objectOut2.writeObject(
											new DataBox(Catch_Protocol.PROTOCOL_TIME, new TimeData(minute, second)));
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						if (minute <= 0 && second <= 0) {
							sendNotice("아무도 정답을 맞추지 못했습니다. 정답 [" + quiz + "]");
							Thread.sleep(5000);
							break;
						}
					}
					for (Server_ChatterAccepter chatter : chatters) {
						try {
							synchronized (server_GameRoom) {
								ObjectOutputStream objectOut = chatter.getObjectOut();
								objectOut.writeObject(new DataBox(Catch_Protocol.PROTOCOL_TESTER, null));
								objectOut.flush();
							//	objectOut.reset();
							}
						} catch (Exception e) {
							e.printStackTrace();
							removeChatter(chatter);
						}
					}
					Thread.sleep(2000);

					if (chatters.size() < 3) {
						break;
					}

					examinerNum++;
					if (examinerNum > chatters.size() - 1) {
						examinerNum = 0;
					}
				}
				System.out.println("게임끝!!");
				gameEnd();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void init() {
			for (Server_ChatterAccepter chatter : chatters) {
				chatter.getUser().clearCorrect();
			}
		}

		private void gameEnd() {
			sendNotice("모든 라운드가 종료되었습니다.");

			UserVO winner = null;
			if (!chatters.isEmpty()) {
				winner = chatters.get(0).getUser();
			}

			for (Server_ChatterAccepter chatter : chatters) {
				if (winner.getCorrectNum() < chatter.getUser().getCorrectNum()) {
					winner = chatter.getUser();
				}
			}

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (winner != null) {
				sendNotice("1등은 " + winner.getCorrectNum() + "개를 맞추신 " + winner.getNickName() + " 님 입니다. ");
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			for (Server_ChatterAccepter chatter : chatters) {
				try {
					synchronized (server_GameRoom) {
						chatter.getUser().clearCorrect();
						ObjectOutputStream objectOut = chatter.getObjectOut();
						objectOut.writeObject(new DataBox(Catch_Protocol.PROTOCOL_QUIZANSWER, ""));
						objectOut.flush();
						//objectOut.reset();
					}
				} catch (IOException e) {
					e.printStackTrace();
					removeChatter(chatter);
				}
			}
			started = false;
			refreshArea();

			if (chatters.size() >= 3) {
				gameStart();
			}
		}

		public void resetBoard() {
			if (drawList == null) {
				drawList = new DrawListData(new ArrayList<DrawInfo>());
			}
			drawList.setList(new ArrayList<DrawInfo>());

			for (int i = 0; i < chatters.size(); i++) {
				try {
					synchronized (server_GameRoom) {
						System.out.println("데이터 보내는중 " + i);
						ObjectOutputStream objectOut2 = chatters.get(i).getObjectOut();
						objectOut2.writeObject(new DataBox(Catch_Protocol.PROTOCOL_DRAW, drawList));
						objectOut2.flush();
						objectOut2.reset();
					}
				} catch (IOException e) {
					e.printStackTrace();
					removeChatter(chatters.get(i));
				}
			}
		}

		public void correct() {
			correct = true;
		}
	}

	public synchronized void correct(Server_ChatterAccepter server_ChatterAccepter) {
		server_ChatterAccepter.getUser().addCorrectNum();
		refreshArea();
		tr.correct();
	}

	public void exitRoom(Server_ChatterAccepter chatter) {
		removeChatter(chatter);
	}
}