package com.codestrive.jdbcDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCDemo {
	
	public static void main(String[] args) {
		
		int stateId = 1;
		String stateName = null;
		String user = "username";
		String pwd = "******";
		String driverClass = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/demo";
		Connection conn = null;
              try {        
            	  	Class.forName(driverClass);
                    conn =  DriverManager.getConnection(url, user, pwd);
	                PreparedStatement ps = conn.prepareStatement("select * from state where state_id=?");
	                ps.setInt(1, stateId);
	                ResultSet rs = ps.executeQuery();
	                if(rs.next()){
	                	stateName =  rs.getString("state_name");
	                }
	                rs.close();
	                ps.close();
	                System.out.println("State Name from DB :"+stateName);
	             }
	         catch(Exception e){
	                e.printStackTrace();
	         }
	         finally {
	        	 try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	         }
	         
	}
	
	

}
