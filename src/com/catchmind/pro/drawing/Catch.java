package com.catchmind.pro.drawing;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import com.catchmind.pro.client.Client_Telecom;
import com.catchmind.pro.databox.DrawListData;

public class Catch extends Canvas implements MouseListener, MouseMotionListener, ItemListener, ActionListener {
	private int x_origin;
	private int y_origin;
	private int x1;
	private int y1;
	private int dist = 0;
	private Color color = Color.BLACK;
	private int pen = 3;
	private Graphics2D g2;
	private boolean clicked = false;
	private boolean isStart = false;

	private Client_Telecom room_protocol;

	public void penSizeUp() {
		pen += 3;
		if (pen > 20) {
			pen = 20;
		}
	}

	public void penSizeDown() {
		pen -= 3;
		if (pen < 3) {
			pen = 3;
		}
	}

	public int getPen() {
		return pen;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setdist(int dist) {
		this.dist = dist;
	}

	private List<DrawInfo> arr = new ArrayList<DrawInfo>();
	Graphics2D gg = null;
	Graphics g = null;
	Canvas cv;

	public List<DrawInfo> getArr() {
		return arr;
	}

	public Catch(Client_Telecom room_protocol) {
		// super();
		this.room_protocol = room_protocol;
		start();
		this.setBackground(Color.WHITE);
		this.setSize(500, 500);
		this.setVisible(true);
	}

	public void start() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	public void paint(Graphics g) {
		g2 = (Graphics2D) g;
		if (!clicked) {
			System.out.println("리스트 전체 재생");
			for (int j = 0; j < arr.size(); j++) {
				DrawInfo imsi2 = (DrawInfo) arr.get(j);
				g2.setColor(imsi2.getColor());
				g2.setStroke(new BasicStroke(imsi2.getpen(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 20));
				g2.drawLine(imsi2.getX(), imsi2.getY(), imsi2.getX1(), imsi2.getY1());
			}
		} else {
			g2.setColor(this.color);
			g2.setStroke(new BasicStroke(this.pen, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 20));
			g2.drawLine(x_origin, y_origin, x1, y1);
		}
	}

	public void remove() {
		arr = new ArrayList<DrawInfo>();
		this.getGraphics().clearRect(0, 0, this.getWidth(), this.getHeight());
		repaint();
	}

	public void mouseClicked(MouseEvent e) {

	}

	// 마우스를 누른 지점을 시작점으로 등록
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			clicked = true;
			isStart = true;
			x_origin = e.getX();
			y_origin = e.getY();
		}
		if (clicked) {
			if (!isStart) {
				x_origin = x1;
				y_origin = y1;
			} else {
				x_origin = e.getX();
				y_origin = e.getY();
				isStart = false;
			}
			x1 = e.getX();
			y1 = e.getY();
			// PEN이 선택 되었을때 모든 움직임을 벡터에 저장한다.
			DrawInfo di = new DrawInfo(x_origin, y_origin, x1, y1, dist, this.color, this.pen);
			this.repaint();
			arr.add(di);
		}
	}

	// 마우스를 뗀 지점을 끝점으로 등록한다. repaint()메서드를 호출하여 다시 그림을 그린다.
	public void mouseReleased(MouseEvent e) {
		clicked = false;
		DrawListData data = new DrawListData(arr);
		room_protocol.sendPaint(data);
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {

	}

	public void mouseDragged(MouseEvent e) {
		if (clicked) {
			if (!isStart) {
				x_origin = x1;
				y_origin = y1;
			} else {
				x_origin = e.getX();
				y_origin = e.getY();
				isStart = false;
			}
			x1 = e.getX();
			y1 = e.getY();
			// PEN이 선택 되었을때 모든 움직임을 벡터에 저장한다.
			DrawInfo di = new DrawInfo(x_origin, y_origin, x1, y1, dist, this.color, this.pen);
			this.repaint();
			arr.add(di);
		}
		clicked = true;
	}

	public void itemStateChanged(ItemEvent e) {

	}

	public void actionPerformed(ActionEvent e) {
		this.setBackground(Color.WHITE);
	}

	public void setDrawingList(List<DrawInfo> list) {
		if (list.size() == 0) {
			remove();
			return;
		}

		int aaa = this.arr.size();
		this.arr = list;

		Graphics g = this.getGraphics();
		Graphics2D g2 = (Graphics2D) g;

		for (int i = aaa; i < arr.size(); i++) {
			g2.setColor(arr.get(i).getColor());
			g2.setStroke(new BasicStroke(arr.get(i).getpen(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 20));
			g2.drawLine(arr.get(i).getX(), arr.get(i).getY(), arr.get(i).getX1(), arr.get(i).getY1());

		}
	}

}
