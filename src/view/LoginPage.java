package view;

import controller.LoginController;
import controller.UserController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Connect;
import model.User;

public class LoginPage {
	Connect con;
	
	Scene scene;
	BorderPane bp;
	
	VBox formContainer;
	public static TextField emailTF;
	public static PasswordField passwordPF;
	
	public static Label titleLabel, emailLabel, passwordLabel, messageLabel;
	public static Button loginBtn, registerBtn;
	
	private void initialize() {
		bp = new BorderPane();
		
		formContainer = new VBox();
		
		emailTF = new TextField();
		passwordPF = new PasswordField();
		
		titleLabel = new Label("LOGIN PAGE");
		emailLabel = new Label("Email:");
		passwordLabel = new Label("Password:");
		messageLabel = new Label();
		
		loginBtn = new Button();
		registerBtn = new Button();
		
		scene = new Scene(bp, 600, 600);
	}
	
	private void setLayout() {
		formContainer.getChildren().addAll(titleLabel, emailLabel, emailTF, passwordLabel, passwordPF, messageLabel, loginBtn, registerBtn);
		formContainer.setMaxWidth(300);
		formContainer.setAlignment(Pos.CENTER);
		
		emailTF.setPromptText("Insert email");
		passwordPF.setPromptText("Insert password");
		
		loginBtn.setText("Login");
		registerBtn.setText("Go to Register Page");
		
		bp.setCenter(formContainer);
	}
	
	private void actions(Stage stage) {
	    LoginController.loginHandle(loginBtn, stage);
	    LoginController.registerHandle(registerBtn, stage);
	}
	
	public LoginPage(Stage stage) {
		// TODO Auto-generated constructor stub
		this.con = Connect.getInstance();
		
		initialize();
		setLayout();
		actions(stage);
		
		stage.setScene(scene);
	}

}
