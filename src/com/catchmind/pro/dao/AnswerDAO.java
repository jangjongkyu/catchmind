package com.catchmind.pro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.catchmind.pro.util.DataBaseConnection;

public class AnswerDAO {
	private static AnswerDAO answerDAO = null;

	private AnswerDAO() {
	}

	public static AnswerDAO getAnswerDAO() {
		if (answerDAO == null) {
			answerDAO = new AnswerDAO();
		}
		return answerDAO;
	}
	
	public List<String> getaAnswer(){
	      try{
	      String sql = "SELECT * FROM catch_answer";
	      
	      Connection con = DataBaseConnection.getDataBaseConnection().getConnector();
	      PreparedStatement pstmt = con.prepareStatement(sql);
	      pstmt.executeQuery();
	      ResultSet rs = pstmt.executeQuery();
	      
	      List<String> answerList = new ArrayList<String>();
	      
	      while(rs.next()){
	         String answer = rs.getString("question");
	         answerList.add(answer);
	      }
	      return answerList;
	      
	      }catch(SQLException e){
	         e.printStackTrace();
	      }
	      return null;
	   }
}
