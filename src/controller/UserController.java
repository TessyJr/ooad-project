package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Connect;
import model.User;
import view.LoginPage;

public class UserController {
	private static Connect con = Connect.getInstance();

//	function untuk cek apakah username unique atau tidak (unique -> tidak boleh sama dalam 1 database)
	private static boolean isUsernameUnique(String username) {
	    String query = "SELECT COUNT(*) FROM Users WHERE Username = ?";
	    
	    try (PreparedStatement ps = con.preparedStatement(query)) {
	        ps.setString(1, username);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                return count == 0;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}

//	function untuk cek apakah email unique atau tidak (unique -> tidak boleh sama dalam 1 database)
	private static boolean isEmailUnique(String email) {
	    String query = "SELECT COUNT(*) FROM Users WHERE Email = ?";
	    
	    try (PreparedStatement ps = con.preparedStatement(query)) {
	        ps.setString(1, email);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                return count == 0;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	
//	function untuk validasi email harus terdapat '@' dan tidak boleh kosong
	private static boolean isValidEmail(String email) {
	    if (email.isEmpty() || !email.contains("@")) {
	        return false;
	    }
	    return true;
	}
	
// function untuk validasi panjang password dan kriteria password
	private static boolean isValidPassword(String password) {
	    return password.length() >= 6 && password.matches(".*[0-9].*") && password.matches(".*[a-zA-Z].*");
	}
	
//  function untuk memanggil function validate register agar muncul error text nya
	public static String addUser(String username, String email, String password, String confirmPassword, String role) {
        String validationError = validateUserAttributes(username, email, password, confirmPassword, role);
        if (!validationError.equals("Success!")) {
            return validationError;
        }

        User.addUser(username, email, password, role);

        return "Success!";
    }

//	function untuk memvalidasi atribut user dari username, email, password dan role
    private static String validateUserAttributes(String username, String email, String password, String confirmPassword, String role) {
        if (username.isEmpty()) {
            return "Username must be filled.";
        }

        if (!isUsernameUnique(username)) {
            return "Username already exists. Choose a different username.";
        }

        if (!isValidEmail(email)) {
            return "Email must be valid and contain '@'.";
        }

        if (!isEmailUnique(email)) {
            return "Email is already registered. Choose a different email.";
        }

        if (!isValidPassword(password)) {
            return "Password must be at least 6 characters long and alphanumeric.";
        }
        
        if (!confirmPassword.equals(password)) {
            return "Confirm Password must be the same as Password.";
        }

        if (role == null || role.isEmpty()) {
            return "Role must be chosen.";
        }

        return "Success!";
    }

//	function untuk cek email apakah sudah ada pada database atau belum
	private static boolean isEmailExists(String email) {
	    String query = "SELECT COUNT(*) FROM Users WHERE Email = ?";
	    
	    try (PreparedStatement ps = con.preparedStatement(query)) {
	        ps.setString(1, email);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                return count > 0;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}

//	function untuk cek apakah password sudah sama dengan password pada database
	private static boolean isPasswordMatch(String email, String password) {
	    String query = "SELECT COUNT(*) FROM Users WHERE Email = ? AND Password = ?";
	    
	    try (PreparedStatement ps = con.preparedStatement(query)) {
	        ps.setString(1, email);
	        ps.setString(2, password);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                return count > 0;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}

//	function untuk validasi atribut login (email dan password)
    private static String validateLogin(String email, String password) {
    	if (email.isEmpty()) {
            return "Email cannot be empty.";
        }

        if (!isEmailExists(email)) {
            return "Email does not exist.";
        }

        if (password.isEmpty()) {
            return "Password cannot be empty.";
        }

        if (!isPasswordMatch(email, password)) {
            return "Password does not match with the email.";
        }

        return "Success!";
    }
	
//    function untuk memanggil function validate login agar muncul error text nya
    public static String login(String email, String password) {
    	String validationError = validateLogin(email, password);
        if (!validationError.equals("Success!")) {
            return validationError;
        }

        User.login(email, password);

        return "Success!";
    }
    
    public static User getUserByEmail(String email) {
    	User user = User.getUserByEmail(email);
    	
    	return user;
    }
    
    public static ObservableList<User> getAllUsersInRole(String role){
    	return User.getAllUsersInRole(role);
    }
    
    public static void deleteFan(Integer id) {
    	User.delete(id);
    }
    
    public static void deleteVendor(Integer id) {
    	User.delete(id);
    }
    
    public static void deleteInfluencer(Integer id) {
    	User.delete(id);
    }
    
    public static void logoutBtnHandle(Button logoutBtn, Stage stage) {
    	logoutBtn.setOnMouseClicked(e -> {
    		new LoginPage(stage);
    	});
    }
    
    public UserController() {
	}
}
