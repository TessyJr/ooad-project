package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Connect;

public class RegisterPage {
	Connect con;
	
	Scene scene;
	BorderPane bp;
	
	VBox formContainer;
	TextField usernameTF, emailTF;
	PasswordField passwordPF, confirmPasswordPF;
	
	ObservableList<String> roleOL;
	ComboBox<String> roleCB;
	
	Label titleLabel, usernameLabel, emailLabel, passwordLabel, confirmPasswordLabel, roleLabel, messageLabel;
	
	Button loginBtn, registerBtn;
	
	private void initialize() {
		bp = new BorderPane();
		
		formContainer = new VBox();
		
		// Labels
		titleLabel = new Label("REGISTER PAGE");
        usernameLabel = new Label("Username:");
        emailLabel = new Label("Email:");
        passwordLabel = new Label("Password:");
        confirmPasswordLabel = new Label("Confirm Password:");
        roleLabel = new Label("Role:");
        messageLabel = new Label("");

        // TextFields
        usernameTF = new TextField();
        emailTF = new TextField();
        passwordPF = new PasswordField();
        confirmPasswordPF = new PasswordField();

        // ComboBox for Role
        roleOL = FXCollections.observableArrayList("Fan", "Vendor", "Influencer"
        );
        roleCB = new ComboBox<>(roleOL);
		
		loginBtn = new Button();
		registerBtn = new Button();
		
		scene = new Scene(bp, 600, 600);
	}
	
	private void setLayout() {
		formContainer.getChildren().addAll(titleLabel, usernameLabel, usernameTF, emailLabel, emailTF, passwordLabel, passwordPF, confirmPasswordLabel, confirmPasswordPF, roleLabel, roleCB, messageLabel, registerBtn, loginBtn);
		formContainer.setMaxWidth(300);
		formContainer.setAlignment(Pos.CENTER);
		
		usernameTF.setPromptText("Insert username");
		emailTF.setPromptText("Insert email");
		passwordPF.setPromptText("Insert password");
		confirmPasswordPF.setPromptText("Insert password again");
		
		roleCB.setValue(roleOL.get(0));
		
		loginBtn.setText("Go to Login Page");
		registerBtn.setText("Register");
		
		bp.setCenter(formContainer);
	}
	
	private void actions(Stage stage){
		loginBtn.setOnMouseClicked(e -> {
		   new LoginPage(stage);
		  });
		
		registerBtn.setOnMouseClicked(e -> {	
			String username = usernameTF.getText();
			String email = emailTF.getText();
			String password = passwordPF.getText();
			String confirmPassword = confirmPasswordPF.getText();
			String role = roleCB.getValue();
			
			if(!messageLabel.getText().equals("Success!")) {
				messageLabel.setText(UserController.addUser(username, email, password, confirmPassword, role));				
			}else {
				usernameTF.setText("");
				emailTF.setText("");
				passwordPF.setText("");
				confirmPasswordPF.setText("");
				roleCB.setValue(roleOL.get(0));				
			}
		});
	}
		
	public RegisterPage(Stage stage) {
		// TODO Auto-generated constructor stub
		this.con = Connect.getInstance();
		
		initialize();
		setLayout();
		actions(stage);
		
		stage.setScene(scene);
	}

}
