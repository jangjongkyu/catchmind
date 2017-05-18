package com.catchmind.pro.util;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.Timer;

import com.catchmind.pro.gui.Window_Room;

/*
 *  4월 21일 17:50 최종수정자 : 장종규
 */
public class Toast extends JDialog implements Observer {
	private static final long serialVersionUID = -1602907470843951525L;

	public enum Style {
		NORMAL, SUCCESS, ERROR
	};

	public static final int LENGTH_SHORT = 3000;
	public static final int LENGTH_LONG = 6000;
	public static final Color ERROR_RED = new Color(121, 0, 0);
	public static final Color SUCCESS_GREEN = new Color(22, 127, 57);
	public static final Color NORMAL_BLACK = new Color(0, 0, 0);

	private final float MAX_OPACITY = 0.8f;
	private final float OPACITY_INCREMENT = 0.05f;
	private final int FADE_REFRESH_RATE = 20;
	private final int WINDOW_RADIUS = 15;
	private final int CHARACTER_LENGTH_MULTIPLIER = 14;
	private final int DISTANCE_FROM_PARENT_TOP = 100;

	private Window_Room mOwner;
	private String mText;
	private String mText2;
	private int mDuration;
	private Color mBackgroundColor = Color.BLACK;
	private Color mForegroundColor = Color.WHITE;

	private int textLength;

	private Toast thisInstance;

	private static int instance = 0;

	private int playerNumber = 0; // 2

	private static List<Toast> toastQue = new ArrayList<Toast>();

	public Toast(Window_Room owner) {
		super(owner);
		mOwner = owner;
		mOwner.addToastObserver(this);
		thisInstance = this;
	}

	private synchronized void createGUI() {

		setLayout(new GridBagLayout());
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), WINDOW_RADIUS, WINDOW_RADIUS));
			}
		});

		setAlwaysOnTop(true);
		setUndecorated(true);
		setFocusableWindowState(false);
		setModalityType(ModalityType.MODELESS);

		System.out.println(textLength);
		setSize(textLength * CHARACTER_LENGTH_MULTIPLIER, 40);

		/*
		 * if(mText.length() > 30){ mText2 = new String(); mText2 =
		 * mText.substring(30); mText = mText.substring(0,30)+"<br>"; mText =
		 * "<html>"+mText+mText2+"<html>"; }
		 */

		getContentPane().setBackground(mBackgroundColor);

		JLabel label = new JLabel(mText);
		JLabel label2 = new JLabel(mText2);

		label.setForeground(mForegroundColor);
		add(label);
		add(label2);
	}

	public synchronized void fadeIn() {
		final Timer timer = new Timer(FADE_REFRESH_RATE, null);
		timer.setRepeats(true);
		timer.addActionListener(new ActionListener() {
			private float opacity = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				opacity += OPACITY_INCREMENT;
				setOpacity(Math.min(opacity, MAX_OPACITY));
				if (opacity >= MAX_OPACITY) {
					timer.stop();
				}
			}
		});
		setOpacity(0);
		timer.start();

		setLocation(getToastLocation());
		setVisible(true);

		System.out.println("현재 토스트 큐에 남은 인스턴스 : "+toastQue.size());
		for (int i = 0 ; i < toastQue.size() ; i++ ) {
			if(toastQue.get(i).playerNumber == this.playerNumber){
				toastQue.get(i).fadeOut();
			}
		}
		toastQue.add(this);
	}

	@Override
	public void update(Observable observavle, Object object) {
		convertLocation();
	}

	public void convertLocation() {
		instance--;
		setLocation(getToastLocation());
	}

	public synchronized void fadeOut() {
		final Timer timer = new Timer(FADE_REFRESH_RATE, null);
		timer.setRepeats(true);
		timer.addActionListener(new ActionListener() {
			private float opacity = MAX_OPACITY;

			@Override
			public void actionPerformed(ActionEvent e) {
				opacity -= OPACITY_INCREMENT;
				setOpacity(Math.max(opacity, 0));
				if (opacity <= 0) {
					timer.stop();
					setVisible(false);
					dispose();
					mOwner.deleteToastObserver(thisInstance);
				}
			}
		});

		setOpacity(MAX_OPACITY);
		timer.start();
		
		toastQue.remove(this);
	}

	private synchronized Point getToastLocation() {
		Point ownerLoc = mOwner.getLocation();
		int x = 0;
		int y = 0;

		switch (playerNumber) {
		case 1:
			x = (int) (ownerLoc.getX() + 210);
			y = (int) (ownerLoc.getY() + 130);
			break;
		case 2:
			x = (int) (ownerLoc.getX() + 800 - getSize().getWidth());
			y = (int) (ownerLoc.getY() + 130);
			break;
		case 3:
			x = (int) (ownerLoc.getX() + 210);
			y = (int) (ownerLoc.getY() + 260);
			break;
		case 4:
			x = (int) (ownerLoc.getX() + 800 - getSize().getWidth());
			y = (int) (ownerLoc.getY() + 260);
			break;
		case 5:
			x = (int) (ownerLoc.getX() + 210);
			y = (int) (ownerLoc.getY() + 390);
			break;
		case 6:
			x = (int) (ownerLoc.getX() + 800 - getSize().getWidth());
			y = (int) (ownerLoc.getY() + 390);
			break;
		case 7:
			x = (int) (ownerLoc.getX() + 210);
			y = (int) (ownerLoc.getY() + 520);
			break;
		case 8:
			x = (int) (ownerLoc.getX() + 800 - getSize().getWidth());
			y = (int) (ownerLoc.getY() + 520);
			break;
		default:
			x = (int) (ownerLoc.getX() + ((mOwner.getWidth() - this.getWidth()) / 2));
			y = (int) ((ownerLoc.getY() + mOwner.getHeight() - 250) + DISTANCE_FROM_PARENT_TOP) - (50);
			instance++;
		}

		return new Point(x, y);
	}

	public void setText(String text) {
		mText = text;
	}

	public void setDuration(int duration) {
		mDuration = duration;
	}

	@Override
	public void setBackground(Color backgroundColor) {
		mBackgroundColor = backgroundColor;
	}

	@Override
	public void setForeground(Color foregroundColor) {
		mForegroundColor = foregroundColor;
	}

	/*
	 * public static Toast makeText(Window_Room owner, String text) { return
	 * makeText(owner, text, LENGTH_SHORT); }
	 * 
	 * public static Toast makeText(Window_Room owner, String text, Style style)
	 * { return makeText(owner, text, LENGTH_SHORT, style); }
	 * 
	 * public static Toast makeText(Window_Room owner, String text, int
	 * duration) { return makeText(owner, text, duration, Style.NORMAL); }
	 */

	public static Toast makeText(Window_Room owner, String text, int duration, Style style, int player) {

		Toast toast = new Toast(owner);
		toast.playerNumber = player;

		toast.textLength = text.length();
		// System.out.println("총 글자수 : " + text.length());
		text.replaceAll("<br>", "");
		if (text.length() > 15) {
			toast.textLength = text.substring(0, 15).length();
			String str1 = text.substring(0, 15) + "<br>";
			String str2 = text.substring(15);
			text = "<html>" + str1 + str2 + "<html>";
		}
		toast.mText = text;
		toast.mDuration = duration;

		if (style == Style.SUCCESS) {
			toast.mBackgroundColor = Color.YELLOW;
			toast.mForegroundColor = Color.BLACK;
		}
		if (style == Style.ERROR)
			toast.mBackgroundColor = ERROR_RED;
		if (style == Style.NORMAL)
			toast.mBackgroundColor = NORMAL_BLACK;

		return toast;
	}

	public void display() {

		Thread tr = new Thread(new Runnable() {
			@Override
			public synchronized void run() {
				try {
					createGUI();
					fadeIn();
					Thread.sleep(mDuration);
					fadeOut();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		tr.start();

		Thread tr2 = new Thread(new Runnable() {
			@Override
			public synchronized void run() {
				try {
					Thread.sleep(mDuration);
					instance = 0;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		tr2.start();
	}

	/*
	 * public static void main(String args) { final Window_Room frame = new
	 * Window_Room(); frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	 * frame.setSize(new Dimension(500, 300)); JButton b = new
	 * JButton("Toast!");
	 * 
	 * b.addActionListener(new ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent e) {
	 * Toast.makeText(frame, "토스트메시지 입니다. 안녕 하세요 반갑습니다. ㅎ.", LENGTH_LONG,
	 * Style.NORMAL).display(); } });
	 * 
	 * frame.add(b); frame.setVisible(true); }
	 */
}
