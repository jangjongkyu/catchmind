package com.catchmind.pro.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import com.catchmind.pro.databox.DataBox;
import com.catchmind.pro.databox.DrawListData;
import com.catchmind.pro.databox.MessageData;
import com.catchmind.pro.util.Catch_Protocol;
import com.catchmind.pro.vo.UserVO;

public class Server_ChatterAccepter extends Thread {
   private UserVO user = null;
   private Socket soc = null;

   private Server_WaitingRoom waitingAdmin;
   private Server_GameRoom chatterListAdmin;

   private ObjectOutputStream objectOut = null;
   private ObjectInputStream objectIn = null;

   private PrintWriter pw = null;
   private BufferedReader br = null;

   public Server_ChatterAccepter(Socket soc, UserVO user) {
      this.soc = soc;
      this.user = user;
      this.waitingAdmin = Server_WaitingRoom.getWaitingRoom();
      // this.chatterListAdmin =
      // Server_ChatterListAdmin.getChatterListAdmin().addChatter(this);

      System.out.println("ChatterAccepter()");
      try {
         pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
         br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
         objectOut = new ObjectOutputStream(soc.getOutputStream());
         objectIn = new ObjectInputStream(soc.getInputStream());

         // 접속한멤버포함한 UserVO 객체들 현재 클라이언트들에게 뿌려주기

      } catch (Exception e1) {
         e1.printStackTrace();
      }

      this.start();
   }

   @Override
   public void run() {

      while (true) {
         DataBox data = null;
         try {
            data = (DataBox) objectIn.readObject();
            System.out.println("protocol : " + data.getProtocol());

            switch (data.getProtocol()) {
            case Catch_Protocol.PROTOCOL_MSG:
               chatterListAdmin.sendMsg(this,data);
               break;
            case Catch_Protocol.PROTOCOL_DRAW:
               chatterListAdmin.sendPaint(this,data);
               break;
            case Catch_Protocol.PROTOCOL_WAITINGROOM_MSG:
               waitingAdmin.sendMsg(user.getNickName(), data);
               break;
            case Catch_Protocol.PROTOCOL_MAKEROOM:
               waitingAdmin.makeRoom(this,data);
               break;
            case Catch_Protocol.PROTOCOL_ROOMACCEPT:
               waitingAdmin.acceptRoom(this,data);
               break;
            case Catch_Protocol.PROTOCOL_EXITROOM:
               chatterListAdmin.exitRoom(this);
               break;
            case Catch_Protocol.PROTOCOL_USERINFO:
               waitingAdmin.sendUserInfo(this);
               break;
            default:
               System.err.println("[ 등록되지 않은 프로토콜 !!]");
               break;
            }

         } catch(SocketException se){
            se.printStackTrace();
            System.err.println(this.getUser().getNickName() + " 님 퇴장 ");   
            if(chatterListAdmin != null){
               chatterListAdmin.removeChatter(this);
            }
            waitingAdmin.removeChatter(this);
            try {
				soc.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
            return;
         } catch (Exception e) {
            e.printStackTrace();
            System.err.println(this.getUser().getNickName() + " 님 퇴장 ");   
            if(chatterListAdmin != null){
               chatterListAdmin.removeChatter(this);
            }
            waitingAdmin.removeChatter(this);
            try {
				soc.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
            return;
         }
      }
   }
   
   
   public Server_WaitingRoom getWaitingAdmin() {
      return waitingAdmin;
   }

   public void setWaitingAdmin(Server_WaitingRoom waitingAdmin) {
      this.waitingAdmin = waitingAdmin;
   }

   public void setChatterListAdmin(Server_GameRoom chatterListAdmin) {
      this.chatterListAdmin = chatterListAdmin;
   }

   public UserVO getUser() {
      return user;
   }

   public void setUser(UserVO user) {
      this.user = user;
   }

   public PrintWriter getPw() {
      return pw;
   }

   public void setPw(PrintWriter pw) {
      this.pw = pw;
   }

   public BufferedReader getBr() {
      return br;
   }

   public void setBr(BufferedReader br) {
      this.br = br;
   }

   public ObjectInputStream getObjectIn() {
      return objectIn;
   }

   public void setObjectIn(ObjectInputStream objectIn) {
      this.objectIn = objectIn;
   }

   public ObjectOutputStream getObjectOut() {
      return objectOut;
   }

   public void setObjectOut(ObjectOutputStream objectOut) {
      this.objectOut = objectOut;
   }

   public Socket getSoc() {
      return soc;
   }

   public void setSoc(Socket soc) {
      this.soc = soc;
   }

}