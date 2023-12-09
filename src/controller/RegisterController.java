package controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.User;
import view.LoginPage;
import view.RegisterPage;

public class RegisterController {
	
	public static void loginHandle(Button loginBtn, Stage stage) {
		loginBtn.setOnMouseClicked(e -> {
			   NavigateController.navigateLogin(stage);
			  });
	}
	
	public static void registerHandle(Button registerBtn, Stage stage) {
		registerBtn.setOnMouseClicked(e -> {	
			String username = RegisterPage.usernameTF.getText();
			String email = RegisterPage.emailTF.getText();
			String password = RegisterPage.passwordPF.getText();
			String confirmPassword = RegisterPage.confirmPasswordPF.getText();
			String role = RegisterPage.roleCB.getValue();
			
			
			
			if(!RegisterPage.messageLabel.getText().equals("Success!")) {
				RegisterPage.messageLabel.setText(UserController.addUser(username, email, password, confirmPassword, role));	
				
				User user = UserController.getUserByEmail(email);
				
				if(role.equals("Influencer")) {
					NavigateController.navigateInfluencerHomePage(stage, user);
				}else if(role.equals("Vendor")) {
					NavigateController.navigateVendorHomePage(stage, user);
				}
			}
		});
	}

	public RegisterController() {
		// TODO Auto-generated constructor stub
	}

}
