package com.catchmind.pro.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.catchmind.pro.gui.Window_Login;
import com.catchmind.pro.gui.Window_Room;
import com.catchmind.pro.gui.Window_UserSearch;

/*
 * 理쒖쥌 �뾽�뜲�씠�듃 �궇吏� :4�썡 28�씪
 */
public class Client_Launcher {

	private InetAddress ia;
	private Socket soc;
	private PrintWriter pw;
	private BufferedReader br;

	private Window_Login login_frame;

	private Client_Telecom room_protocol;
	public Client_Launcher() {
		login_frame = new Window_Login(this);
		login_frame.setVisible(true);

		try {
			ia = InetAddress.getByName("localhost");
			soc = new Socket(ia, 20000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Thread th = new Thread(this);
	}
	public Socket getSoc() {
		return soc;
	}
	public void setSoc(Socket soc) {
		this.soc = soc;
	}
	public Window_Login getWindow_Login() {
		return login_frame;
	}
	public void setWindow_Login(Window_Login window_Login) {
		this.login_frame = window_Login;
	}

	public void login(String id, String password) {// 濡쒓렇�씤
		try {
			br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			pw = new PrintWriter(soc.getOutputStream());
			System.out.println("id : " + id + " , pw : " + password);
			pw.println("!" + id + "!" + password);
			pw.flush();
			String msg = br.readLine();
			System.out.println("msg : ");
			System.out.println(msg);
			if (msg != null && msg.equals("@loginOK")) {
				login_frame.dispose();
				JOptionPane.showMessageDialog(null, "濡쒓렇�씤 �꽦怨�!!");
				this.room_protocol = new Client_Telecom(this);
			}else if(msg != null && msg.equals("@accepting")){
				System.out.println("[�젒�넚以�]");
				JOptionPane.showMessageDialog(null, "�씠誘� �젒�냽以묒씤 怨꾩젙�엯�땲�떎.!!");
			}else {
				JOptionPane.showMessageDialog(null, "濡쒓렇�씤�떎�뙣 !!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void join(String id, String password, String email, String nickname, String personalname, String year,
			String month, String day, int user_pokemon) {// �쉶�썝媛��엯
		try {
			br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			pw = new PrintWriter(soc.getOutputStream());
			System.out.println("*" + id + "*" + password + "*" + email + "*" + nickname + "*" + personalname + "*"
					+ year + "*" + month + "*" + day);
			pw.println("*" + id + "*" + password + "*" + email + "*" + nickname + "*" + personalname + "*" + year + "*"
					+ month + "*" + day + "*" + user_pokemon );
			pw.flush();
			String msg = br.readLine();
			System.out.println("msg : ");
			System.out.println(msg);
			if (msg != null && msg.equals("@joinOK")) {
				JOptionPane.showMessageDialog(null, "�쉶�썝媛��엯 �꽦怨�!!");
				login_frame.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "�쉶�썝媛��엯 �떎�뙣 !!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void idfind(String email, String personalname, String year, String month, String day,
			Window_UserSearch search_frame) {// �븘�씠�뵒李얘린
		try {
			br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			pw = new PrintWriter(soc.getOutputStream());
			System.out.println("#" + email + "#" + personalname + "#" + year + "#" + month + "#" + day);

			pw.println("#" + email + "#" + personalname + "#" + year + "#" + month + "#" + day);
			pw.flush();

			String msg = br.readLine();
			System.out.println("msg : ");
			System.out.println(msg);
			if (msg != null && msg.startsWith("@id?")) {
				String usersStr = msg.substring(4);
				String[] userIds = usersStr.split("\\?");
				String[] hiddenId = new String[userIds.length];
				String resultMsg = "";
				for (int j = 0 ; j < userIds.length ; j++) {
					hiddenId[j] = "";
					for (int i = 0; i < userIds[j].length(); i++) {
						if (i > 2 && i < userIds[j].length() - 2) {
							System.out.println("�닲源�");
							hiddenId[j] += "*";
						} else {
							System.out.println("異붽�");
							hiddenId[j] += userIds[j].charAt(i);
						}
					}
					resultMsg += hiddenId[j]+"  ";
				}
				JOptionPane.showMessageDialog(null, "�빐�떦�릺�뒗 �쉶�썝�쓽 �븘�씠�뵒�뒗 :" + resultMsg + " �엯�땲�떎.");
				login_frame.setVisible(true);
				search_frame.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "�엯�젰�븳 �젙蹂댁뿉 �씪移섑븯�뒗 �쉶�썝�씠 議댁옱�븯吏� �븡�뒿�땲�떎.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pwfind(String id, String personalname, String year, String month, String day, Window_UserSearch search_frame) {// 鍮꾨�踰덊샇李얘린
		try {
			br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			pw = new PrintWriter(soc.getOutputStream());
			System.out.println("$" + id + "$" + personalname + "$" + year + "$" + month + "$" + day);
			
			pw.println("$" + id + "$" + personalname + "$" + year + "$" + month + "$" + day);
			pw.flush();
			
			String msg = br.readLine();
			System.out.println("msg : ");
			System.out.println(msg);
			if (msg != null && msg.equals("@pwFindOK")) {
				JOptionPane.showMessageDialog(null, "�빐�떦�쑀���뿉 �벑濡앸릺�뼱�엳�뒗 �씠硫붿씪濡� �엫�떆鍮꾨�踰덊샇瑜� �쟾�넚�뻽�뒿�땲�떎.");
				login_frame.setVisible(true);
				search_frame.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "�엯�젰�븳 �젙蹂댁뿉 �씪移섑븯�뒗 �쉶�썝�씠 議댁옱�븯吏� �븡�뒿�땲�떎.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//------------------------------洹몃┝�솕硫�------------------------
	
	public static void main(String[] args) {
		new Client_Launcher();
	}
}
