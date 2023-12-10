package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
	private static Connect instance;
	
	// Connection -> menyimpan koneksi dengan mySQL
	private Connection con;
	
	// Staement -> Menjalankan query
	private Statement st;

	// username, password, nama database, host
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String HOST = "localhost:3306";
	
	private final String DATABASE = "snova";
	private final String CONNECTION = String.format("JDBC:mysql://%s/%s", HOST, DATABASE);
	
	public static Connect getInstance() {
		if(instance == null) {
			instance = new Connect();
		}
		
		return instance;
	}
	
	public Connect() {
		try {
			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			st = con.createStatement();
//			System.out.println(con);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void execute(String query) {
		try {
			st.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void executeUpdate(String query) {
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public ResultSet executeQuery(String query) {
		try {
			ResultSet rs = st.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public PreparedStatement preparedStatement(String query) {
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ps;
	}
	
	public PreparedStatement transactionPreparedStatement(String query, int returnGeneratedKeys) {
	    PreparedStatement ps = null;

	    try {
	        if (returnGeneratedKeys == Statement.RETURN_GENERATED_KEYS) {
	            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        } else {
	            ps = con.prepareStatement(query);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return ps;
	}
}
