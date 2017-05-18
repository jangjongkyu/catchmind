package com.catchmind.pro.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.catchmind.pro.databox.DataBox;
import com.catchmind.pro.databox.DrawListData;
import com.catchmind.pro.databox.MessageData;
import com.catchmind.pro.databox.RoomData;
import com.catchmind.pro.databox.TimeData;
import com.catchmind.pro.drawing.Catch;
import com.catchmind.pro.gui.Window_Room;
import com.catchmind.pro.gui.Window_WaitingRoom;
import com.catchmind.pro.util.Catch_Protocol;
import com.catchmind.pro.util.Toast;
import com.catchmind.pro.vo.UserVO;

public class Client_Telecom extends Thread {
	private Socket soc;
	private ObjectInputStream objectIn;
	private ObjectOutputStream objectOut;

	private Catch canvas;
	//private Client_Launcher client = null;
	private Window_Room room_frame = null;

	private Client_Telecom_WaitingRoom waiting_pro = null;
	private Window_WaitingRoom waiting_frame = null;
	private UserVO userInfo;
	public Client_Telecom(Client_Launcher client) {
		soc = client.getSoc();
		waiting_pro = new Client_Telecom_WaitingRoom();
		waiting_frame = new Window_WaitingRoom(this);
		/*
		 * canvas = new Catch(this); room_frame = new Window_Room(this, canvas);
		 */

		try {

			objectOut = new ObjectOutputStream(soc.getOutputStream());
			objectIn = new ObjectInputStream(soc.getInputStream());
			start();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				objectOut = new ObjectOutputStream(soc.getOutputStream());
				objectIn = new ObjectInputStream(soc.getInputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		System.out.println("client_room_protocol 끝3");
	}

	public Window_Room getRoom_frame() {
		return room_frame;
	}
	public void setRoom_frame(Window_Room room_frame) {
		this.room_frame = room_frame;
	}

	@Override
	public void run() {
		try {
			/*
			 * Integer st = (Integer) objectIn.readObject(); System.out.println(
			 * "시작문자 : " + st);
			 */
			while (true) {
				// 프로토콜 선별중
				System.out.println("프로토콜 대기중");
				DataBox data = (DataBox) objectIn.readObject();

				switch (data.getProtocol()) {
				case Catch_Protocol.PROTOCOL_MSG:
					receiveMessage(data);
					break;
				case Catch_Protocol.PROTOCOL_CHANGEAREA:
					refreshArea(data);
					break;
				case Catch_Protocol.PROTOCOL_TIME:
					receiveTime(data);
					break;
				case Catch_Protocol.PROTOCOL_QUIZANSWER:
					examinerSetting(data);
					break;
				case Catch_Protocol.PROTOCOL_TESTER:
					testerSetting();
					break;
				case Catch_Protocol.PROTOCOL_DRAW:
					receiveDraw(data);
					break;
				case Catch_Protocol.PROTOCOL_WAITINGROOM_ACCEPT:
					waiting_pro.acceptGuest(data);
					break;
				case Catch_Protocol.PROTOCOL_WAITINGROOM_MSG:
					waiting_pro.receiveMsg(data);
					break;
				case Catch_Protocol.PROTOCOL_ROOMREFRESH:
					waiting_frame.refreshRoomInfo(data);
					break;
				case Catch_Protocol.PROTOCOL_ROOMMEMBERFIIL:
					JOptionPane.showMessageDialog(null, "방에 인원이 가득찼습니다.");
					room_frame.dispose();
					waiting_frame.setVisible(true);
					break;
				case Catch_Protocol.PROTOCOL_USERINFO:
					userInfo = (UserVO)data.getData();
					break;
					default : 
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "서버가 종료되었습니다.");
			System.err.println("서버가 종료되었습니다.");
		}
	}
	
	

	public UserVO getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserVO userInfo) {
		this.userInfo = userInfo;
	}

	public Window_WaitingRoom getWaiting_frame() {
		return waiting_frame;
	}

	public void setWaiting_frame(Window_WaitingRoom waiting_frame) {
		this.waiting_frame = waiting_frame;
	}

	public Catch getCanvas() {
		return canvas;
	}

	public void setCanvas(Catch canvas) {
		this.canvas = canvas;
	}

	private void testerSetting() {
		room_frame.testerSetting();
	}

	private void examinerSetting(DataBox data) {
		room_frame.examinerSetting((String) data.getData());
	}

	private void receiveTime(DataBox data) {
		TimeData time = (TimeData) data.getData();
		room_frame.refreshTime(time);
	}

	private void refreshArea(DataBox data) {
		try {
			List<UserVO> members = (List<UserVO>) data.getData();
			room_frame.refreshArea(members);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void receiveDraw(DataBox data) {
		System.out.println("그림 받아오는중 .......");

		try {
			DrawListData draw = (DrawListData) data.getData();
			canvas.setDrawingList(draw.getList());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void receiveMessage(DataBox data) {
		try {
			MessageData msgData = (MessageData) data.getData();
			System.out.println("receive확인 : ");
			System.out.println(room_frame);
			Toast.makeText(room_frame, msgData.getMsg(), Toast.LENGTH_LONG, Toast.Style.NORMAL,
					msgData.getPlayerNumber()).display();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void request_Myinfo(){
		try {
			objectOut.writeObject(new DataBox(Catch_Protocol.PROTOCOL_USERINFO, ""));
			objectOut.flush();
			objectOut.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String msg) {
		try {
			objectOut.writeObject(new DataBox(Catch_Protocol.PROTOCOL_MSG, msg));
			objectOut.flush();
			objectOut.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendPaint(DrawListData data) {
		try {
			objectOut.writeObject(new DataBox(Catch_Protocol.PROTOCOL_DRAW, data));
			objectOut.flush();

			objectOut.reset();
			System.out.println("sendPaint [다보냄]");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Client_Telecom_WaitingRoom getWaiting_Pro() {
		return this.waiting_pro;
	}

	/////////////// ------------------- [대기실 응답]

	class Client_Telecom_WaitingRoom {

		public void sendMessage(String msg) {
			try {
				objectOut.writeObject(new DataBox(Catch_Protocol.PROTOCOL_WAITINGROOM_MSG, msg));
				objectOut.flush();
				objectOut.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void acceptGuest(DataBox data) {
			try {
				ArrayList<UserVO> members = (ArrayList<UserVO>) data.getData();
				waiting_frame.refreshGuestInfo(members);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void receiveMsg(DataBox data) {
			try {
				System.out.println("Telecom receiveMsg");
				MessageData msgData = (MessageData) data.getData();
				waiting_frame.receiveMsg(msgData);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void makeRoom(String room_name) {
			try {
				System.out.println("텔레콤 : "+room_name);
				objectOut.writeObject(new DataBox(Catch_Protocol.PROTOCOL_MAKEROOM, new RoomData(room_name, "")));
				objectOut.flush();
				objectOut.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void acceptRoom(RoomData roomData) {
			try{
				System.out.println("텔레콤 : "+roomData);
				objectOut.writeObject(new DataBox(Catch_Protocol.PROTOCOL_ROOMACCEPT, roomData));
				objectOut.flush();
				objectOut.reset();
			}catch(IOException e){
				e.printStackTrace();
			}
		}

		public void roomExit() {
			try{
				System.out.println("텔레콤 방나감");
				objectOut.writeObject(new DataBox(Catch_Protocol.PROTOCOL_EXITROOM, "@EXIT"));
				objectOut.flush();
				objectOut.reset();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	public void sendMessage_waiting(String msg) {
		waiting_pro.sendMessage(msg);
	}

	public void makeRoom(String room_name) {
		waiting_pro.makeRoom(room_name);
	}
	
	public void acceptRoom(RoomData roomData) {
		waiting_pro.acceptRoom(roomData);
	}

	public void roomExit() {
		waiting_pro.roomExit();
	}

}
