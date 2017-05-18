package com.catchmind.pro.util;
import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {
	private static DataBaseConnection dataBaseConnection = null;
	private Connection connection = null;
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "scott";
	private String password = "tiger";	
	
	private DataBaseConnection(){
		try{
			System.out.println("Connection Create .. ");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(url,user,password);	
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("데이터베이스 커넥터 연결실패 !");
		}
	}
	
	public static DataBaseConnection getDataBaseConnection(){
		if(dataBaseConnection == null){
			dataBaseConnection = new DataBaseConnection();
		}
		return dataBaseConnection;
	}
	
	public Connection getConnector(){
		try{
			return connection;
		}catch(Exception e){
			return null;
		}
	}
	
}
