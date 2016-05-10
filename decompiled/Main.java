/*
 * author: Kudryash Igor
 * description: Simple sql query builder based on java
 * 09.05.2016 21:58
 * 
 * Requires mysql-connector-java.5.1.38 or grader
 */

package SQLBuilder;

import java.sql.*;

public class Main {
	
	public static Connection conn;
	public static Statement stmt;
	public static ResultSet result;
	
	private String host;
	private String dbUser;
	private String password;
	private String dbName;
	private int port;
	private String url;
	
	private String table = "";
	private String query = "";
	
	public Main(String host, int port, String dbUser, String password, String dbName) {
		this.host = host;
		this.port = port;
		this.dbUser = dbUser;
		this.password = password;
		this.dbName = dbName;
		
		this.url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.dbName;
	}
	
	public Main connect() {
		try {
			conn = DriverManager.getConnection(this.url, this.dbUser, this.password);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	public Main table(String table) {
		this.table = table;
		
		return this;
	}
	
	public Main select(String[] selection) {
		this.query = "SELECT";
		for (String str : selection) {
			this.query += " " + str + ", ";
		}
		this.query = this.query.substring(0, this.query.length() - 2);
		this.query += " FROM " + this.table;
		
		return this;
	}
	
	public Main where(String str) {
		this.query += " WHERE";
		String[] spliting = str.split("=>");
		if (spliting.length == 2) {
			for (String component : spliting) {
				component = component.trim();
			}			
			this.query += " " + spliting[0] + "=" + spliting[1];
		} else {
			System.out.println("Error in 'where' syntax");
		}		
		
		return this;
	}
	
	public String get(int clm) {
		String Result = "";
		try {
			result = stmt.executeQuery(query);
			while (result.next()) {
				Result = result.getString(clm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Result;
	}
	
}
