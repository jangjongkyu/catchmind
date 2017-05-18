package com.catchmind.pro.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import com.catchmind.pro.client.Client_Telecom;
import com.catchmind.pro.databox.DataBox;
import com.catchmind.pro.databox.MessageData;
import com.catchmind.pro.databox.RoomData;
import com.catchmind.pro.drawing.Catch;
import com.catchmind.pro.server.Server_GameRoom;
import com.catchmind.pro.vo.UserVO;

public class Window_WaitingRoom extends JFrame implements ActionListener, KeyListener, Runnable {
	private Window_WaitingRoom wating_frame;
	// JFrame frame = new JFrame("대기실");

	private JPanel contentPane;
	private JScrollPane scp_roomList;
	private JTable tb_roomList;
	private JScrollPane scp_userList;
	private JTable tb_userList;
	private JLabel lb_roomlist;
	private JLabel lb_userlist;
	private JButton bt_makeRoom;
	private JTextField textField;

	private Inet4Address ia;
	private Socket soc;
	private PrintWriter pw;
	private BufferedReader br;
	private ScrollPane scrollPane;

	private Client_Telecom telecom;

	private TextArea textArea;
	
	private JButton btn_sendMsg;

	private Dialog_MakeRoom makeRoom;
	public Window_WaitingRoom(Client_Telecom telecom) {
		this.telecom = telecom;
		this.wating_frame = this;

		setTitle("게임 대기실");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 583);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setForeground(new Color(255, 255, 255));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);

		scp_roomList = new JScrollPane();
		scp_roomList.setBounds(25, 43, 344, 219);
		contentPane.add(scp_roomList);

		tb_roomList = new JTable();
		tb_roomList.setShowGrid(false);

		scp_roomList.setViewportView(tb_roomList);

		scp_userList = new JScrollPane();
		scp_userList.setBounds(398, 43, 156, 219);
		scp_userList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scp_userList);

		tb_userList = new JTable();
		tb_userList.setShowGrid(false);
		scp_userList.setViewportView(tb_userList);

		lb_roomlist = new JLabel("방 목록");
		lb_roomlist.setBounds(25, 10, 124, 26);
		lb_roomlist.setFont(new Font("굴림", Font.PLAIN, 15));
		contentPane.add(lb_roomlist);

		lb_userlist = new JLabel("사용자 목록");
		lb_userlist.setBounds(398, 10, 124, 26);
		lb_userlist.setFont(new Font("굴림", Font.PLAIN, 15));
		contentPane.add(lb_userlist);

		JButton bt_roomEnter = new JButton("입장하기");
		bt_roomEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tb_roomList.getSelectedRow() >= 0) {
					RoomData selectRoom = rooms.get(tb_roomList.getSelectedRow());
					Catch canvas = new Catch(telecom);
					Window_Room room_frame = new Window_Room(telecom,canvas);
					room_frame.setTitle(" [ 방제 : "+selectRoom.getRoom_name()+" ] ");
					telecom.setRoom_frame(room_frame);
					telecom.acceptRoom(selectRoom);
					telecom.setCanvas(canvas);
					setVisible(false);
				}
			}
		});
		bt_roomEnter.setBounds(256, 274, 97, 23);
		contentPane.add(bt_roomEnter);

		bt_makeRoom = new JButton("방 만들기");
		bt_makeRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(makeRoom != null){
					makeRoom.dispose();
					makeRoom = null;
				}
				
				makeRoom = new Dialog_MakeRoom(telecom, wating_frame);
				makeRoom.setVisible(true);
			}
		});
		bt_makeRoom.setBounds(143, 274, 97, 23);
		contentPane.add(bt_makeRoom);
		Border lineBorder = BorderFactory.createLineBorder(Color.black, 1);
		Border emptyBorder = BorderFactory.createEmptyBorder(7, 7, 7, 7);

		textField = new JTextField();
		textField.setBounds(25, 511, 437, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.addKeyListener(this);

		btn_sendMsg = new JButton("전송");
		btn_sendMsg.setBounds(474, 510, 80, 23);
		contentPane.add(btn_sendMsg);
		btn_sendMsg.addActionListener(this);
		btn_sendMsg.addKeyListener(this);

		textArea = new TextArea();
		textArea.setForeground(Color.BLACK);
		textArea.setBounds(25, 314, 529, 199);
		textArea.setEditable(false);
		contentPane.add(textArea);
		
		JButton btnNewButton_1 = new JButton("\uAC1C\uC778\uC815\uBCF4\uC218\uC815");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telecom.request_Myinfo();
				new Catch_edit(telecom);
			}
		});
		btnNewButton_1.setBounds(398, 281, 146, 23);
		contentPane.add(btnNewButton_1);
		btn_sendMsg.addActionListener(this);
		btn_sendMsg.addKeyListener(this);

		this.setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() == textField && e.getKeyCode() == KeyEvent.VK_ENTER) {
			telecom.sendMessage_waiting(textField.getText());
			textField.setText("");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_sendMsg){
			telecom.sendMessage_waiting(textField.getText());
			textField.setText("");
		}
	}

	@Override
	public void run() {
	}

	public void refreshGuestInfo(ArrayList<UserVO> users) {
		System.out.println("refreshGuestInfo()");
		tb_userList.setShowGrid(false);
		String columnNames[] = { "닉네임" };
		
		for(UserVO user : users){
			System.out.println(user.getNickName());
		}
		
		Object rowData[][] = new Object[users.size()][];
		for (int i = 0; i < users.size(); i++) {
			Object[] userInfo = new Object[] { users.get(i).getNickName() };
			rowData[i] = userInfo;
		}
		DefaultTableModel defaultTableModel = new DefaultTableModel(rowData, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tb_userList.setModel(defaultTableModel);
		tb_userList.getColumnModel().getColumn(0).setResizable(false);
		repaint();
	}

	private ArrayList<RoomData> rooms;

	public void refreshRoomInfo(DataBox data) {
		//////
		System.out.println("refreshRoomInfo()");
		rooms = (ArrayList<RoomData>) data.getData();
		for(RoomData room : rooms){
			System.out.println(room.getRoom_name());
		}
		tb_roomList.setShowGrid(false);
		String columnNames[] = { "번호", "방이름", "인원", "상태" };
		Object rowData[][] = new Object[rooms.size()][];
		for (int i = 0; i < rooms.size(); i++) {
			String room_state = "대기중";
			if (rooms.get(i).isStarted()) {
				room_state = "게임중";
			}
			Object[] userInfo = new Object[] { rooms.get(i).getRoom_number(), rooms.get(i).getRoom_name(),
					(rooms.get(i).getMemberNum() + "/" + 8), room_state };
			rowData[i] = userInfo;
		}
		DefaultTableModel defaultTableModel = new DefaultTableModel(rowData, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tb_roomList.setModel(defaultTableModel);

		tb_roomList.getColumnModel().getColumn(0).setResizable(false);
		tb_roomList.getColumnModel().getColumn(0).setPreferredWidth(53);
		tb_roomList.getColumnModel().getColumn(1).setResizable(false);
		tb_roomList.getColumnModel().getColumn(1).setPreferredWidth(270);
		tb_roomList.getColumnModel().getColumn(2).setResizable(false);
		tb_roomList.getColumnModel().getColumn(3).setResizable(false);
		repaint();
	}

	public void receiveMsg(MessageData msg) {
		System.out.println("윈도우 Msg");
		textArea.append(msg.getMsg() + "\n");
	}
}