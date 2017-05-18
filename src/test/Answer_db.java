package test;

import java.sql.Statement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.*;
 
   
public class Answer_db {
    BufferedReader br = null;
     Connection conn = null;
     Statement stmt = null;
     PreparedStatement ps = null;
     String url,user,pass;
     String file = "C:/answer.txt";
  
     String sql;
     public Answer_db(){
        try {
           String getLine;
         FileReader fr = new FileReader(file);
         br = new BufferedReader(fr);
         url = "jdbc:oracle:thin:@localhost:1521:xe";
            user = "scott";
            pass = "tiger";
            
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url,user,pass);
            stmt = conn.createStatement();
            
           // String sql;
            //int num = 0;
            while((getLine = br.readLine()) != null){
               //sql = "insert into catch_q values('"++"','"+getLine+"')";
               sql = "insert into catch_answer values(ques_sequence.nextval , '"+getLine+"')";
               stmt.addBatch(sql);
               //stmt.executeQuery(sql);
               stmt.executeBatch();
               //stmt.executeUpdate(sql);
            }
            //stmt.executeUpdate(sql);
            stmt.executeBatch();
            //stmt.executeQuery(getLine);
            conn.commit();
            if(br!=null)br.close();
            if(fr!=null)fr.close();
      } catch (FileNotFoundException e) {
         // TODO Auto-generated catch block
         System.out.println("파일찾을수없음");
         e.printStackTrace();
      } catch (ClassNotFoundException e) {
         // TODO Auto-generated catch block
         System.out.println("클래스 찾을수없음");
         e.printStackTrace();
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         System.out.println("sql오류");
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         System.out.println("파일 입출력 오류");
         e.printStackTrace();
      }    
        finally{
           try {stmt.close();} catch (Exception ee) {}
           try {conn.close();} catch (Exception eee) {}
           try {br.close();} catch (Exception e) {}
        }
     }
 
        public static void main(String[] args) {
              Answer_db ex01 = new Answer_db();
        }
 
}