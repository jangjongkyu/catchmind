package com.catchmind.pro.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.UUID;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.catchmind.pro.client.Client_Launcher;
import com.catchmind.pro.dao.UserDAO;
import com.catchmind.pro.util.BoundDocument;
import com.catchmind.pro.util.MailExam;
import com.catchmind.pro.vo.UserVO;

class MyImage extends Canvas {

	private Image img = Toolkit.getDefaultToolkit().getImage("");

	public void setImage(Image img) {
		this.img = img;
	}

	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}

public class Window_Join extends JFrame implements ItemListener, CaretListener, DocumentListener, ActionListener {

	private JPanel contentPane;
	private JTextField textField_id;
	private JPasswordField passwordField1;
	private JPasswordField passwordField2;
	private JTextField textField_eamil1;
	private JTextField textField_email2;
	private JTextField textField_nickName;
	private JTextField textField_personName;
	private String uuid;
	private Catch_View chview;
	private JComboBox comboBox_year = null;
	private JComboBox comboBox_month = null;
	private JComboBox comboBox_day = null;

	private UserDAO dao = null;
	private JTextField textField_emailConfirm;

	private boolean birth = false;
	private JButton btn_join;
	private JButton btn_confirmEmail;
	private JButton bt_select;

	private boolean confirm_id = false;
	private boolean confirm_pw = false;
	private boolean confirm_email = false;
	private boolean confirm_nickName = false;
	private JCheckBox select_char;
	private JCheckBox chckbx_id = null;
	private JCheckBox chckbx_pw = null;
	private JCheckBox chckbx_email = null;
	private JCheckBox chckbx_nickName = null;
	private JLabel lb_select;
	private JButton btn_emailConfirmNumber = null;

	private JLabel lblTt;

	private Thread th = null;

	private JLabel label_emailConfirmTime;
	private Client_Launcher client;
	private Window_Login login_frame;
	private Window_Join join_frame;

	private MyImage mimg = new MyImage();
	private JButton btn_view;
	private JLabel ch_character;
	private int user_pokemon;
	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { Window_Join frame = new
	 * Window_Join(); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */

	/**
	 * Create the frame.
	 */
	private void init() {
		setResizable(false);
		centerLocation(this);
	}

	private void centerLocation(Component component) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		component.setLocation((int) (dimension.getWidth() / 2) - (component.getHeight() / 2),
				(int) (dimension.getHeight() / 2) - (component.getHeight() / 2));
	}

	public Window_Join(Client_Launcher client, Window_Login login_frame) {
		setBounds(100, 100, 384, 540);
		dao = UserDAO.getUserDAO();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.client = client;
		this.login_frame = login_frame;
		this.join_frame = this;
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("회원가입", JLabel.CENTER);
		lblNewLabel.setBounds(143, 0, 86, 36);
		lblNewLabel.setFont(new Font("", Font.BOLD, 20));
		contentPane.add(lblNewLabel);

		JLabel label_id = new JLabel("\uC544\uC774\uB514");
		label_id.setBounds(27, 53, 57, 15);
		contentPane.add(label_id);

		init();

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				login_frame.setVisible(true);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				System.out.println("windowClosed()");
				login_frame.setVisible(true);
			}

		});

		JLabel label_password1 = new JLabel("\uBE44\uBC00\uBC88\uD638");
		label_password1.setBounds(27, 78, 90, 15);
		contentPane.add(label_password1);

		JLabel label_password2 = new JLabel("\uBE44\uBC00\uBC88\uD638\uD655\uC778");
		label_password2.setBounds(27, 105, 90, 15);
		contentPane.add(label_password2);

		JLabel label_email = new JLabel("\uC774\uBA54\uC77C");
		label_email.setBounds(12, 130, 105, 26);
		contentPane.add(label_email);

		JLabel lblNewLabel_1 = new JLabel("\uB2C9\uB124\uC784");
		lblNewLabel_1.setBounds(27, 251, 57, 15);
		contentPane.add(lblNewLabel_1);

		btn_join = new JButton("\uD68C\uC6D0\uAC00\uC785");
		btn_join.setBounds(233, 464, 121, 36);
		btn_join.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String personName = textField_personName.getText();
				if (personName.length() < 2 || personName.length() > 20) {
					JOptionPane.showMessageDialog(null, "이름의 글자수는 2~20 사이만 가능합니다.");
					return;
				}

				if (personName.contains("'") || personName.contains("\"") || personName.contains("#")
						|| personName.contains("!") || personName.contains("+") || personName.contains("-")
						|| personName.contains("*") || personName.contains("/") || personName.contains(" ")
						|| personName.contains("$")) {
					JOptionPane.showMessageDialog(null, "이름에 공백 ' \" # ! + - * / $는 포함시킬수 없습니다.");
					return;
				}

				if (comboBox_day.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "생년월일을 입력해주세요 !!");
					return;
				}

				if (confirm_id && confirm_pw && confirm_email && confirm_nickName && select_char.isSelected()) {
					int action = JOptionPane.showConfirmDialog(null, "현재 정보대로 회원가입을 진행합니까?", "회원가입 확인",
							JOptionPane.YES_NO_OPTION);

					if (action != 0) {
						return;
					}
					System.out.println("가입버튼 : " + user_pokemon);
					client.join(textField_id.getText(), passwordField1.getText(),
							textField_eamil1.getText() + "@" + textField_email2.getText(), textField_nickName.getText(),
							textField_personName.getText(), comboBox_year.getSelectedItem() + "",
							comboBox_month.getSelectedItem() + "", comboBox_day.getSelectedItem() + "", user_pokemon);
					join_frame.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "아이디중복체크 혹은 닉네임중복체크 , 비밀번호일치, 이메일인증, 캐릭터 선택이 확인되지 않았습니다.");
				}
			}
		});
		contentPane.add(btn_join);

		textField_id = new JTextField();
		textField_id.setBounds(114, 50, 126, 21);
		textField_id.setToolTipText("8~15 \uC601\uBB38\uC22B\uC790");
		contentPane.add(textField_id);
		textField_id.setColumns(10);

		JButton btn_idCheck = new JButton("\uC911\uBCF5\uD655\uC778");
		btn_idCheck.setBounds(252, 49, 102, 23);
		btn_idCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_id.getText().length() < 8 || textField_id.getText().length() > 15) {
					JOptionPane.showMessageDialog(null, "아이디는 8글자에서 15글자까지 입력할수 있습니다.");
					return;
				}
				for (int i = 0; i < textField_id.getText().length(); i++) {
					char c = textField_id.getText().charAt(i);
					if (!(c >= 48 && c <= 57 || c >= 65 && c <= 90 || c >= 97 && c <= 122)) {
						confirm_id = false;
						chckbx_id.setSelected(false);
						JOptionPane.showMessageDialog(null, "아이디는 영문 ,숫자만 허용합니다.");
						return;
					}
				}

				UserVO vo = new UserVO();
				vo.setUser_id(textField_id.getText());

				if (dao.checkUserId(vo)) {
					confirm_id = true;
					chckbx_id.setSelected(true);
					JOptionPane.showMessageDialog(null, vo.getUser_id() + " 는 사용가능한 아이디 입니다.");
				} else {
					confirm_id = false;
					chckbx_id.setSelected(false);
					JOptionPane.showMessageDialog(null, vo.getUser_id() + " 는 이미 사용중인 아이디 입니다.");
				}
			}
		});
		contentPane.add(btn_idCheck);

		passwordField1 = new JPasswordField();
		passwordField1.setBounds(114, 78, 126, 18);
		passwordField1.setToolTipText("8~15 \uC601\uBB38\uC22B\uC790");
		contentPane.add(passwordField1);
		passwordField1.addCaretListener(this);

		passwordField2 = new JPasswordField();
		passwordField2.setBounds(114, 103, 126, 18);
		passwordField2.setToolTipText("8~15 \uC601\uBB38\uC22B\uC790");
		contentPane.add(passwordField2);
		passwordField2.addCaretListener(this);

		textField_eamil1 = new JTextField();
		textField_eamil1.setBounds(95, 135, 126, 21);
		textField_eamil1.setToolTipText("\uC2E4\uC81C \uC774\uBA54\uC77C\uC8FC\uC18C\uC785\uB825");
		contentPane.add(textField_eamil1);
		textField_eamil1.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("@");
		lblNewLabel_2.setBounds(222, 138, 23, 15);
		contentPane.add(lblNewLabel_2);

		textField_email2 = new JTextField();
		textField_email2.setBounds(233, 135, 116, 21);
		contentPane.add(textField_email2);
		textField_email2.setColumns(10);

		textField_nickName = new JTextField();
		textField_nickName.setBounds(95, 248, 126, 21);
		textField_nickName.setToolTipText("' \" # ! * \uC81C\uC678\uD55C \uBAA8\uB4E0\uAE00\uC790 3~8\uC790");
		contentPane.add(textField_nickName);
		textField_nickName.setColumns(10);

		JButton btn_nickNameCheck = new JButton("\uC911\uBCF5\uD655\uC778");
		btn_nickNameCheck.setBounds(243, 247, 102, 23);
		btn_nickNameCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_nickName.getText().length() < 3 || textField_nickName.getText().length() > 8) {
					JOptionPane.showMessageDialog(null, "닉네임은 3글자이상 8글자 이하로 입력해주세요.");
					return;
				}

				String nick = textField_nickName.getText();
				if (nick.contains("'") || nick.contains("\"") || nick.contains("#") || nick.contains("!")
						|| nick.contains("+") || nick.contains("-") || nick.contains("*") || nick.contains("/")
						|| nick.contains(" ")) {
					JOptionPane.showMessageDialog(null, "닉네임에 특수문자 공백  ' \" # ! + - * / 는 포함시킬수 없습니다.");
					return;
				}

		/*		for (int i = 0; i < textField_nickName.getText().length(); i++) {
					char c = textField_nickName.getText().charAt(i);
				}*/

				UserVO vo = new UserVO();
				vo.setNickName(textField_nickName.getText());

				if (dao.checkUserNickName(vo)) {
					confirm_nickName = true;
					chckbx_nickName.setSelected(true);
					JOptionPane.showMessageDialog(null, vo.getNickName() + " 는 사용가능한 닉네임 입니다.");
				} else {
					confirm_nickName = false;
					chckbx_nickName.setSelected(false);
					JOptionPane.showMessageDialog(null, vo.getNickName() + " 는 이미 사용중인 닉네임 입니다.");
				}

			}
		});
		contentPane.add(btn_nickNameCheck);

		JPanel panel_personInfo = new JPanel();
		panel_personInfo.setBounds(12, 276, 344, 100);
		panel_personInfo.setToolTipText(
				"\uC544\uC774\uB514,\uBE44\uBC00\uBC88\uD638 \uBD84\uC2E4\uC2DC \uD544\uC694\uD55C \uB0B4\uC5ED\uC774\uBBC0\uB85C \uAC00\uB2A5\uD55C\uD55C \uC78A\uD600\uC9C0\uC9C0\uC54A\uC744 \uC2E4\uBA85,\uC2E4\uC81C\uC0DD\uB144\uC6D4\uC77C\uC744 \uC785\uB825\uD558\uC2DC\uAE38 \uCD94\uCC9C\uB4DC\uB9BD\uB2C8\uB2E4.");
		panel_personInfo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"\uC720\uC800\uC815\uBCF4", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panel_personInfo);
		panel_personInfo.setLayout(null);

		JLabel label_birthday = new JLabel("\uC0DD\uB144\uC6D4\uC77C");
		label_birthday.setBounds(12, 64, 57, 15);
		panel_personInfo.add(label_birthday);

		JLabel lblid = new JLabel("\uC774\uB984");
		lblid.setBounds(12, 27, 73, 15);
		panel_personInfo.add(lblid);

		textField_personName = new JTextField();
		textField_personName.setToolTipText("\uACF5\uBC31 ' \\\" # ! + - * / $ \uB97C \uC81C\uC678\uD558\uBA70, \uAE00\uC790\uC218\uC81C\uD55C 2~20");
		textField_personName.setBounds(112, 24, 116, 21);
		panel_personInfo.add(textField_personName);
		textField_personName.setColumns(10);

		comboBox_year = new JComboBox();
		comboBox_year.setBounds(68, 61, 93, 21);
		panel_personInfo.add(comboBox_year);
		Calendar cal = Calendar.getInstance();
		for (int i = cal.get(Calendar.YEAR); i >= 1900; i--) {
			comboBox_year.addItem(i);
		}
		comboBox_year.addItemListener(this);

		JLabel label = new JLabel("\uB144");
		label.setBounds(163, 64, 23, 15);
		panel_personInfo.add(label);

		comboBox_month = new JComboBox();
		comboBox_month.setBounds(180, 61, 48, 21);
		panel_personInfo.add(comboBox_month);
		for (int i = 1; i <= 12; i++) {
			comboBox_month.addItem(i);
		}
		comboBox_month.addItemListener(this);

		JLabel label_1 = new JLabel("\uC6D4");
		label_1.setBounds(230, 64, 23, 15);
		panel_personInfo.add(label_1);

		comboBox_day = new JComboBox();
		comboBox_day.setBounds(250, 61, 48, 21);
		panel_personInfo.add(comboBox_day);

		JLabel label_2 = new JLabel("\uC77C");
		label_2.setBounds(298, 64, 23, 15);
		panel_personInfo.add(label_2);

		btn_confirmEmail = new JButton("\uC778\uC99D\uBC88\uD638\uBC1C\uC1A1");
		btn_confirmEmail.setBounds(232, 163, 117, 23);
		btn_confirmEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MailExam mail = new MailExam();
				String address = textField_eamil1.getText() + "@" + textField_email2.getText();
				uuid = UUID.randomUUID().toString().substring(0, 6);
				boolean ok = mail.mailJoinConfirm(address, uuid);
				if(!ok){
					return;
				}
				
				btn_emailConfirmNumber.setEnabled(true);
				if (th != null && th.isAlive()) {
					th.stop();
				}
				th = new Thread() {
					int minute = 2;
					int second = 60;

					@Override
					public void run() {
						while (true) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if (second == 0) {
								if (minute == 0) {
									btn_emailConfirmNumber.setEnabled(false);
									label_emailConfirmTime.setText("인증번호를 재전송 해주세요.");
									break;
								} else {
									minute--;
									second = 60;
								}
							}
							second--;
							label_emailConfirmTime.setText("제한시간 : " + minute + " : " + second);
						}
					}
				};
				th.start();
			}
		});
		contentPane.add(btn_confirmEmail);

		textField_emailConfirm = new JTextField();
		textField_emailConfirm.setBounds(114, 197, 116, 21);
		textField_emailConfirm.setToolTipText("6\uC790\uB9AC");
		contentPane.add(textField_emailConfirm);
		textField_emailConfirm.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("\uC778\uC99D\uBC88\uD6386\uC790\uB9AC");
		lblNewLabel_3.setBounds(27, 200, 90, 15);
		contentPane.add(lblNewLabel_3);

		btn_emailConfirmNumber = new JButton("\uC778\uC99D\uD655\uC778");
		btn_emailConfirmNumber.setBounds(242, 196, 97, 23);
		btn_emailConfirmNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_emailConfirm.getText().equals(uuid)) {
					JOptionPane.showMessageDialog(null, "인증완료");
					label_emailConfirmTime.setForeground(Color.BLUE);
					th.stop();
					th = null;
					label_emailConfirmTime.setText("[ 이메일 인증완료 ]");
					btn_confirmEmail.setEnabled(false);
					btn_emailConfirmNumber.setEnabled(false);
					textField_emailConfirm.setEditable(false);
					textField_eamil1.setEnabled(false);
					textField_email2.setEnabled(false);

					confirm_email = true;
					chckbx_email.setSelected(true);

				} else {
					JOptionPane.showMessageDialog(null, "인증번호가 일치하지 않습니다.");
				}
			}
		});
		btn_emailConfirmNumber.setEnabled(false);
		contentPane.add(btn_emailConfirmNumber);

		lblTt = new JLabel();
		lblTt.setBounds(252, 105, 102, 15);
		lblTt.setForeground(Color.BLACK);
		contentPane.add(lblTt);

		chckbx_id = new JCheckBox("");
		chckbx_id.setBounds(0, 48, 23, 23);
		chckbx_id.setEnabled(false);
		contentPane.add(chckbx_id);

		chckbx_pw = new JCheckBox("");
		chckbx_pw.setBounds(0, 73, 23, 23);
		chckbx_pw.setEnabled(false);
		contentPane.add(chckbx_pw);

		chckbx_email = new JCheckBox("");
		chckbx_email.setBounds(0, 196, 23, 23);
		chckbx_email.setEnabled(false);
		contentPane.add(chckbx_email);

		chckbx_nickName = new JCheckBox("");
		chckbx_nickName.setBounds(0, 247, 23, 23);
		chckbx_nickName.setEnabled(false);
		contentPane.add(chckbx_nickName);

		label_emailConfirmTime = new JLabel("");
		label_emailConfirmTime.setBounds(83, 223, 194, 15);
		label_emailConfirmTime.setForeground(Color.RED);
		contentPane.add(label_emailConfirmTime);

		bt_select = new JButton("캐릭터 뽑기");
		bt_select.setBounds(233, 432, 121, 23);
		contentPane.add(bt_select);

		lb_select = new JLabel("");
		lb_select.setBounds(27, 475, 202, 14);
		contentPane.add(lb_select);

		select_char = new JCheckBox("\uCE90\uB9AD\uD130 \uACB0\uC815");
		select_char.setBounds(257, 380, 97, 23);
		select_char.setEnabled(false);
		contentPane.add(select_char);
		select_char.addActionListener(this);
		/*
		 * if(select_char.isSelected()){ bt_select.setEnabled(false); }else{
		 * bt_select.setEnabled(true); }
		 */

		ch_character = new JLabel();
		ch_character.setBounds(27, 386, 75, 76);
		contentPane.add(ch_character);

		btn_view = new JButton("\uCE90\uB9AD\uD130 \uBCF4\uAE30");
		btn_view.setBounds(233, 409, 121, 23);
		contentPane.add(btn_view);
		btn_view.addActionListener(this);

		setStringLengthLimit();
		textField_id.getDocument().addDocumentListener(this);
		textField_nickName.getDocument().addDocumentListener(this);
		passwordField1.getDocument().addDocumentListener(this);
		passwordField2.getDocument().addDocumentListener(this);
		textField_personName.getDocument().addDocumentListener(this);
		bt_select.addActionListener(this);
		this.setVisible(true);

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == comboBox_year) {
			comboBox_day.removeAllItems();
			comboBox_month.setSelectedIndex(0);
		}

		if (e.getSource() == comboBox_month) {
			// birth = true;
			if ((int) ((comboBox_month.getSelectedItem())) == 1 || (int) ((comboBox_month.getSelectedItem())) == 3
					|| (int) ((comboBox_month.getSelectedItem())) == 5
					|| (int) ((comboBox_month.getSelectedItem())) == 7
					|| (int) ((comboBox_month.getSelectedItem())) == 8
					|| (int) ((comboBox_month.getSelectedItem())) == 10
					|| (int) ((comboBox_month.getSelectedItem())) == 12) {
				comboBox_day.removeAllItems();
				for (int i = 1; i <= 31; i++) {
					comboBox_day.addItem(i);
				}
			} else if ((int) (comboBox_month.getSelectedItem()) == 2) {
				comboBox_day.removeAllItems();
				if ((int) (comboBox_year.getSelectedItem()) % 4 == 0) {
					for (int i = 1; i <= 29; i++) {
						comboBox_day.addItem(i);
					}
				} else {
					for (int i = 1; i <= 28; i++) {
						comboBox_day.addItem(i);
					}
				}
			} else {
				comboBox_day.removeAllItems();
				for (int i = 1; i <= 30; i++) {
					comboBox_day.addItem(i);
				}
			}
		}
	}

	@Override
	public void caretUpdate(CaretEvent e) {

	}

	private void setStringLengthLimit() {
		// id제한
		textField_id.setDocument(new BoundDocument(15, textField_id));
		// password2제한
		passwordField1.setDocument(new BoundDocument(15, passwordField1));
		// password2제한
		passwordField2.setDocument(new BoundDocument(15, passwordField2));
		// 이메일인증 제한
		textField_emailConfirm.setDocument(new BoundDocument(6, textField_emailConfirm));
		// 닉네임 제한
		textField_nickName.setDocument(new BoundDocument(16, textField_nickName));
		// 실명 제한
		textField_personName.setDocument(new BoundDocument(20, textField_personName));
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		textChange(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		textChange(e);
	}

	public void textChange(DocumentEvent e) {
		if (e.getDocument() == passwordField1.getDocument() || e.getDocument() == passwordField2.getDocument()) {
			if (passwordField1.getText().equals(passwordField2.getText())) {
				for (int i = 0; i < passwordField1.getText().length(); i++) {
					char c = passwordField1.getText().charAt(i);
					if (!(c >= 48 && c <= 57 || c >= 65 && c <= 90 || c >= 97 && c <= 122)) {
						confirm_pw = false;
						chckbx_pw.setSelected(false);
						lblTt.setForeground(Color.RED);
						lblTt.setText("영문숫자만 가능");
						return;
					}
				}

				if (passwordField1.getText().length() >= 8 && passwordField1.getText().length() <= 15) {
					confirm_pw = true;
					chckbx_pw.setSelected(true);
					lblTt.setForeground(Color.BLACK);
					lblTt.setText("패스워드 일치");
				} else {
					confirm_pw = false;
					chckbx_pw.setSelected(false);
					lblTt.setForeground(Color.RED);
					lblTt.setText("글자수 부적합");
				}
			} else {
				confirm_pw = false;
				chckbx_pw.setSelected(false);
				lblTt.setForeground(Color.RED);
				lblTt.setText("패스워드 불일치");
			}
		} else if (e.getDocument() == textField_id.getDocument()) {
			confirm_id = false;
			chckbx_id.setSelected(false);
		} else if (e.getDocument() == textField_nickName.getDocument()) {
			confirm_nickName = false;
			chckbx_nickName.setSelected(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (select_char.isSelected()) {
			bt_select.setEnabled(false);
		} else {
			bt_select.setEnabled(true);
		}

		if (e.getSource() == btn_view) {
			chview = new Catch_View();
			chview.setVisible(true);
		}

		if (e.getSource() == bt_select) {
			String[] character = new String[18];
			character[0] = "/pokemon/kobugi.gif";
			character[1] = "/pokemon/pairi.gif";
			character[2] = "/pokemon/pika.gif";
			character[3] = "/pokemon/rumi.gif";
			character[4] = "/pokemon/ssi.gif";
			character[5] = "/pokemon/purin.gif";
			character[6] = "/pokemon/99.gif";
			character[7] = "/pokemon/anelka.jpg";
			character[8] = "/pokemon/star.gif";
			character[9] = "/pokemon/coil.gif";
			character[10] = "/pokemon/dugi.gif";
			character[11] = "/pokemon/bbul.gif";
			character[12] = "/pokemon/modapi.gif";
			character[13] = "/pokemon/muntari.png";
			character[14] = "/pokemon/cat.gif";
			character[15] = "/pokemon/digda.gif";
			character[16] = "/pokemon/gadi.gif";
			character[17] = "/pokemon/keisi.gif";

			user_pokemon = (int) (Math.random() * 18);
			String pokemon = character[user_pokemon];

			System.out.println("actionPer");
			select_char.setEnabled(true);
			// mimg.setImage(Toolkit.getDefaultToolkit().getImage(character[sel]));
			ch_character.setIcon(new ImageIcon(this.getClass().getResource(pokemon)));

			System.out.println("셀렉터 : " + user_pokemon);
			switch (user_pokemon) {
			case 0:
				lb_select.setText("꼬부기가 소환되었습니다.");
				break;
			case 1:
				lb_select.setText("파이리가 소환되었습니다.");
				break;
			case 2:
				lb_select.setText("피카츄가 소환되었습니다.");
				break;
			case 3:
				lb_select.setText("내루미가 소환되었습니다.");
				break;
			case 4:
				lb_select.setText("이상해씨가 소환되었습니다.");
				break;
			case 5:
				lb_select.setText("푸린이 소환되었습니다.");
				break;
			case 6:
				lb_select.setText("구구 소환되었습니다.");
				break;
			case 7:
				lb_select.setText("아넬카 소환되었습니다.");
				break;
			case 8:
				lb_select.setText("별가사리 소환되었습니다.");
				break;
			case 9:
				lb_select.setText("코일이 소환되었습니다.");
				break;
			case 10:
				lb_select.setText("모래두지가 소환되었습니다.");
				break;
			case 11:
				lb_select.setText("뿔충이가 소환되었습니다.");
				break;
			case 12:
				lb_select.setText("모다피가 소환되었습니다.");
				break;
			case 13:
				lb_select.setText("문타리가 소환되었습니다.");
				break;
			case 14:
				lb_select.setText("냐옹이가 소환되었습니다.");
				break;
			case 15:
				lb_select.setText("디그다가 소환되었습니다.");
				break;
			case 16:
				lb_select.setText("가디가 소환되었습니다.");
				break;
			case 17:
				lb_select.setText("케이시가 소환되었습니다.");
				break;
			default:
			}
		}

	}
}