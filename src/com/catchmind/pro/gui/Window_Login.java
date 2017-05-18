package com.catchmind.pro.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.catchmind.pro.client.Client_Launcher;

public class Window_Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField_id;
	private JPasswordField passwordField;

	private Client_Launcher client = null;
	private Window_Login login_frame;
	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { Window_Login frame = new
	 * Window_Login(); frame.setVisible(true); } catch (Exception e) {
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

	public Window_Login(Client_Launcher client) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 5));
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.client = client;
		this.login_frame = this;

		init();
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(this.getClass().getResource("/cuid.png")));
		label.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		label.setBounds(46, 254, 78, 39);
		contentPane.add(label);
		
		
		textField_id = new JTextField();
		textField_id.setBounds(133, 263, 145, 23);
		contentPane.add(textField_id);
		textField_id.setColumns(10);
		textField_id.setOpaque(false);
		textField_id.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(this.getClass().getResource("/cufw.png")));
		label_1.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		label_1.setBounds(46, 291, 78, 39);
		contentPane.add(label_1);
		
		
		// 로그인 버튼
		JButton btnNewButton = new JButton("");
		btnNewButton.setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
		btnNewButton.setBackground(Color.YELLOW);
		btnNewButton.setIcon(new ImageIcon(this.getClass().getResource("/login3.png")));
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 14));
		btnNewButton.setBounds(82, 340, 71, 39);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for (int i = 0; i < textField_id.getText().length(); i++) {
		               char c = textField_id.getText().charAt(i);
		               if (!(c >= 48 && c <= 57 || c >= 65 && c <= 90 || c >= 97 && c <= 122)) {
		                  JOptionPane.showMessageDialog(null, "아이디는 영문과 숫자로만 입력하셔야 합니다.");
		                  return;
		               }
		            }
				
				for (int i = 0; i < passwordField.getText().length(); i++) {
		               char c = passwordField.getText().charAt(i);
		               if (!(c >= 48 && c <= 57 || c >= 65 && c <= 90 || c >= 97 && c <= 122)) {
		                  JOptionPane.showMessageDialog(null, "패스워드는 영문과 숫자로만 입력하셔야 합니다.");
		                  return;
		               }
		            }
				
				client.login(textField_id.getText(), passwordField.getText());
			}
		});
		
		
		
		JButton button = new JButton("");
		button.setBackground(Color.YELLOW);
		button.setIcon(new ImageIcon(this.getClass().getResource("/join.png")));
		button.setFont(new Font("굴림", Font.PLAIN, 14));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Window_Join(client,login_frame);
				login_frame.setVisible(false);
			}
		});
		
		button.setBorder(new LineBorder(Color.LIGHT_GRAY,3));
		button.setBounds(184, 340, 71, 39);

		contentPane.add(button);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(133, 298, 145, 23);
		passwordField.setOpaque(false);
		passwordField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(passwordField);
		passwordField.addKeyListener(new KeyListener() {
			
			private char chr;

			@Override
			public void keyTyped(KeyEvent e) {
				
			}
				
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					client.login(textField_id.getText(), passwordField.getText());
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(this.getClass().getResource("/loginlogo.png")));
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		lblNewLabel.setBounds(5, 5, 322, 253);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setBackground(Color.LIGHT_GRAY);
		btnNewButton_1.setIcon(new ImageIcon(this.getClass().getResource("/forgot5.png")));
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Window_UserSearch(client,login_frame);
				login_frame.setVisible(false);
			}
		});
		btnNewButton_1.setBorder(new LineBorder(Color.LIGHT_GRAY,3));
		btnNewButton_1.setFont(new Font("굴림", Font.PLAIN, 12));
		btnNewButton_1.setBounds(46, 389, 257, 33);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(this.getClass().getResource("/cutf.png")));
		lblNewLabel_1.setBounds(124, 291, 157, 39);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setBounds(96, 254, 27, -53);
		contentPane.add(lblNewLabel_2);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(this.getClass().getResource("/cutf.png")));
		label_2.setBounds(123, 254, 157, 39);
		contentPane.add(label_2);
		
		JLabel label_slash = new JLabel("/");
		label_slash.setIcon(new ImageIcon(this.getClass().getResource("/slash.png")));
		label_slash.setBounds(155, 340, 27, 39);
		contentPane.add(label_slash);
	}
}
