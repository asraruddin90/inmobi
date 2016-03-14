package com.inmobi.sample.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class DataAccess {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://inmobidb.ccbasdh3roho.us-west-2.rds.amazonaws.com:3306/inmobidb";

	//  Database credentials
	static final String USER = "asrar";
	static final String PASS = "12345678";
	PreparedStatement pstmt = null;

	Connection conn = null;

	public DataAccess() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			pstmt = conn.prepareStatement("insert into inmobi_response values(?,?,?,?,?,?)");

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertDb(String imgXhdpi , String clickUrl , String pckName , String rqstTime , String ipAdd)
	{
		try {
			pstmt.setInt(1, fetchPrimaryKey());
			pstmt.setString(2,imgXhdpi);
			pstmt.setString(3,clickUrl);
			pstmt.setString(4,pckName);
			pstmt.setString(5,rqstTime);
			pstmt.setString(6,ipAdd);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Could not make an entry in the DB because Package_Name is already present");
		}

	}

	public int fetchPrimaryKey()
	{
		String sql = "select count(*) as count from inmobi_response";
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		int retrunInt = 0;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			retrunInt = rs.getInt("count");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ++retrunInt;
	}

	public static void main(String[] args) {

	}

}
