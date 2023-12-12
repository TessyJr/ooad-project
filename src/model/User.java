package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Connect;

public class User {
	private static Connect con = Connect.getInstance();
	private Integer userID;
	private String username, email, password, role;
	
	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer id) {
		this.userID = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public static void addUser(String username, String email, String password, String role) {
		String query = "INSERT INTO `Users`(`Username`, `Email`, `Password`, `Role`) VALUES (?, ?, ?, ?)";
		
		PreparedStatement ps = con.preparedStatement(query);
		
		try {
			ps.setString(1, username);
			ps.setString(2, email);
			ps.setString(3, password);
			ps.setString(4, role);
			
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	public static void login(String email, String password) {
		String loginQuery = String.format("SELECT * FROM users WHERE Email = '%s' AND Password = '%s'", email, password);
		
		ResultSet rs = con.executeQuery(loginQuery);
		  
		  try {
		   while(rs.next()) {
//		    System.out.println(rs.getString("Email") + " " + rs.getString("Password"));
		   }
		  } catch (SQLException e2) {
		   // TODO Auto-generated catch block
		   e2.printStackTrace();
		  }
	};
	
	public static User getUserByEmail(String email) {
	    String getUserQuery = String.format("SELECT * FROM users WHERE Email = '%s'", email);

	    try (ResultSet rs = con.executeQuery(getUserQuery)) {
	        if (rs.next()) {
	        	Integer userId = rs.getInt("UserID");
	            String username = rs.getString("Username");
	            String userEmail = rs.getString("Email");
	            String password = rs.getString("Password");
	            String role = rs.getString("Role");
	            
	            User user = new User(userId, username, userEmail, password, role);

	            return user;
	        } else {
	            return null;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	public static ObservableList<User> getAllUsersInRole(String role) {
        ObservableList<User> userList = FXCollections.observableArrayList();;

        String query = String.format("SELECT * FROM users WHERE Role = '%s'", role);

        try (
        	PreparedStatement ps = con.preparedStatement(query);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Integer userId = rs.getInt("UserID");
                String username = rs.getString("Username");
	            String userEmail = rs.getString("Email");
	            String password = rs.getString("Password");
	            
	            User user = new User(userId, username, userEmail, password, role);
	            userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }
	
	public static void delete(Integer id) {
		String query = String.format("DELETE FROM users WHERE UserID = %d", id);
	
	    PreparedStatement ps = con.preparedStatement(query);
	    try {
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	     
	public User(Integer userId, String username, String email, String password, String role) {
		super();
		this.userID = userId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}
}
