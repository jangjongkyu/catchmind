package com.catchmind.pro.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.catchmind.pro.client.Client_Telecom;
import com.catchmind.pro.dao.UserDAO;
import com.catchmind.pro.util.BoundDocument;
import com.catchmind.pro.util.Catch_Protocol;
import com.catchmind.pro.util.MailExam;
import com.catchmind.pro.vo.UserVO;
import java.awt.Color;

public class Catch_memberedit extends JDialog implements ActionListener, DocumentListener {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField passwordField1;
	private JPasswordField passwordField2;
	private JTextField textField_email1;
	private JTextField textField_nickName;
	private JButton btn_email;
	private JButton okbtn;
	private JButton canclebtn;
	private JButton bt_nickConfirm;
	private JLabel label;
	private JCheckBox cb_pw;
	private JCheckBox cb_email;
	private JCheckBox cb_nick;
	private JButton btn_emailConfirm;
	private JDialog edlg = new JDialog(this, "메일", true);
	private JDialog edlg_2 = new JDialog(this, "인증", true);
	private JDialog edlg_3 = new JDialog(this, "인증실패", true);
	private JDialog edlg_4 = new JDialog(this, "확인", true);
	private JDialog edlg_5 = new JDialog(this, "확인", true);
	private String[] mails = { "선택", "naver.com", "daum.net", "nate.com", "google.co.kr", "hanmail.com",
			"yahoo.co.kr" };
	MailExam me = new MailExam();
	private JTextField tf_emailcomfirm;
	private JButton edlgbut;
	private JButton edlgbut_2;
	private JButton edlgbut_3;
	private JButton edlgbut_4 = new JButton("확인");
	private JButton edlgbut_5;
	private JLabel edlglbl;
	private JLabel edlglbl_2;
	private JLabel edlglbl_3;
	private JLabel edlglbl_4;// = new JLabel("인증번호가 없습니다.",Label.CENTER);
	private JLabel edlglbl_5;
	private JPanel dp = new JPanel();
	private JPanel dp_2 = new JPanel();
	private JPanel dp_3 = new JPanel();
	private JPanel dp_4 = new JPanel();
	private JPanel dp_5 = new JPanel();
	private UserDAO dao = null;

	private JLabel lblTt;
	private Client_Telecom telecom;

	private boolean confirm_pw = false;
	private boolean confirm_email = false;
	private boolean confirm_nickName = false;

	public Catch_memberedit(Client_Telecom telecom) {
		this.dao = UserDAO.getUserDAO();
		this.telecom = telecom;
		setBounds(100, 100, 439, 424);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblmain = new JLabel("회원수정");
		lblmain.setFont(new Font("굴림", Font.BOLD, 16));
		lblmain.setBounds(157, 10, 68, 21);
		contentPanel.add(lblmain);

		JLabel lbpwd = new JLabel("패스워드");
		lbpwd.setFont(new Font("굴림", Font.PLAIN, 13));
		lbpwd.setBounds(97, 64, 57, 15);
		contentPanel.add(lbpwd);

		JLabel lbpwd_2 = new JLabel("패스워드확인");
		lbpwd_2.setFont(new Font("굴림", Font.PLAIN, 13));
		lbpwd_2.setBounds(97, 99, 78, 15);
		contentPanel.add(lbpwd_2);

		passwordField1 = new JPasswordField();
		passwordField1.setBounds(185, 61, 116, 21);
		contentPanel.add(passwordField1);
		passwordField1.setColumns(10);
		passwordField1.setEnabled(false);

		passwordField2 = new JPasswordField();
		passwordField2.setColumns(10);
		passwordField2.setBounds(185, 96, 116, 21);
		contentPanel.add(passwordField2);
		passwordField2.setEnabled(false);

		label = new JLabel("");
		label.setFont(new Font("굴림", Font.PLAIN, 10));
		label.setBounds(185, 137, 154, 15);
		contentPanel.add(label);

		JLabel lbemail = new JLabel("이메일");
		lbemail.setFont(new Font("굴림", Font.PLAIN, 13));
		lbemail.setBounds(97, 187, 78, 15);
		contentPanel.add(lbemail);

		textField_email1 = new JTextField();
		textField_email1.setColumns(10);
		textField_email1.setBounds(158, 184, 105, 21);
		contentPanel.add(textField_email1);
		textField_email1.setEnabled(false);

		JLabel lbemail_2 = new JLabel("@");
		lbemail_2.setFont(new Font("굴림", Font.PLAIN, 13));
		lbemail_2.setBounds(275, 187, 21, 15);
		contentPanel.add(lbemail_2);

		btn_email = new JButton("인증");
		btn_email.setBounds(298, 207, 79, 23);
		contentPanel.add(btn_email);
		btn_email.addActionListener(this);
		btn_email.setEnabled(false);

		JLabel lbnick = new JLabel("닉네임");
		lbnick.setFont(new Font("굴림", Font.PLAIN, 13));
		lbnick.setBounds(97, 310, 57, 15);
		contentPanel.add(lbnick);

		textField_nickName = new JTextField();
		textField_nickName.setColumns(10);
		textField_nickName.setBounds(158, 307, 101, 21);
		contentPanel.add(textField_nickName);
		textField_nickName.setEnabled(false);

		bt_nickConfirm = new JButton("\uC911\uBCF5\uD655\uC778");
		bt_nickConfirm.addActionListener(new ActionListener() {
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

				UserVO vo = new UserVO();
				vo.setNickName(textField_nickName.getText());

				if (dao.checkUserNickName(vo)) {
					confirm_nickName = true;
					JOptionPane.showMessageDialog(null, vo.getNickName() + " 는 사용가능한 닉네임 입니다.");
				} else {
					confirm_nickName = false;
					JOptionPane.showMessageDialog(null, vo.getNickName() + " 는 이미 사용중인 닉네임 입니다.");
				}
			}
		});
		bt_nickConfirm.setBounds(277, 306, 88, 23);
		contentPanel.add(bt_nickConfirm);
		bt_nickConfirm.setEnabled(false);

		cb_pw = new JCheckBox("");
		cb_pw.setBounds(63, 61, 26, 23);
		contentPanel.add(cb_pw);
		cb_pw.setEnabled(true);
		cb_pw.addActionListener(this);

		cb_email = new JCheckBox("");
		cb_email.setBounds(63, 183, 26, 23);
		contentPanel.add(cb_email);
		cb_email.setEnabled(true);
		cb_email.addActionListener(this);

		cb_nick = new JCheckBox("");
		cb_nick.setBounds(63, 306, 26, 23);
		contentPanel.add(cb_nick);
		cb_nick.setEnabled(true);
		cb_nick.addActionListener(this);

		JLabel lbemail2 = new JLabel("인증번호");
		lbemail2.setFont(new Font("굴림", Font.PLAIN, 13));
		lbemail2.setBounds(97, 257, 78, 15);
		contentPanel.add(lbemail2);

		tf_emailcomfirm = new JTextField();
		tf_emailcomfirm.setColumns(10);
		tf_emailcomfirm.setBounds(185, 254, 78, 21);
		contentPanel.add(tf_emailcomfirm);
		tf_emailcomfirm.setEnabled(false);

		btn_emailConfirm = new JButton("\uC778\uC99D\uD655\uC778");
		btn_emailConfirm.setBounds(277, 253, 86, 23);
		contentPanel.add(btn_emailConfirm);
		btn_emailConfirm.addActionListener(this);
		btn_emailConfirm.setEnabled(false);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okbtn = new JButton("완료");
				okbtn.setActionCommand("OK");
				buttonPane.add(okbtn);
				getRootPane().setDefaultButton(okbtn);
				okbtn.setEnabled(false);
				okbtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						
							if (cb_pw.isSelected() && !confirm_pw) {
								JOptionPane.showMessageDialog(null, "패스워드 입력이 올바르지 않습니다.");
								return;
							}
							if (cb_nick.isSelected() && !confirm_nickName) {
								JOptionPane.showMessageDialog(null, "닉네임 중복미확인 ,또는 입력이 올바르지 않습니다. ");
								return;
							}
							if (cb_email.isSelected() && !confirm_email) {
								JOptionPane.showMessageDialog(null, "이메일 인증을 진행해주세요.");
								return;
							}

							UserVO vo = telecom.getUserInfo();
							if(cb_pw.isSelected()){
								vo.setUser_password(passwordField1.getText());
							}else{
								vo.setUser_password(Catch_Protocol.STATE_PASSWORD_EMPTY+"");
							}
							
							if(cb_nick.isSelected()){
								vo.setNickName(textField_nickName.getText());
							}
							if(cb_email.isSelected()){
								vo.setEmail(textField_email1.getText()+"@"+textField_email2.getText());
							}
						
							dao.modifyUser(vo);
							setVisible(false);
							dispose();
					}
				});
			}
			{
				canclebtn = new JButton("취소");
				canclebtn.setActionCommand("Cancel");
				buttonPane.add(canclebtn);
				canclebtn.addActionListener(this);
			}
		}

		edlg_2 = new JDialog(this, "인증", true);
		edlgbut_2 = new JButton("확인");
		edlglbl_2 = new JLabel("인증번호가 일치합니다.");
		edlg_2.getContentPane().add(dp_2);
		dp_2.setLayout(new BorderLayout());
		dp_2.add(edlglbl_2, "Center");
		dp_2.add(edlgbut_2, "South");
		edlgbut_2.addActionListener(this);

		edlg_3 = new JDialog(this, "인증", true);
		edlgbut_3 = new JButton("확인");
		edlglbl_3 = new JLabel("인증번호가 일치하지 않습니다.");
		edlg_3.getContentPane().add(dp_3);
		dp_3.setLayout(new BorderLayout());
		dp_3.add(edlglbl_3, "Center");
		dp_3.add(edlgbut_3, "South");
		edlgbut_3.addActionListener(this);

		tf_emailcomfirm.setText("");

		textField_email2 = new JTextField();
		textField_email2.setEnabled(false);
		textField_email2.setBounds(290, 184, 101, 21);
		contentPanel.add(textField_email2);
		textField_email2.setColumns(10);

		lblTt = new JLabel();
		lblTt.setForeground(Color.BLACK);
		lblTt.setBounds(309, 99, 102, 15);
		contentPanel.add(lblTt);
		settingDefault();
		setStringLengthLimit();
		passwordField1.getDocument().addDocumentListener(this);
		passwordField2.getDocument().addDocumentListener(this);
		textField_nickName.getDocument().addDocumentListener(this);
	}
	
	private void setStringLengthLimit() {
		// password2제한
		passwordField1.setDocument(new BoundDocument(15, passwordField1));
		// password2제한
		passwordField2.setDocument(new BoundDocument(15, passwordField2));
		// 이메일인증 제한
		tf_emailcomfirm.setDocument(new BoundDocument(6, tf_emailcomfirm));
		// 닉네임 제한
		textField_nickName.setDocument(new BoundDocument(16, textField_nickName));
	}

	String uuid = null;
	private JTextField textField_email2;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cb_pw) {
			passwordField1.setEnabled(true);
			passwordField2.setEnabled(true);
			if (!cb_pw.isSelected()) {
				passwordField1.setText("");
				passwordField2.setText("");
				passwordField1.setEnabled(false);
				passwordField2.setEnabled(false);
			}
			confirm_pw = false;
		}

		if (e.getSource() == cb_email) {
			textField_email1.setEnabled(true);
			textField_email2.setEnabled(true);
			btn_email.setEnabled(true);
			tf_emailcomfirm.setEnabled(true);
			btn_emailConfirm.setEnabled(true);

			if (!cb_email.isSelected()) {
				String email = telecom.getUserInfo().getEmail();
				String[] emailSpl = email.split("@");
				textField_email1.setText(emailSpl[0]);
				textField_email2.setText(emailSpl[1]);
				tf_emailcomfirm.setText("");
				textField_email1.setEnabled(false);
				textField_email2.setEnabled(false);
				btn_email.setEnabled(false);
				tf_emailcomfirm.setEnabled(false);
				btn_emailConfirm.setEnabled(false);
				confirm_email = false;
			}
		}
		if (e.getSource() == cb_nick) {
			textField_nickName.setEnabled(true);
			bt_nickConfirm.setEnabled(true);

			if (!cb_nick.isSelected()) {
				textField_nickName.setText(telecom.getUserInfo().getNickName());
				textField_nickName.setEnabled(false);
				bt_nickConfirm.setEnabled(false);
				confirm_nickName = false;
			}
		}
		if (e.getSource() == btn_email) {
			uuid = UUID.randomUUID().toString();
			uuid = uuid.substring(0, 6);
			boolean ok = me.mailJoinConfirm(textField_email1.getText() + "@" + textField_email2.getText(), uuid);
			if(!ok){
				return;
			}
		/*	edlgbut = new JButton("확인");
			edlglbl = new JLabel("메일로 전송되었습니다.", edlglbl.CENTER);
			edlg.getContentPane().add(dp);
			dp.setLayout(new BorderLayout());
			dp.add("Center", edlglbl);
			dp.add("South", edlgbut);
			edlgbut.addActionListener(this);
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize(); // 스크린사이즈
																			// 단지
																			// 가로
																			// 세로
																			// 길이만
																			// 관리
			int xpos = (int) screen.getWidth() / 2 - this.getWidth() / 2;
			int ypos = (int) screen.getHeight() / 2 - this.getHeight() / 2;
			edlg.setBounds(xpos + 400, ypos, 200, 300);
			edlg.setResizable(false);
			edlg.setSize(300, 100);
			edlg.setVisible(true);*/
		}
		if (e.getSource() == edlgbut) {
			edlg.setVisible(false);
		}
		if (e.getSource() == edlgbut_2) {
			edlg_2.setVisible(false);
		}
		if (e.getSource() == edlgbut_3) {
			edlg_3.setVisible(false);
			tf_emailcomfirm.setText("");
		}
		if (e.getSource() == edlgbut_4) {
			edlg_4.setVisible(false);
		}
		if (e.getSource() == btn_emailConfirm && uuid != null) {
			// System.out.println(uuid);
			if (tf_emailcomfirm.getText().equals(uuid)) {
/*				edlglbl_2 = new JLabel("인증번호가 일치합니다.", edlglbl_2.CENTER);
				edlgbut_2 = new JButton("확인");
				edlg_2.getContentPane().add(dp_2);
				dp_2.setLayout(new BorderLayout());
				dp_2.add("Center", edlglbl_2);
				dp_2.add("South", edlgbut_2);
				edlgbut_2.addActionListener(this);
				Dimension screen = Toolkit.getDefaultToolkit().getScreenSize(); // 스크린사이즈
																				// 단지
																				// 가로
																				// 세로
																				// 길이만
																				// 관리
				int xpos = (int) screen.getWidth() / 2 - this.getWidth() / 2;
				int ypos = (int) screen.getHeight() / 2 - this.getHeight() / 2;
				edlg_2.setBounds(xpos + 400, ypos, 200, 300);
				edlg_2.setResizable(false);
				edlg_2.setSize(300, 100);
				edlg_2.setVisible(true);*/
				JOptionPane.showMessageDialog(null, "인증이 완료되었습니다.");
				confirm_email = true;
				btn_emailConfirm.setEnabled(false);
				tf_emailcomfirm.setEnabled(false);
				textField_email1.setEnabled(false);
				textField_email2.setEnabled(false);
				btn_email.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(null, "인증번호 불일치 !");
/*				// emailtf_2.setText(null);
				edlglbl_3 = new JLabel("인증번호가 일치하지 않습니다.", edlglbl_3.CENTER);
				edlgbut_3 = new JButton("확인");
				edlg_3.getContentPane().add(dp_3);
				dp_3.setLayout(new BorderLayout());
				dp_3.add("Center", edlglbl_3);
				dp_3.add("South", edlgbut_3);
				edlgbut_3.addActionListener(this);
				Dimension screen = Toolkit.getDefaultToolkit().getScreenSize(); // 스크린사이즈
																				// 단지
																				// 가로
																				// 세로
																				// 길이만
																				// 관리
				int xpos = (int) screen.getWidth() / 2 - this.getWidth() / 2;
				int ypos = (int) screen.getHeight() / 2 - this.getHeight() / 2;
				edlg_3.setBounds(xpos + 400, ypos, 200, 300);
				edlg_3.setResizable(false);
				edlg_3.setSize(300, 100);
				edlg_3.setVisible(true);*/
				return;
			}
		}
		if (cb_pw.isSelected() || cb_email.isSelected() || cb_nick.isSelected()) {
			okbtn.setEnabled(true);
			// 체크박스가 셋중 하나라도 선택되있으면 ok버튼 활성화
		} else {
			// 아닐시 모두 비활성화
			okbtn.setEnabled(false);
		}

		if (e.getSource() == canclebtn) {
			setVisible(false);
			dispose();
		}
	}

	private void settingDefault() {
		textField_nickName.setText(telecom.getUserInfo().getNickName());
		String email = telecom.getUserInfo().getEmail();
		String[] emailSpl = email.split("@");
		textField_email1.setText(emailSpl[0]);
		textField_email2.setText(emailSpl[1]);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		textChange(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		textChange(e);
	}

	private void textChange(DocumentEvent e) {
		if (e.getDocument() == passwordField1.getDocument() || e.getDocument() == passwordField2.getDocument()) {
			if (passwordField1.getText().equals(passwordField2.getText())) {
				for (int i = 0; i < passwordField1.getText().length(); i++) {
					char c = passwordField1.getText().charAt(i);
					if (!(c >= 48 && c <= 57 || c >= 65 && c <= 90 || c >= 97 && c <= 122)) {
						confirm_pw = false;
						lblTt.setForeground(Color.RED);
						lblTt.setText("영문숫자만 가능");
						return;
					}
				}

				if (passwordField1.getText().length() >= 8 && passwordField1.getText().length() <= 15) {
					confirm_pw = true;
					lblTt.setForeground(Color.BLACK);
					lblTt.setText("패스워드 일치");
				} else {
					confirm_pw = false;
					lblTt.setForeground(Color.RED);
					lblTt.setText("글자수 부적합");
				}
			} else {
				confirm_pw = false;
				lblTt.setForeground(Color.RED);
				lblTt.setText("패스워드 불일치");
			}
		} else if (e.getDocument() == textField_nickName.getDocument()) {
			System.out.println("컨펌 닉네임 false");
			confirm_nickName = false;
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {

	}
}
