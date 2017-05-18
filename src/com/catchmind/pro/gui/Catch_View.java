package com.catchmind.pro.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import javax.swing.*;

public class Catch_View extends JDialog implements ActionListener{

	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Catch_View dialog = new Catch_View();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Catch_View() {
		setBounds(100, 100, 450, 327);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton btn_1 = new JButton("");
			btn_1.setBounds(12, 10, 61, 61);
			btn_1.setIcon(new ImageIcon(this.getClass().getResource("/pokemon/kobugi.gif")));
			contentPanel.add(btn_1);
		}
		{
			JButton btn_2 = new JButton("");
			btn_2.setBounds(81, 10, 61, 61);
			btn_2.setIcon(new ImageIcon(this.getClass().getResource("/pokemon/pairi.gif")));
			contentPanel.add(btn_2);
		}
		{
			JButton btn_3 = new JButton("");
			btn_3.setBounds(152, 10, 61, 61);
			btn_3.setIcon(new ImageIcon(this.getClass().getResource("/pokemon/pika.gif")));
			contentPanel.add(btn_3);
		}
		{
			JButton btn_4 = new JButton("");
			btn_4.setBounds(224, 10, 61, 61);
			btn_4.setIcon(new ImageIcon(this.getClass().getResource("/pokemon/rumi.gif")));
			contentPanel.add(btn_4);
		}
		{
			JButton btn_6 = new JButton("");
			btn_6.setBounds(370, 10, 61, 61);
			btn_6.setIcon(new ImageIcon(this.getClass().getResource("/pokemon/purin.gif")));
			contentPanel.add(btn_6);
		}
		{
			JButton btn_5 = new JButton("");
			btn_5.setBounds(297, 10, 61, 61);
			btn_5.setIcon(new ImageIcon(this.getClass().getResource("/pokemon/ssi.gif")));
			contentPanel.add(btn_5);
		}
		{
			JButton btn_7 = new JButton("");
			btn_7.setBounds(12, 90, 61, 61);
			btn_7.setIcon(new ImageIcon(this.getClass().getResource("/pokemon/99.gif")));
			contentPanel.add(btn_7);
		}
		{
			JButton btn_8 = new JButton("");
			btn_8.setBounds(81, 90, 61, 61);
			btn_8.setIcon(new ImageIcon(this.getClass().getResource("/pokemon/anelka.jpg")));
			contentPanel.add(btn_8);
		}
		{
			JButton btn_9 = new JButton("");
			btn_9.setBounds(152, 90, 61, 61);
			btn_9.setIcon(new ImageIcon(this.getClass().getResource("/pokemon/star.gif")));
			contentPanel.add(btn_9);
		}
		{
			JButton btn_10 = new JButton("");
			btn_10.setBounds(224, 90, 61, 61);
			btn_10.setIcon(new ImageIcon(this.getClass().getResource("/pokemon/coil.gif")));
			contentPanel.add(btn_10);
		}
		{
			JButton btn_11 = new JButton("");
			btn_11.setBounds(297, 90, 61, 61);
			btn_11.setIcon(new ImageIcon(this.getClass().getResource("/pokemon/dugi.gif")));
			contentPanel.add(btn_11);
		}
		{
			JButton btn_12 = new JButton("");
			btn_12.setBounds(370, 90, 61, 61);
			btn_12.setIcon(new ImageIcon(this.getClass().getResource("/pokemon/bbul.gif")));
			contentPanel.add(btn_12);
		}
		{
			JButton btn_13 = new JButton("");
			btn_13.setBounds(12, 173, 61, 61);
			btn_13.setIcon(new ImageIcon(this.getClass().getResource("/pokemon/modapi.gif")));
			contentPanel.add(btn_13);
		}
		{
			JButton btn_14 = new JButton("");
			btn_14.setBounds(81, 173, 61, 61);
			btn_14.setIcon(new ImageIcon(this.getClass().getResource("/pokemon/muntari.png")));
			contentPanel.add(btn_14);
		}
		{
			JButton btn_15 = new JButton("");
			btn_15.setBounds(152, 173, 61, 61);
			btn_15.setIcon(new ImageIcon(this.getClass().getResource("/pokemon/cat.gif")));
			contentPanel.add(btn_15);
		}
		{
			JButton btn_16 = new JButton("");
			btn_16.setBounds(224, 173, 61, 61);
			btn_16.setIcon(new ImageIcon(this.getClass().getResource("/pokemon/digda.gif")));
			contentPanel.add(btn_16);
		}
		{
			JButton btn_17 = new JButton("");
			btn_17.setBounds(297, 173, 61, 61);
			btn_17.setIcon(new ImageIcon(this.getClass().getResource("/pokemon/gadi.gif")));
			contentPanel.add(btn_17);
		}
		{
			JButton btn_18 = new JButton("");
			btn_18.setBounds(370, 173, 61, 61);
			btn_18.setIcon(new ImageIcon(this.getClass().getResource("/pokemon/keisi.gif")));
			contentPanel.add(btn_18);
		}
		{
			JLabel lbl_name1 = new JLabel("\uAF2C\uBD80\uAE30");
			lbl_name1.setBounds(22, 71, 57, 15);
			contentPanel.add(lbl_name1);
		}
		{
			JLabel lbl_name2 = new JLabel("\uD30C\uC774\uB9AC");
			lbl_name2.setBounds(91, 71, 57, 15);
			contentPanel.add(lbl_name2);
		}
		{
			JLabel lbl_name3 = new JLabel("\uD53C\uCE74\uCE04");
			lbl_name3.setBounds(162, 71, 57, 15);
			contentPanel.add(lbl_name3);
		}
		{
			JLabel lbl_name4 = new JLabel("\uB0B4\uB8E8\uBBF8");
			lbl_name4.setBounds(234, 71, 57, 15);
			contentPanel.add(lbl_name4);
		}
		{
			JLabel lbl_name5 = new JLabel("\uC774\uC0C1\uD574\uC528");
			lbl_name5.setBounds(301, 71, 57, 15);
			contentPanel.add(lbl_name5);
		}
		{
			JLabel lbl_name6 = new JLabel("\uD478\uB9B0");
			lbl_name6.setBounds(390, 71, 57, 15);
			contentPanel.add(lbl_name6);
		}
		{
			JLabel lbl_name7 = new JLabel("\uAD6C\uAD6C");
			lbl_name7.setBounds(32, 150, 57, 15);
			contentPanel.add(lbl_name7);
		}
		{
			JLabel lbl_name8 = new JLabel("\uC544\uB12C\uCE74");
			lbl_name8.setBounds(91, 150, 57, 15);
			contentPanel.add(lbl_name8);
		}
		{
			JLabel lbl_name9 = new JLabel("\uBCC4\uAC00\uC0AC\uB9AC");
			lbl_name9.setBounds(158, 150, 57, 15);
			contentPanel.add(lbl_name9);
		}
		{
			JLabel lbl_name10 = new JLabel("\uCF54\uC77C");
			lbl_name10.setBounds(244, 150, 57, 15);
			contentPanel.add(lbl_name10);
		}
		{
			JLabel lbl_name11 = new JLabel("\uBAA8\uB798\uB450\uC9C0");
			lbl_name11.setBounds(301, 150, 57, 15);
			contentPanel.add(lbl_name11);
		}
		{
			JLabel lbl_name12 = new JLabel("\uBFD4\uCDA9\uC774");
			lbl_name12.setBounds(380, 150, 57, 15);
			contentPanel.add(lbl_name12);
		}
		{
			JLabel lbl_name13 = new JLabel("\uBAA8\uB2E4\uD53C");
			lbl_name13.setBounds(20, 238, 57, 15);
			contentPanel.add(lbl_name13);
		}
		{
			JLabel lbl_name14 = new JLabel("\uBB38\uD0C0\uB9AC");
			lbl_name14.setBounds(91, 238, 57, 15);
			contentPanel.add(lbl_name14);
		}
		{
			JLabel lbl_name15 = new JLabel("\uB0D0\uC639\uC774");
			lbl_name15.setBounds(162, 238, 57, 15);
			contentPanel.add(lbl_name15);
		}
		{
			JLabel lbl_name16 = new JLabel("\uB514\uADF8\uB2E4");
			lbl_name16.setBounds(234, 238, 57, 15);
			contentPanel.add(lbl_name16);
		}
		{
			JLabel lbl_name17 = new JLabel("\uAC00\uB514");
			lbl_name17.setBounds(311, 238, 57, 15);
			contentPanel.add(lbl_name17);
		}
		{
			JLabel lbl_name18 = new JLabel("\uCF00\uC774\uC2DC");
			lbl_name18.setBounds(380, 238, 57, 15);
			contentPanel.add(lbl_name18);
		}
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
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == okButton){
			this.setVisible(false);
		}
	}

}
