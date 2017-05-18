package com.catchmind.pro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.swing.JOptionPane;

import com.catchmind.pro.util.Catch_Protocol;
import com.catchmind.pro.util.DataBaseConnection;
import com.catchmind.pro.util.MailExam;
import com.catchmind.pro.vo.UserVO;

public class UserDAO {
   private static UserDAO userDAO = null;

   private UserDAO() {
   }

   public static UserDAO getUserDAO() {
      if (userDAO == null) {
         userDAO = new UserDAO();
      }
      return userDAO;
   }

   public boolean checkUserId(UserVO vo) {
      String sql = "SELECT * FROM CATCH_USER WHERE user_id = ?";

      Connection connection = DataBaseConnection.getDataBaseConnection().getConnector();
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
         pstmt = connection.prepareStatement(sql);
         pstmt.setString(1, vo.getUser_id());
         rs = pstmt.executeQuery();

         boolean isOk = false;
         if (rs.next()) {
            isOk = false;
         } else {
            isOk = true;
         }

         if (rs != null)
            rs.close();
         if (pstmt != null)
            pstmt.close();
         return isOk;
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
      }
      return false;
   }

   public boolean checkUserNickName(UserVO vo) {
      String sql = "SELECT * FROM CATCH_USER WHERE nickName = ?";

      Connection connection = DataBaseConnection.getDataBaseConnection().getConnector();
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
         pstmt = connection.prepareStatement(sql);
         pstmt.setString(1, vo.getNickName());
         rs = pstmt.executeQuery();

         boolean isOk = false;
         if (rs.next()) {
            isOk = false;
         } else {
            isOk = true;
         }

         if (rs != null)
            rs.close();
         if (pstmt != null)
            pstmt.close();

         return isOk;
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return false;
   }

   public UserVO getUser(UserVO vo) {
      String sql = "SELECT * FROM CATCH_USER WHERE user_id = ?";
      System.out.println("getUser() start");
      Connection connection = DataBaseConnection.getDataBaseConnection().getConnector();
      try {
         PreparedStatement pstmt = connection.prepareStatement(sql);
         pstmt.setString(1, vo.getUser_id());
         ResultSet rs = pstmt.executeQuery();

         if (rs.next()) {
            String pw_db1 = rs.getString("user_password1");
            String pw_db2 = rs.getString("user_password2");
            String pw_cl1 = (vo.getUser_password().substring(0, vo.getUser_password().length() / 2)) + "password";
            String pw_cl2 = (vo.getUser_password().substring(vo.getUser_password().length() / 2)) + "password";
            pw_cl1 = pw_cl1.hashCode() + "";
            pw_cl2 = pw_cl2.hashCode() + "";
            if (pw_db1.equals(pw_cl1) && pw_db2.equals(pw_cl2)) {
               UserVO user = new UserVO();
               user.setSeq(rs.getInt("seq"));
               user.setUser_id(rs.getString("user_id"));
               user.setUser_password(pw_db1 + pw_db2);
               user.setEmail(rs.getString("email"));
               user.setNickName(rs.getString("nickName"));
               user.setPersonName(rs.getString("personName"));
               user.setBirth_year(rs.getInt("birth_year"));
               user.setBirth_month(rs.getInt("birth_month"));
               user.setBirth_day(rs.getInt("birth_day"));
               user.setUser_pokemon(rs.getInt("user_pokemon"));

               return user;
            } else {
               System.out.println("[서버]비밀번호 불일치");
               // JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다
               // !!");
               return null;
            }
         } else {
            // JOptionPane.showMessageDialog(null, "존재하지 않는 ID 입니다.");
            System.out.println("[서버] 존재하지 않는 ID");
         }

      } catch (SQLException e) {
         System.out.println("DB 에러!!");
         e.printStackTrace();
      }
      System.out.println("getUser()");
      return null;
   }

   public boolean insertUser(UserVO vo) {
      Connection connection = DataBaseConnection.getDataBaseConnection().getConnector();
      String sql = "INSERT INTO CATCH_USER(seq , user_id , user_password1 , user_password2 , "
            + "email , nickName , personName , birth_year , birth_month , birth_day, user_pokemon) "
            + "values( catch_user_seq.nextval,?,?,?,?,?,?,?,?,?,?)";

      System.out.println("DAO 포켓몬번호 : "+vo.getUser_pokemon());
      String pw = vo.getUser_password();
      String pw_hash1 = pw.substring(0, (pw.length() / 2)) + "password";
      String pw_hash2 = pw.substring(pw.length() / 2) + "password";
      System.out.println(pw_hash1.hashCode());
      System.out.println(pw_hash2.hashCode());
      try {
         PreparedStatement pstmt = connection.prepareStatement(sql);
         pstmt.setString(1, vo.getUser_id());
         pstmt.setString(2, pw_hash1.hashCode() + "");
         pstmt.setString(3, pw_hash2.hashCode() + "");
         pstmt.setString(4, vo.getEmail());
         pstmt.setString(5, vo.getNickName());
         pstmt.setString(6, vo.getPersonName());
         pstmt.setInt(7, vo.getBirth_year());
         pstmt.setInt(8, vo.getBirth_month());
         pstmt.setInt(9, vo.getBirth_day());
         pstmt.setInt(10, vo.getUser_pokemon());
         int res = pstmt.executeUpdate();
         if (res > 0) {
            return true;
         } else {
            return false;
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return false;
   }

   public void modifyUser(UserVO vo) {
      Connection con = DataBaseConnection.getDataBaseConnection().getConnector();

      String sql = "UPDATE catch_user set ";
      if(!vo.getUser_password().equals(Catch_Protocol.STATE_PASSWORD_EMPTY+"")){
         String pw = vo.getUser_password();
         String pw_hash1 = pw.substring(0 , (pw.length()/2)) + "password";
         String pw_hash2 = pw.substring(pw.length() / 2) + "password";
         System.out.println(pw_hash1.hashCode());
         System.out.println(pw_hash2.hashCode());
         sql += "user_password1='"+ (pw_hash1.hashCode()+"") +"', user_password2= '"+ (pw_hash2.hashCode()+"") +"',";
      }
      sql += "email='"+vo.getEmail()+"', nickname='"+vo.getNickName()+"' where seq="+vo.getSeq();
      
      System.out.println("dao 확인");
      System.out.println(vo);
      
      try {
         PreparedStatement pstmt = con.prepareStatement(sql);
/*         pstmt.setString(1, pw_hash1.hashCode() + "");
         pstmt.setString(2, pw_hash2.hashCode() + "");
         pstmt.setString(3, vo.getEmail());
         pstmt.setString(4, vo.getNickName());
         pstmt.setInt(5, vo.getSeq());*/
         int res = pstmt.executeUpdate();
         if (res > 0) {
            JOptionPane.showMessageDialog(null, "회원 수정을 성공적으로 마쳤습니다. 재접속을 하면 적용됩니다.");
         } else {
            JOptionPane.showMessageDialog(null, "회원수정 실패!", "회원수정 오류", JOptionPane.ERROR_MESSAGE);
         }
      } catch (SQLException e) {
         e.printStackTrace();
         JOptionPane.showMessageDialog(null, "회원수정 실패!", "회원수정 오류", JOptionPane.ERROR_MESSAGE);
      }
   }

   public String idFind(UserVO vo) {
      Connection con = DataBaseConnection.getDataBaseConnection().getConnector();
      String sql = "SELECT * FROM catch_user WHERE email = ? and personName = ? and birth_year = ? and birth_month = ? and birth_day = ?";
      System.out.println(sql);
      try {
         PreparedStatement pstmt = con.prepareStatement(sql);
         pstmt.setString(1, vo.getEmail());
         pstmt.setString(2, vo.getPersonName());
         pstmt.setInt(3, vo.getBirth_year());
         pstmt.setInt(4, vo.getBirth_month());
         pstmt.setInt(5, vo.getBirth_day());
         ResultSet rs = pstmt.executeQuery();
         int cnt = 0;
         String msg = "@id";
         while (rs.next()) {
            UserVO user = new UserVO();
            user.setEmail(rs.getString("email"));
            user.setPersonName(rs.getString("personName"));
            user.setBirth_year(rs.getInt("birth_year"));
            user.setBirth_month(rs.getInt("birth_month"));
            user.setBirth_day(rs.getInt("birth_day"));

            if (user.getEmail().equals(vo.getEmail()) && user.getPersonName().equals(vo.getPersonName())
                  && user.getBirth_year() == vo.getBirth_year() && user.getBirth_month() == vo.getBirth_month()
                  && user.getBirth_day() == vo.getBirth_day()) {
               user.setUser_id(rs.getString("user_id"));
               msg += "?" + user.getUser_id();
               cnt++;
            }
         }
         if (cnt > 0) {
            return msg;
         } else {
            return null;
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return null;
   }

   public String pwFind(UserVO vo) {
      try {
         String sql = "SELECT * FROM catch_user WHERE user_id = ? and personName = ? and "
               + " birth_year = ? and birth_month = ? and birth_day = ?";
         Connection con = DataBaseConnection.getDataBaseConnection().getConnector();
         PreparedStatement pstmt = con.prepareStatement(sql);
         pstmt.setString(1, vo.getUser_id());
         pstmt.setString(2, vo.getPersonName());
         pstmt.setInt(3, vo.getBirth_year());
         pstmt.setInt(4, vo.getBirth_month());
         pstmt.setInt(5, vo.getBirth_day());
         ResultSet rs = pstmt.executeQuery();

         if (rs.next()) {
            UserVO user = new UserVO();
            user.setUser_id(rs.getString("user_id"));
            user.setPersonName(rs.getString("personName"));
            user.setBirth_year(rs.getInt("birth_year"));
            user.setBirth_month(rs.getInt("birth_month"));
            user.setBirth_day(rs.getInt("birth_day"));
            user.setEmail(rs.getString("email"));
            user.setSeq(rs.getInt("seq"));
            if (user.getUser_id().equals(vo.getUser_id()) && user.getPersonName().equals(vo.getPersonName())
                  && user.getBirth_year() == vo.getBirth_year() && user.getBirth_month() == vo.getBirth_month()
                  && user.getBirth_day() == vo.getBirth_day()) {
               return convertPw(user);
            }
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return "@pwFindFail";
   }

   private String convertPw(UserVO vo) {
      try {
         String sql = "UPDATE catch_user SET user_password1 = ? , user_password2 = ? WHERE seq = ? ";
         
         String[] randomUUID = UUID.randomUUID().toString().split("-");
         String randomPw = randomUUID[randomUUID.length-1];
         String pw = randomPw;
         String pw_hash1 = pw.substring(0, (pw.length() / 2)) + "password";
         String pw_hash2 = pw.substring(pw.length() / 2) + "password";
         
         
         Connection con = DataBaseConnection.getDataBaseConnection().getConnector();
         PreparedStatement pstmt = con.prepareStatement(sql);
         pstmt.setString(1, pw_hash1.hashCode()+"");
         pstmt.setString(2, pw_hash2.hashCode()+"");
         pstmt.setInt(3, vo.getSeq());
         int res = pstmt.executeUpdate();
         
         if(res > 0 ){
            MailExam mail = new MailExam();
            return mail.mailPasswordFind(vo.getEmail(), randomPw);
         }else{
            return "@pwFindFail";
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }

      return "@pwFindFail";
   }
   
   public void deleteUser(UserVO vo) {

   }

}