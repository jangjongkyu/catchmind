package com.catchmind.pro.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.catchmind.pro.client.Client_Telecom;
import com.catchmind.pro.drawing.Catch;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Dialog_MakeRoom extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tf_roomName;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 */
	private String room_name;
	public Dialog_MakeRoom(Client_Telecom telecom , Window_WaitingRoom wating_frame) {
		setBounds(100, 100, 223, 163);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			tf_roomName = new JTextField();
			tf_roomName.setBounds(45, 49, 114, 20);
			contentPanel.add(tf_roomName);
			tf_roomName.setColumns(10);
		}
		
		JLabel lblNewLabel = new JLabel("방 이름");
		lblNewLabel.setFont(new Font("HYHeadLine M", Font.BOLD, 13));
		lblNewLabel.setBounds(45, 24, 114, 23);
		contentPanel.add(lblNewLabel);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Catch canvas = new Catch(telecom);
						Window_Room room_frame = new Window_Room(telecom,canvas);
						telecom.setRoom_frame(room_frame);
						room_frame.setTitle("[ 방제 : "+tf_roomName.getText()+" ] ");
						dispose();
						wating_frame.setVisible(false);
						telecom.makeRoom(tf_roomName.getText());
						telecom.setCanvas(canvas);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
