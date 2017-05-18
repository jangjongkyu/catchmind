package com.catchmind.pro.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.catchmind.pro.client.Client_Telecom;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.*;
import javax.swing.JPasswordField;

public class Catch_edit extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField pwF;
	private JButton okButton;
	private JButton cancelButton;
	private Catch_memberedit medit;
	private Catch_edit cedit;

	private Client_Telecom telecom;

	public Catch_edit(Client_Telecom telecom) {
		this.telecom = telecom;
		setBounds(100, 100, 290, 177);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lbl_main = new JLabel("비밀번호 확인");
		lbl_main.setFont(new Font("굴림", Font.BOLD, 16));
		lbl_main.setBounds(87, 10, 135, 38);
		contentPanel.add(lbl_main);

		JLabel lbl_pwd = new JLabel("패스워드 확인");
		lbl_pwd.setBounds(24, 58, 86, 15);
		contentPanel.add(lbl_pwd);

		pwF = new JPasswordField();
		pwF.setBounds(108, 58, 99, 21);
		contentPanel.add(pwF);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(this);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(this);
			}
		}
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {
			// 로그인된 비밀번호와 일치하는구문 넣어야함
			String pw = pwF.getText();
			String pw_cl1 = (pw.substring(0, pw.length() / 2)) + "password";
			String pw_cl2 = (pw.substring(pw.length() / 2)) + "password";
			pw_cl1 = pw_cl1.hashCode() + "";
			pw_cl2 = pw_cl2.hashCode() + "";
			if ((pw_cl1 + pw_cl2).equals(telecom.getUserInfo().getUser_password())) {
				medit = new Catch_memberedit(telecom);
				JOptionPane.showMessageDialog(null, "비밀번호가 일치합니다.");
				medit.setVisible(true);
				this.setVisible(false);
				getRootPane().setDefaultButton(okButton);
				return;
			}else{
				JOptionPane.showMessageDialog(null, "비밀번호가 불일치 !");
			}
		}
		if (e.getSource() == cancelButton) {
			this.dispose();
			this.setVisible(false);
		}
	}
}
