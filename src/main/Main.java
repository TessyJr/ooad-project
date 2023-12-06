package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginPage;

public class Main extends Application {

	public Main() {
		// TODO Auto-generated constructor stub
}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Query();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		new LoginPage(primaryStage);
		
		primaryStage.setTitle("SNova");
		primaryStage.show();
		
	}
}
