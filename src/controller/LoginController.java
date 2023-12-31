package controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.User;
import view.AdminDashboardPage;
import view.LoginPage;
import view.RegisterPage;

public class LoginController {
//	function untuk login user
	public static void loginHandle(Button loginBtn, Stage stage) {
		loginBtn.setOnMouseClicked(e -> {
	        String email = LoginPage.emailTF.getText();
	        String password = LoginPage.passwordPF.getText();

	        String loginResult = UserController.login(email, password);

	        if (loginResult.equals("Success!")) {
	            User user = UserController.getUserByEmail(email);
	            
//	            memvalidasi dashboard sesuai dengan role user
	            if (user.getRole().equals("Admin")) {
	                NavigateController.navigateAdminDashbaord(stage);
	            } else if(user.getRole().equals("Influencer")) {
	            	NavigateController.navigateInfluencerHomePage(stage, user);
	            } else if(user.getRole().equals("Vendor")) {
	            	NavigateController.navigateVendorHomePage(stage, user);
	            } else if(user.getRole().equals("Fan")) {
	            	NavigateController.navigateFanHomePage(stage, user);
	            }

	            LoginPage.emailTF.setText("");
	            LoginPage.passwordPF.setText("");
	        } else {
	        	LoginPage.messageLabel.setText(loginResult);
	        }
	    });
	}
	
	public static void registerHandle(Button registerBtn, Stage stage) {
		registerBtn.setOnMouseClicked(e -> {
	        NavigateController.navigateRegister(stage);
	    });
	}

	public LoginController() {
		// TODO Auto-generated constructor stub
	}

}
