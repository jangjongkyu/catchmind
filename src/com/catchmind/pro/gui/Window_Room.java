package com.catchmind.pro.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import com.catchmind.pro.client.Client_Launcher;
import com.catchmind.pro.client.Client_Telecom;
import com.catchmind.pro.databox.DrawListData;
import com.catchmind.pro.databox.TimeData;
import com.catchmind.pro.drawing.Catch;
import com.catchmind.pro.util.BoundDocument;
import com.catchmind.pro.util.WindowMovingObserver;
import com.catchmind.pro.vo.UserVO;

import javax.swing.UIManager;
import javax.swing.SwingConstants;

public class Window_Room extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2555804705872092879L;
	private JPanel contentPane;
	private JTextField textField_chat;

	private ButtonGroup buttonGroup;
	private JToggleButton buttonBlack;
	private JToggleButton buttonRed;
	private JToggleButton buttonBlue;
	private JToggleButton buttonGreen;
	private JToggleButton buttonYellow;
	private JToggleButton buttonEraser;

	private JButton btremove;
	private JButton upbtn;
	private JButton downbtn;
	/*
	 * private Color color = Color.BLACK; private int up = 2; private int down =
	 * 2; private int dist = 0;
	 */
	
	//윈도우창이 움직이는이벤트를 잡아내어 객체들에게 알려주는  리스너
	private WindowMovingObserver toastObserver = new WindowMovingObserver();

	private JLabel label_penSize;

	private Catch canvas = null;

	private Client_Launcher client = null;
	private Client_Telecom room_telecom = null;

	private JPanel namep_01;
	private JPanel namep_02;
	private JPanel namep_03;
	private JPanel namep_04;
	private JPanel namep_05;
	private JPanel namep_06;
	private JPanel namep_07;
	private JPanel namep_08;
	
	private JLabel label_quiz;  //문제 답안
	private JLabel label_time;

	private JLabel label_name[] = new JLabel[8];
	private JLabel label_pokemon[] = new JLabel[8];
	private JLabel label_correct[] = new JLabel[8];

	public WindowMovingObserver getToastObserver() {
		return toastObserver;
	}

	public void addToastObserver(Observer o) {
		toastObserver.addObserver(o);
	}

	public void deleteToastObserver(Observer o) {
		toastObserver.deleteObserver(o);
	}

	private Window_Room window_room = null;

	private void init() {
		setResizable(false);
		centerLocation(this);
	}

	private void centerLocation(Component component) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		component.setLocation((int) (dimension.getWidth() / 2) - (component.getHeight() / 2),
				(int) (dimension.getHeight() / 2) - (component.getHeight() / 2));
	}

	public Window_Room(Client_Telecom room_telecom, Catch canvas) {
		this.setResizable(false);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.canvas = canvas;
		canvas.setSize(562, 472);
		canvas.setLocation(24, 20);
		init();

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				room_telecom.roomExit();
				room_telecom.getWaiting_frame().setVisible(true);
			}
			
		});
		
		this.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentMoved(ComponentEvent e) {
				toastObserver.action();
			}

		});

		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.getImage("img/pen/black.png");
		Cursor myCursor = tk.createCustomCursor(img, new Point(0, 0), "dynamite stick");
		setCursor(myCursor);

		this.window_room = this;
		this.room_telecom = room_telecom;

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel.setBounds(198, 69, 611, 515);
		contentPane.add(panel);
		panel.setLayout(null);

		// canvas.setBounds(10, 10, 591, 495);
		panel.add(canvas);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(65, 105, 225), 3, true));
		panel_1.setBounds(0, 48, 200, 139);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 2));

		JPanel userp_1 = new JPanel();
		panel_1.add(userp_1);
		userp_1.setLayout(null);

		label_pokemon[0] = new JLabel("");
		label_pokemon[0].setBounds(12, 10, 73, 113);
		userp_1.add(label_pokemon[0]);

		JPanel panel_25 = new JPanel();
		panel_25.setBorder(new MatteBorder(0, 3, 0, 0, (Color) new Color(0, 0, 0)));
		panel_1.add(panel_25);
		panel_25.setLayout(null);

		namep_01 = new JPanel();
		namep_01.setBounds(3, 0, 94, 44);
		namep_01.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		panel_25.add(namep_01);
		namep_01.setLayout(null);

		label_name[0] = new JLabel("", JLabel.CENTER);
		label_name[0].setBounds(0, 10, 94, 24);
		label_name[0].setFont(new Font("",Font.PLAIN,11));
		namep_01.add(label_name[0]);

		JPanel lvp_1 = new JPanel();
		lvp_1.setBounds(3, 44, 94, 44);
		lvp_1.setBorder(new MatteBorder(2, 0, 2, 0, (Color) new Color(0, 0, 0)));
		panel_25.add(lvp_1);
		lvp_1.setLayout(null);

		JPanel corp_1 = new JPanel();
		corp_1.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		corp_1.setBounds(3, 89, 94, 44);
		panel_25.add(corp_1);
		corp_1.setLayout(null);
		
		label_correct[0] = new JLabel("정답 : 0",JLabel.CENTER);
		label_correct[0].setBounds(0, 10, 94, 24);
		corp_1.add(label_correct[0]);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBackground(new Color(192, 192, 192));
		lblNewLabel.setIcon(new ImageIcon(this.getClass().getResource("/gametitle.png")));
		lblNewLabel.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 44));
		lblNewLabel.setBounds(281, 0, 439, 72);
		contentPane.add(lblNewLabel);

		textField_chat = new JTextField();
		textField_chat.setBounds(611, 661, 326, 27);
		contentPane.add(textField_chat);
		textField_chat.setColumns(30);
		textField_chat.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					room_telecom.sendMessage(textField_chat.getText());
					textField_chat.setText("");
				}
			}

		});

		JLabel lblChatting = new JLabel("CHATTING");
		lblChatting.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 18));
		lblChatting.setBounds(611, 641, 98, 15);
		contentPane.add(lblChatting);

		buttonGroup = new ButtonGroup();
		// blackbt
		buttonBlack = new JToggleButton("");
		buttonBlack.setIcon(new ImageIcon(this.getClass().getResource("/color/color_black.png")));
		buttonBlack.setBounds(208, 594, 57, 59);
		contentPane.add(buttonBlack);
		buttonBlack.addActionListener(this);

		// button
		buttonRed = new JToggleButton("");
		buttonRed.setIcon(new ImageIcon(this.getClass().getResource("/color/color_red.png")));
		buttonRed.setBounds(267, 594, 57, 59);
		contentPane.add(buttonRed);
		buttonRed.addActionListener(this);

		// button_1
		buttonBlue = new JToggleButton("");
		buttonBlue.setIcon(new ImageIcon(this.getClass().getResource("/color/color_blue.png")));
		buttonBlue.addActionListener(this);
		buttonBlue.setBounds(325, 594, 57, 59);
		contentPane.add(buttonBlue);

		// button_2
		buttonGreen = new JToggleButton("");
		buttonGreen.setIcon(new ImageIcon(this.getClass().getResource("/color/color_green.png")));
		buttonGreen.setBounds(383, 594, 57, 59);
		contentPane.add(buttonGreen);
		buttonGreen.addActionListener(this);

		// button_3
		buttonYellow = new JToggleButton("");
		buttonYellow.setIcon(new ImageIcon(this.getClass().getResource("/color/color_yellow.png")));
		buttonYellow.setBounds(441, 594, 57, 59);

		contentPane.add(buttonYellow);
		buttonYellow.addActionListener(this);

		// bteraser
		buttonEraser = new JToggleButton("");
		buttonEraser.setIcon(new ImageIcon(this.getClass().getResource("/color/eraser.png")));
		buttonEraser.setBounds(503, 594, 57, 59);
		contentPane.add(buttonEraser);
		buttonEraser.addActionListener(this);

		buttonGroup.add(buttonBlack);
		buttonGroup.add(buttonRed);
		buttonGroup.add(buttonBlue);
		buttonGroup.add(buttonGreen);
		buttonGroup.add(buttonYellow);
		buttonGroup.add(buttonEraser);

		// btnNewButton_2
		btremove = new JButton("모두지우기");
		btremove.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		btremove.setBounds(572, 599, 104, 32);
		contentPane.add(btremove);
		btremove.addActionListener(this);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(65, 105, 225), 3, true));
		panel_2.setBounds(0, 188, 200, 139);
		contentPane.add(panel_2);
		panel_2.setLayout(new GridLayout(1, 2));

		JPanel userp_2 = new JPanel();
		panel_2.add(userp_2);
		userp_2.setLayout(null);

		label_pokemon[2] = new JLabel("");
		label_pokemon[2].setBounds(12, 10, 73, 113);
		userp_2.add(label_pokemon[2]);

		JPanel panel_28 = new JPanel();
		panel_28.setBorder(new MatteBorder(0, 3, 0, 0, (Color) new Color(0, 0, 0)));
		panel_2.add(panel_28);
		panel_28.setLayout(null);

		namep_03 = new JPanel();
		namep_03.setBounds(3, 0, 94, 44);
		namep_03.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		panel_28.add(namep_03);
		namep_03.setLayout(null);

		label_name[2] = new JLabel("", JLabel.CENTER);
		label_name[2].setBounds(0, 10, 94, 24);
		label_name[2].setFont(new Font("",Font.PLAIN,11));
		namep_03.add(label_name[2]);

		JPanel lvp_2 = new JPanel();
		lvp_2.setBounds(3, 44, 94, 44);
		lvp_2.setBorder(new MatteBorder(2, 0, 2, 0, (Color) new Color(0, 0, 0)));
		panel_28.add(lvp_2);
		lvp_2.setLayout(null);

		JPanel corp_2 = new JPanel();
		corp_2.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		corp_2.setBounds(3, 89, 94, 44);
		panel_28.add(corp_2);
		corp_2.setLayout(null);
		
		label_correct[2] = new JLabel("\uC815\uB2F5 : 0", SwingConstants.CENTER);
		label_correct[2].setBounds(0, 10, 94, 24);
		corp_2.add(label_correct[2]);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(65, 105, 225), 3, true));
		panel_3.setBounds(0, 329, 200, 139);
		contentPane.add(panel_3);
		panel_3.setLayout(new GridLayout(1, 2));

		JPanel userp_3 = new JPanel();
		panel_3.add(userp_3);
		userp_3.setLayout(null);

		label_pokemon[4] = new JLabel("");
		label_pokemon[4].setBounds(12, 10, 73, 113);
		userp_3.add(label_pokemon[4]);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new MatteBorder(0, 3, 0, 0, (Color) new Color(0, 0, 0)));
		panel_3.add(panel_4);
		panel_4.setLayout(null);

		namep_05 = new JPanel();
		namep_05.setBounds(3, 0, 94, 44);
		namep_05.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		panel_4.add(namep_05);
		namep_05.setLayout(null);

		label_name[4] = new JLabel("", JLabel.CENTER);
		label_name[4].setBounds(0, 10, 94, 24);
		label_name[4].setFont(new Font("",Font.PLAIN,11));
		namep_05.add(label_name[4]);

		JPanel lvp_3 = new JPanel();
		lvp_3.setBounds(3, 44, 94, 44);
		lvp_3.setBorder(new MatteBorder(2, 0, 2, 0, (Color) new Color(0, 0, 0)));
		panel_4.add(lvp_3);
		lvp_3.setLayout(null);

		JPanel corp_3 = new JPanel();
		corp_3.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		corp_3.setBounds(3, 89, 94, 44);
		panel_4.add(corp_3);
		corp_3.setLayout(null);
		
		label_correct[4] = new JLabel("\uC815\uB2F5 : 0", SwingConstants.CENTER);
		label_correct[4].setBounds(0, 10, 94, 24);
		corp_3.add(label_correct[4]);

		JPanel panel_33 = new JPanel();
		panel_33.setBorder(new LineBorder(new Color(65, 105, 225), 3, true));
		panel_33.setBounds(0, 469, 200, 139);
		contentPane.add(panel_33);
		panel_33.setLayout(new GridLayout(1, 2));

		JPanel userp_4 = new JPanel();
		panel_33.add(userp_4);
		userp_4.setLayout(null);

		label_pokemon[6] = new JLabel("");
		label_pokemon[6].setBounds(12, 10, 73, 113);
		userp_4.add(label_pokemon[6]);

		JPanel panel_34 = new JPanel();
		panel_34.setBorder(new MatteBorder(0, 3, 0, 0, (Color) new Color(0, 0, 0)));
		panel_33.add(panel_34);
		panel_34.setLayout(null);

		namep_07 = new JPanel();
		namep_07.setBounds(3, 0, 94, 44);
		namep_07.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		panel_34.add(namep_07);
		namep_07.setLayout(null);

		label_name[6] = new JLabel("", JLabel.CENTER);
		label_name[6].setBounds(0, 10, 94, 24);
		label_name[6].setFont(new Font("",Font.PLAIN,11));
		namep_07.add(label_name[6]);

		JPanel lvp_4 = new JPanel();
		lvp_4.setBounds(3, 44, 94, 44);
		lvp_4.setBorder(new MatteBorder(2, 0, 2, 0, (Color) new Color(0, 0, 0)));
		panel_34.add(lvp_4);
		lvp_4.setLayout(null);

		JPanel corp_4 = new JPanel();
		corp_4.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		corp_4.setBounds(3, 89, 94, 44);
		panel_34.add(corp_4);
		corp_4.setLayout(null);
		
		label_correct[6] = new JLabel("\uC815\uB2F5 : 0", SwingConstants.CENTER);
		label_correct[6].setBounds(0, 10, 94, 24);
		corp_4.add(label_correct[6]);

		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(new Color(65, 105, 225), 3, true));
		panel_5.setBounds(808, 48, 200, 139);
		contentPane.add(panel_5);
		panel_5.setLayout(new GridLayout(1, 2));

		JPanel userp_5 = new JPanel();
		panel_5.add(userp_5);
		userp_5.setLayout(null);

		label_pokemon[1] = new JLabel("");
		label_pokemon[1].setBounds(12, 10, 73, 113);
		userp_5.add(label_pokemon[1]);

		JPanel panel_37 = new JPanel();
		panel_37.setBorder(new MatteBorder(0, 3, 0, 0, (Color) new Color(0, 0, 0)));
		panel_5.add(panel_37);
		panel_37.setLayout(null);

		namep_02 = new JPanel();
		namep_02.setBounds(3, 0, 94, 44);
		namep_02.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		panel_37.add(namep_02);
		namep_02.setLayout(null);

		label_name[1] = new JLabel("", JLabel.CENTER);
		label_name[1].setBounds(0, 10, 94, 24);
		label_name[1].setFont(new Font("",Font.PLAIN,11));
		namep_02.add(label_name[1]);

		JPanel lvp_5 = new JPanel();
		lvp_5.setBounds(3, 44, 94, 44);
		lvp_5.setBorder(new MatteBorder(2, 0, 2, 0, (Color) new Color(0, 0, 0)));
		panel_37.add(lvp_5);
		lvp_5.setLayout(null);

		JPanel corp_5 = new JPanel();
		corp_5.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		corp_5.setBounds(3, 89, 94, 44);
		panel_37.add(corp_5);
		corp_5.setLayout(null);
		
		label_correct[1] = new JLabel("\uC815\uB2F5 : 0", SwingConstants.CENTER);
		label_correct[1].setBounds(0, 10, 94, 24);
		corp_5.add(label_correct[1]);

		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(new Color(65, 105, 225), 3, true));
		panel_6.setBounds(808, 188, 200, 139);
		contentPane.add(panel_6);
		panel_6.setLayout(new GridLayout(1, 2));

		JPanel userp_6 = new JPanel();
		panel_6.add(userp_6);
		userp_6.setLayout(null);

		label_pokemon[3] = new JLabel("");
		label_pokemon[3].setBounds(12, 10, 73, 113);
		userp_6.add(label_pokemon[3]);

		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new MatteBorder(0, 3, 0, 0, (Color) new Color(0, 0, 0)));
		panel_6.add(panel_7);
		panel_7.setLayout(new GridLayout(3, 0, 0, 0));

		namep_04 = new JPanel();
		namep_04.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		panel_7.add(namep_04);
		namep_04.setLayout(null);

		label_name[3] = new JLabel("", JLabel.CENTER);
		label_name[3].setBounds(0, 10, 94, 24);
		label_name[3].setFont(new Font("",Font.PLAIN,11));
		namep_04.add(label_name[3]);

		JPanel lvp_6 = new JPanel();
		lvp_6.setBorder(new MatteBorder(2, 0, 2, 0, (Color) new Color(0, 0, 0)));
		panel_7.add(lvp_6);
		lvp_6.setLayout(null);

		JPanel corp_6 = new JPanel();
		corp_6.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		panel_7.add(corp_6);
		corp_6.setLayout(null);
		
		label_correct[3] = new JLabel("\uC815\uB2F5 : 0", SwingConstants.CENTER);
		label_correct[3].setBounds(0, 10, 94, 24);
		corp_6.add(label_correct[3]);

		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new LineBorder(new Color(65, 105, 225), 3, true));
		panel_10.setBounds(808, 329, 200, 139);
		contentPane.add(panel_10);
		panel_10.setLayout(new GridLayout(1, 2));

		JPanel userp_7 = new JPanel();
		panel_10.add(userp_7);
		userp_7.setLayout(null);

		label_pokemon[5] = new JLabel("");
		label_pokemon[5].setBounds(12, 10, 73, 113);
		userp_7.add(label_pokemon[5]);

		JPanel panel_12 = new JPanel();
		panel_12.setBorder(new MatteBorder(0, 3, 0, 0, (Color) new Color(0, 0, 0)));
		panel_10.add(panel_12);
		panel_12.setLayout(new GridLayout(3, 0, 0, 0));

		namep_06 = new JPanel();
		namep_06.setLayout(null);
		namep_06.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		panel_12.add(namep_06);

		label_name[5] = new JLabel("", JLabel.CENTER);
		label_name[5].setBounds(0, 10, 94, 24);
		label_name[5].setFont(new Font("",Font.PLAIN,11));
		namep_06.add(label_name[5]);

		JPanel lvp_7 = new JPanel();
		lvp_7.setLayout(null);
		lvp_7.setBorder(new MatteBorder(2, 0, 2, 0, (Color) new Color(0, 0, 0)));
		panel_12.add(lvp_7);

		JPanel corp_7 = new JPanel();
		corp_7.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		panel_12.add(corp_7);
		corp_7.setLayout(null);
		
		label_correct[5] = new JLabel("\uC815\uB2F5 : 0", SwingConstants.CENTER);
		label_correct[5].setBounds(0, 10, 94, 24);
		corp_7.add(label_correct[5]);

		JPanel panel_16 = new JPanel();
		panel_16.setBorder(new LineBorder(new Color(65, 105, 225), 3, true));
		panel_16.setBounds(808, 469, 200, 139);
		contentPane.add(panel_16);
		panel_16.setLayout(new GridLayout(1, 2));

		JPanel userp_8 = new JPanel();
		panel_16.add(userp_8);
		userp_8.setLayout(null);

		label_pokemon[7] = new JLabel("");
		label_pokemon[7].setBounds(12, 10, 73, 113);
		userp_8.add(label_pokemon[7]);

		JPanel panel_24 = new JPanel();
		panel_24.setBorder(new MatteBorder(0, 3, 0, 0, (Color) new Color(0, 0, 0)));
		panel_16.add(panel_24);
		panel_24.setLayout(new GridLayout(3, 0, 0, 0));

		namep_08 = new JPanel();
		namep_08.setLayout(null);
		namep_08.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		panel_24.add(namep_08);

		label_name[7] = new JLabel("", JLabel.CENTER);
		label_name[7].setBounds(0, 10, 94, 24);
		label_name[7].setFont(new Font("",Font.PLAIN,11));
		namep_08.add(label_name[7]);

		JPanel lvp_8 = new JPanel();
		lvp_8.setLayout(null);
		lvp_8.setBorder(new MatteBorder(2, 0, 2, 0, (Color) new Color(0, 0, 0)));
		panel_24.add(lvp_8);

		JPanel corp_8 = new JPanel();
		corp_8.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		panel_24.add(corp_8);
		corp_8.setLayout(null);
		
		label_correct[7] = new JLabel("\uC815\uB2F5 : 0", SwingConstants.CENTER);
		label_correct[7].setBounds(0, 10, 94, 24);
		corp_8.add(label_correct[7]);

		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(null, "\uAD75\uAE30", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_8.setBounds(218, 667, 131, 62);
		contentPane.add(panel_8);
		panel_8.setLayout(null);

		upbtn = new JButton("");
		upbtn.setBounds(35, 10, 42, 45);
		panel_8.add(upbtn);
		upbtn.setIcon(new ImageIcon(this.getClass().getResource("/color/penplus.png")));
		upbtn.addActionListener(this);

		downbtn = new JButton("");
		downbtn.setBounds(79, 10, 42, 45);
		panel_8.add(downbtn);
		downbtn.setIcon(new ImageIcon(this.getClass().getResource("/color/penminus.png")));

		label_penSize = new JLabel("3", JLabel.CENTER);
		label_penSize.setBounds(0, 22, 32, 27);
		panel_8.add(label_penSize);
		downbtn.addActionListener(this);

		textField_chat.setDocument(new BoundDocument(60, textField_chat));
		
		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\uBB38\uC81C", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_9.setBounds(361, 670, 156, 59);
		contentPane.add(panel_9);
		panel_9.setLayout(null);
		
		label_quiz = new JLabel("",JLabel.CENTER);
		label_quiz.setBounds(12, 22, 132, 27);
		panel_9.add(label_quiz);
		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new TitledBorder(null, "\uB0A8\uC740\uC2DC\uAC04", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_11.setBounds(28, 667, 156, 68);
		contentPane.add(panel_11);
		panel_11.setLayout(null);
		
		label_time = new JLabel("00:00",JLabel.CENTER);
		label_time.setFont(new Font("",Font.BOLD,20));
		label_time.setBounds(12, 21, 132, 37);
		panel_11.add(label_time);
		
		JButton btnNewButton = new JButton("\uB300\uAE30\uC2E4\uB85C");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				room_telecom.roomExit();
				room_telecom.getWaiting_frame().setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("굴림", Font.BOLD, 15));
		btnNewButton.setBounds(877, 698, 131, 31);
		contentPane.add(btnNewButton);
		System.out.println("비지블");
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == buttonBlack) {
			canvas.setColor(Color.BLACK);
			Toolkit tk = Toolkit.getDefaultToolkit();
			Image img = tk.getImage("img/pen/black.png");
			Cursor myCursor = tk.createCustomCursor(img, new Point(0, 0), "dynamite stick");
			setCursor(myCursor);
		}
		if (e.getSource() == buttonRed) {
			canvas.setColor(Color.RED);
			System.out.println("레드누름1");
			Toolkit tk = Toolkit.getDefaultToolkit();
			Image img = tk.getImage("img/pen/red.png");
			Cursor myCursor = tk.createCustomCursor(img, new Point(0, 0), "dynamite stick");
			setCursor(myCursor);
		}
		if (e.getSource() == buttonBlue) {
			System.out.println("블루누름1");
			canvas.setColor(Color.BLUE);
			Toolkit tk = Toolkit.getDefaultToolkit();
			Image img = tk.getImage("img/pen/blue.png");
			Cursor myCursor = tk.createCustomCursor(img, new Point(0, 0), "dynamite stick");
			setCursor(myCursor);
			System.out.println("블루누름");
		}
		if (e.getSource() == buttonGreen) {
			canvas.setColor(Color.GREEN);
			Toolkit tk = Toolkit.getDefaultToolkit();
			Image img = tk.getImage("img/pen/green.png");
			Cursor myCursor = tk.createCustomCursor(img, new Point(0, 0), "dynamite stick");
			setCursor(myCursor);
		}
		if (e.getSource() == buttonYellow) {
			canvas.setColor(Color.YELLOW);
			Toolkit tk = Toolkit.getDefaultToolkit();
			Image img = tk.getImage("img/pen/yellow.png");
			Cursor myCursor = tk.createCustomCursor(img, new Point(0, 0), "dynamite stick");
			setCursor(myCursor);
		}
		if (e.getSource() == upbtn) {
			canvas.penSizeUp();
			label_penSize.setText(canvas.getPen() + "");
		}
		if (e.getSource() == downbtn) {
			canvas.penSizeDown();
			label_penSize.setText(canvas.getPen() + "");
		}
		// 지우개
		if (e.getSource() == buttonEraser) {
			canvas.setColor(Color.WHITE);
			Toolkit tk = Toolkit.getDefaultToolkit();
			Image img = tk.getImage("img/pen/eraser.png");
			Cursor myCursor = tk.createCustomCursor(img, new Point(15, 15), "dynamite stick");
			setCursor(myCursor);

		}
		if (e.getSource() == btremove) {
			canvas.remove();
			room_telecom.sendPaint(new DrawListData(canvas.getArr()));
		}
	}

	public void refreshArea(List<UserVO> members) {
		int i = 0;
		System.out.println("리프레쉬");
		for (i = 0; i < members.size(); i++) {
			label_name[i].setText(members.get(i).getNickName());
			label_correct[i].setText("정답 : "+members.get(i).getCorrectNum());
			System.out.println(members.get(i));
			/*if (members.get(i).getUser_pokemon() == 1) {
				label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/pika.gif")));
			} else {
				label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/pairi.gif")));
			}*/
	         switch(members.get(i).getUser_pokemon()){
	         case 0: label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/kobugi.gif")));
	        	 break;
	         case 1: label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/pairi.gif")));
	        	 break;
	         case 2: label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/pika.gif")));
	        	 break;
	         case 3: label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/rumi.gif")));
	        	 break;
	         case 4: label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/ssi.gif")));
	        	 break;
	         case 5: label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/purin.gif")));
	        	 break;
	         case 6: label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/99.gif")));
	        	 break;
	         case 7: label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/anelka.jpg")));
	        	 break;
	         case 8: label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/star.gif")));
	        	 break;
	         case 9:label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/coil.gif")));
	        	 break;
	         case 10: label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/dugi.gif")));
	        	 break;
	         case 11: label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/bbul.gif")));
	        	 break;
	         case 12: label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/modapi.gif")));
	        	 break;
	         case 13: label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/muntari.png")));
	        	 break;
	         case 14:label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/cat.gif")));
	        	 break;
	         case 15: label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/digda.gif")));
	        	 break;
	         case 16: label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/gadi.gif")));
	        	 break;
	         case 17: label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/keisi.gif")));
	        	 break;
	        	 default :
	        		 label_pokemon[i].setIcon(new ImageIcon(this.getClass().getResource("/pokemon/egg.gif")));
	         }
		}
		for (; i < 8; i++) {
			label_name[i].setText("");
			label_pokemon[i].setIcon(null);
			label_correct[i].setText("정답 : "+0);
		}
		this.repaint();
	}

	public void refreshTime(TimeData time) {
		label_time.setText(time.getMinute()+" : "+time.getSecond());
	}

	public void testerSetting() {
		label_quiz.setText("");
		textField_chat.setEnabled(true);
		buttonBlack.setEnabled(false);
		buttonBlue.setEnabled(false);
		buttonEraser.setEnabled(false);
		buttonGreen.setEnabled(false);
		buttonRed.setEnabled(false);
		buttonYellow.setEnabled(false);
		upbtn.setEnabled(false);
		downbtn.setEnabled(false);
		btremove.setEnabled(false);
		canvas.setEnabled(false);
		repaint();
	}

	public void examinerSetting(String answer) {
		label_quiz.setText(answer);
		textField_chat.setEnabled(false);
		buttonBlack.setEnabled(true);
		buttonBlue.setEnabled(true);
		buttonEraser.setEnabled(true);
		buttonGreen.setEnabled(true);
		buttonRed.setEnabled(true);
		buttonYellow.setEnabled(true);
		upbtn.setEnabled(true);
		downbtn.setEnabled(true);
		btremove.setEnabled(true);
		canvas.setEnabled(true);
		repaint();
	}
}
