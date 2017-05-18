package com.catchmind.pro.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Window_ServerInfo extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3937833443214577013L;
	private JPanel contentPane;

	public Window_ServerInfo(int port,String ip) {
		setTitle("CatchUp Server. ver_1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 336, 182);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	 

		JLabel lblIp = new JLabel("IP : "+ip,JLabel.CENTER);
		lblIp.setFont(new Font("±¼¸²", Font.BOLD, 13));
		lblIp.setBounds(71, 33, 177, 22);
		contentPane.add(lblIp);
		
		JLabel lblPort = new JLabel("Port : "+port,JLabel.CENTER);
		lblPort.setFont(new Font("±¼¸²", Font.BOLD, 13));
		lblPort.setBounds(71, 76, 177, 22);
		contentPane.add(lblPort);
		
		JButton btnNewButton = new JButton("\uC11C\uBC84\uC885\uB8CC");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton.setBounds(104, 110, 106, 23);
		contentPane.add(btnNewButton);
		setVisible(true);
	}
}
