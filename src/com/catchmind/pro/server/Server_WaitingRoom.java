package com.catchmind.pro.server;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.catchmind.pro.databox.DataBox;
import com.catchmind.pro.databox.MessageData;
import com.catchmind.pro.databox.RoomData;
import com.catchmind.pro.util.Catch_Protocol;
import com.catchmind.pro.vo.UserVO;

public class Server_WaitingRoom {
	private static Server_WaitingRoom waitingRoom = null;
	private List<Server_ChatterAccepter> chatters = null;
	private List<Server_GameRoom> room_list = null;

	public static Server_WaitingRoom getWaitingRoom() {
		if (waitingRoom == null) {
			waitingRoom = new Server_WaitingRoom();
		}
		return waitingRoom;
	}

	
	
	public List<Server_GameRoom> getRoom_list() {
		return room_list;
	}



	private Server_WaitingRoom() {
	}

	public void removeRoom(Server_GameRoom removeRoom) {
		room_list.remove(removeRoom);
		roomRefresh();
	}

	public void addChatter(Server_ChatterAccepter chatter) {
		if (chatters == null) {
			chatters = new ArrayList<Server_ChatterAccepter>();
		}
		System.out.println("addChatter()");
		chatter.getUser().clearCorrect();
		chatters.add(chatter);
		guestRefresh();
		roomRefresh();
		System.out.println("[현재 대기실 상황]");
		for (Server_ChatterAccepter chat : chatters) {
			System.out.println(chat.getUser().getNickName());
		}
	}

	public void removeChatter(Server_ChatterAccepter chatter) {
		if (chatters != null) {
			this.chatters.remove(chatter);
		}
		guestRefresh();
		roomRefresh();
	}

	public List<Server_ChatterAccepter> getChatters() {
		return chatters;
	}

	public void guestRefresh() {
		try {
			List<UserVO> users = new ArrayList<UserVO>();
			for (Server_ChatterAccepter chatter : chatters) {
				users.add(chatter.getUser());
			}
			DataBox data = new DataBox(Catch_Protocol.PROTOCOL_WAITINGROOM_ACCEPT, users);

			for (int i = 0; i < chatters.size(); i++) {
				try {
					System.out.println("데이터 보내는중 " + i);
					ObjectOutputStream objectOut = chatters.get(i).getObjectOut();
					objectOut.writeObject(data);
					objectOut.flush();
					//objectOut.reset();
				} catch (Exception e) {
					e.printStackTrace();
					removeChatter(chatters.get(i));
				}
			}
		} catch (Exception e) {

		}
	}
	
	public void sendUserInfo(Server_ChatterAccepter chatter){
		try {
				for (int i = 0; i < chatters.size(); i++) {
				if(chatters.get(i) == chatter){
				System.out.println("데이터 보내는중 " + i);
				ObjectOutputStream objectOut2 = chatters.get(i).getObjectOut();
				DataBox data = new DataBox(Catch_Protocol.PROTOCOL_USERINFO, chatters.get(i).getUser());
				objectOut2.writeObject(data);
				objectOut2.flush();
				//objectOut2.reset();
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMsg(String userNick, DataBox data) {
		try {
			System.out.println("대기실의 server sendMsg");
			List<Server_ChatterAccepter> chatters = this.chatters;

			String msg = (String) data.getData();
			data.setData(new MessageData(chatters.indexOf(this) + 1, userNick + " : " + msg));

			for (int i = 0; i < chatters.size(); i++) {
				System.out.println("대기실 데이터 보내는중 " + i);

				ObjectOutputStream objectOut2 = chatters.get(i).getObjectOut();
				objectOut2.writeObject(data);
				objectOut2.flush();
				//objectOut2.reset();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void makeRoom(Server_ChatterAccepter accepter, DataBox data) {
		if (room_list == null) {
			room_list = new ArrayList<Server_GameRoom>();
		}

		RoomData roomData = (RoomData) data.getData();

		Random random = new Random();
		int roomNumber = 0;
		while (true) {
			roomNumber = random.nextInt(200);
			for (Server_GameRoom room : room_list) {
				if (roomNumber == room.getRoom_number()) {
					continue;
				}
			}
			break;
		}
		System.out.println("make room : " + roomData.getRoom_name());
		Server_GameRoom room = new Server_GameRoom(roomNumber, roomData.getRoom_name(), "");
		room_list.add(room);
		room.addChatter(accepter);
		this.removeChatter(accepter);
		roomRefresh();
		guestRefresh();
	}

	public void acceptRoom(Server_ChatterAccepter accepter, DataBox data) {
		if (room_list == null) {
			room_list = new ArrayList<Server_GameRoom>();
		}
		RoomData roomData = (RoomData) data.getData();

		for (Server_GameRoom room : room_list) {
			if (room.getRoom_number() == roomData.getRoom_number()) {
				room.addChatter(accepter);
				removeChatter(accepter);
			}
		}
	}

	private void roomRefresh() {
		// try {
		List<RoomData> room_datas = new ArrayList<RoomData>();
		if (room_list != null) {
			for (Server_GameRoom room : room_list) {
				System.out.println("방이름은 ? " + room.getRoom_name());
				RoomData r = new RoomData(room.getRoom_name(), room.getRoom_password());
				r.setRoom_number(room.getRoom_number());
				r.setMemberNum(room.getChatters().size());
				r.setStarted(room.isStarted());
				room_datas.add(r);
			}
		}
		DataBox data = new DataBox(Catch_Protocol.PROTOCOL_ROOMREFRESH, room_datas);
		if (chatters != null) {
			for (int i = 0; i < chatters.size(); i++) {
				try {
					System.out.println("roomRefresh 보내는중 ");
					System.out.println("대기실 데이터 보내는중 " + i);
					ObjectOutputStream objectOut2 = chatters.get(i).getObjectOut();
					objectOut2.writeObject(data);
					objectOut2.flush();
					//objectOut2.reset();
				} catch (Exception e) {
					e.printStackTrace();
					removeChatter(chatters.get(i));
				}
			}
		}

	}

}