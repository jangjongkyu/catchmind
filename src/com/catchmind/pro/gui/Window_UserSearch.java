package com.catchmind.pro.gui;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.catchmind.pro.client.Client_Launcher;

public class Window_UserSearch extends JFrame implements ActionListener, ItemListener {

	private JPanel contentPane;
	private JTextField textField_pwfind_id;
	private JTextField textField_pwfind_personName;
	private JTextField tf_email1;
	private JTextField choice_email2;
	private JTextField tf_idfind_name;
	private JComboBox comyear_idfind;
	private JComboBox commonth_idfind;
	private JComboBox comday_idfind;
	private JComboBox comyear_pwfind;
	private JComboBox commonth_pwfind;
	private JComboBox comday_pwfind;
	// boolean label_1;
	// boolean label_5;
	String year = "년";
	String month = "월";
	String day = "일";

	private Window_Login login_frame;
	private Window_UserSearch search_frame;
	private Client_Launcher client;
	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { Window_UserSearch frame = new
	 * Window_UserSearch(); frame.setVisible(true); } catch (Exception e) {
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

	public Window_UserSearch(Client_Launcher client, Window_Login login_frame) {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 385, 340);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.client = client;
		this.login_frame = login_frame;
		this.search_frame = this;

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				login_frame.setVisible(true);
			}
		});

		init();

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setForeground(Color.BLACK);
		tabbedPane.setBounds(-3, 0, 383, 312);
		contentPane.add(tabbedPane);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(240, 248, 255));
		panel_1.setForeground(Color.BLACK);
		tabbedPane.addTab("아이디 찾기", null, panel_1, null);
		panel_1.setLayout(null);

		JLabel label_3 = new JLabel("e-mail");
		label_3.setBounds(30, 73, 56, 15);
		panel_1.add(label_3);

		JLabel label_4 = new JLabel("이름");
		label_4.setBounds(30, 127, 48, 15);
		panel_1.add(label_4);

		tf_email1 = new JTextField();
		tf_email1.setBounds(103, 70, 96, 21);
		panel_1.add(tf_email1);
		tf_email1.setColumns(10);

		JLabel label_5 = new JLabel("생년월일");
		label_5.setBounds(30, 177, 67, 15);
		panel_1.add(label_5);

		JLabel label_6 = new JLabel("아이디 찾기");
		label_6.setForeground(new Color(0, 0, 0));
		label_6.setBounds(244, 10, 140, 43);
		panel_1.add(label_6);
		label_6.setFont(new Font("휴먼매직체", Font.BOLD, 24));

		tf_idfind_name = new JTextField();
		tf_idfind_name.setBounds(103, 124, 220, 21);
		panel_1.add(tf_idfind_name);
		tf_idfind_name.setColumns(10);

		JLabel label_7 = new JLabel("@");
		label_7.setBounds(202, 73, 12, 15);
		panel_1.add(label_7);

		choice_email2 = new JTextField();
		choice_email2.setBounds(219, 70, 103, 21);
		panel_1.add(choice_email2);

		comyear_idfind = new JComboBox();
		comyear_idfind.setBounds(103, 177, 56, 21);
		for (int i = 1900; i <= 2017; ++i) {
			comyear_idfind.addItem(i);
		}
		panel_1.add(comyear_idfind);

		commonth_idfind = new JComboBox();
		commonth_idfind.setBounds(194, 177, 40, 21);
		for (int i = 1; i <= 12; ++i) {
			commonth_idfind.addItem(i);
		}
		panel_1.add(commonth_idfind);

		comday_idfind = new JComboBox();
		comday_idfind.setBounds(269, 177, 40, 21);
		// comday_idfind.addItem(day);
		commonth_idfind.addItemListener(this);
		comday_idfind.addItemListener(this);
		panel_1.add(comday_idfind);

		JButton button = new JButton("아이디 찾기");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String msg_email = tf_email1.getText() + "@" + choice_email2.getText();
					String msg_name = tf_idfind_name.getText();
					String msg_year = comyear_idfind.getSelectedItem().toString();
					String msg_month = commonth_idfind.getSelectedItem().toString();
					String msg_day = comday_idfind.getSelectedItem().toString();

					if (msg_email.equals(null) || msg_email.equals("") || msg_name.equals(null) || msg_name.equals("")
							|| msg_year.equals(null) || msg_year.equals("") || msg_month.equals(null)
							|| msg_month.equals("") || msg_day.equals(null) || msg_day.equals("")) {
						JOptionPane.showMessageDialog(null, "빈 칸에 값을 정확히 입력해주세요.");
					} else {
						client.idfind(msg_email, msg_name, msg_year, msg_month, msg_day, search_frame);
						// tf_idfind_email.setText("");
						choice_email2.transferFocus();
						// tf_idfind_name.setText("");
						comyear_idfind.transferFocus();
						commonth_idfind.transferFocus();
						comday_idfind.transferFocus();
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "입력값이 올바르지않습니다.");
				}
			}
		});
		button.setBounds(131, 236, 118, 23);
		panel_1.add(button);

		JLabel label_8 = new JLabel("년");
		label_8.setBounds(163, 177, 24, 21);
		panel_1.add(label_8);

		JLabel label_9 = new JLabel("월");
		label_9.setBounds(238, 177, 24, 21);
		panel_1.add(label_9);

		JLabel label_10 = new JLabel("일");
		label_10.setBounds(313, 177, 24, 21);
		panel_1.add(label_10);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 248, 255));
		panel.setToolTipText("1234");
		tabbedPane.addTab("비밀번호 찾기", null, panel, null);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setBounds(30, 73, 50, 15);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel = new JLabel("비밀번호 찾기");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBounds(229, 15, 135, 32);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("휴먼매직체", Font.BOLD, 24));

		JLabel label = new JLabel("이름");
		label.setBounds(30, 127, 48, 15);
		panel.add(label);

		JLabel label_1 = new JLabel("생년월일");
		label_1.setBounds(30, 177, 67, 15);
		panel.add(label_1);

		JLabel lblNewLabel_3 = new JLabel("년");
		lblNewLabel_3.setBounds(163, 177, 24, 21);
		panel.add(lblNewLabel_3);

		JLabel label_mon = new JLabel("월");
		label_mon.setBounds(238, 177, 24, 21);
		panel.add(label_mon);

		JLabel label_2 = new JLabel("일");
		label_2.setBounds(313, 177, 24, 21);
		panel.add(label_2);

		textField_pwfind_id = new JTextField();
		textField_pwfind_id.setBounds(103, 70, 220, 21);
		panel.add(textField_pwfind_id);
		textField_pwfind_id.setColumns(10);

		textField_pwfind_personName = new JTextField();
		textField_pwfind_personName.setBounds(103, 124, 220, 21);
		panel.add(textField_pwfind_personName);
		textField_pwfind_personName.setColumns(10);

		comyear_pwfind = new JComboBox();
		comyear_pwfind.setBounds(103, 177, 56, 21);
		for (int i = 1900; i <= 2017; ++i) {
			comyear_pwfind.addItem(i);
		}
		panel.add(comyear_pwfind);

		JButton jbt_pw = new JButton("비밀번호 찾기");
		jbt_pw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				String msg_id = textField_pwfind_id.getText();
				String msg_name = textField_pwfind_personName.getText();
				String msg_year = comyear_pwfind.getSelectedItem().toString();
				String msg_month = commonth_pwfind.getSelectedItem().toString();
				String msg_day = comday_pwfind.getSelectedItem().toString();

				if (msg_id.equals(null) || msg_id.equals("") || msg_name.equals(null) || msg_name.equals("")
						|| msg_year.equals(null) || msg_year.equals("") || msg_month.equals(null)
						|| msg_month.equals("") || msg_day.equals(null) || msg_day.equals("")) {
					JOptionPane.showMessageDialog(null, "빈 칸에 값을 정확히 입력해주세요.");
				} else {
					client.pwfind(msg_id, msg_name, msg_year, msg_month, msg_day, search_frame);
					comyear_pwfind.transferFocus();
					commonth_pwfind.transferFocus();
					comday_pwfind.transferFocus();
				}
				}catch(Exception e2){
					JOptionPane.showMessageDialog(null, "입력이 올바르지 않습니다.");
				}
			}
		});
		jbt_pw.setBounds(131, 236, 118, 23);
		panel.add(jbt_pw);

		commonth_pwfind = new JComboBox();
		commonth_pwfind.setBounds(194, 177, 40, 21);
		for (int i = 1; i <= 12; ++i) {
			commonth_pwfind.addItem(i);
		}
		panel.add(commonth_pwfind);

		comday_pwfind = new JComboBox();
		comday_pwfind.setBounds(269, 177, 40, 21);
		// comday_pwfind.addItem(day);
		/*
		 * for(int i=1; i<=31; ++i){ comday_pw.addItem(i); }
		 */
		panel.add(comday_pwfind);
		commonth_pwfind.addItemListener(this);
		comday_pwfind.addItemListener(this);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == commonth_idfind) {
			if ((int) (commonth_idfind.getSelectedItem()) % 2 == 1) {
				comday_idfind.removeAllItems();
				for (int i = 1; i <= 31; i++) {
					comday_idfind.addItem(i);
				}
			} else if ((int) (commonth_idfind.getSelectedItem()) == 2) {
				comday_idfind.removeAllItems();

				if ((int) (comyear_idfind.getSelectedItem()) % 4 == 0) {
					for (int i = 1; i <= 29; ++i) {
						comday_idfind.addItem(i);
					}

				} else {
					for (int i = 1; i <= 28; ++i) {
						comday_idfind.addItem(i);
					}
				}
			} else if ((int) (commonth_idfind.getSelectedItem()) == 8) {
				comday_idfind.removeAllItems();
				for (int i = 1; i <= 31; ++i) {
					comday_idfind.addItem(i);
				}
			}

			else {
				comday_idfind.removeAllItems();
				for (int i = 1; i <= 30; ++i) {
					comday_idfind.addItem(i);
				}
			}
		} else if (e.getSource() == commonth_pwfind) {
			if ((int) (commonth_pwfind.getSelectedItem()) % 2 == 1) {
				comday_pwfind.removeAllItems();
				for (int i = 1; i <= 31; ++i) {
					comday_pwfind.addItem(i);
				}
			} else if ((int) commonth_pwfind.getSelectedItem() == 2) {
				comday_pwfind.removeAllItems();

				if ((int) (comyear_pwfind.getSelectedItem()) % 4 == 0) {
					for (int i = 1; i <= 29; ++i) {
						comday_pwfind.addItem(i);
					}
				} else {
					for (int i = 1; i <= 28; ++i) {
						comday_pwfind.addItem(i);
					}
				}
			} else if ((int) (commonth_pwfind.getSelectedItem()) == 8) {
				comday_pwfind.removeAllItems();
				for (int i = 1; i <= 31; ++i) {
					comday_pwfind.addItem(i);
				}
			} else {
				comday_pwfind.removeAllItems();
				for (int i = 1; i <= 30; ++i) {
					comday_pwfind.addItem(i);
				}
			}
		}
	}
}