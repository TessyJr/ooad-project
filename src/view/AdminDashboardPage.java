package view;

import controller.AdminController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Connect;
import model.User;

public class AdminDashboardPage {
	Connect con;
	
	Scene scene;
	BorderPane bp;
	
	VBox formContainer;

	Label titleLabel;
	Button vendorBtn, fanBtn, influencerBtn;
	
	private void initialize() {
		bp = new BorderPane();
		
		formContainer = new VBox();
		
		titleLabel = new Label("ADMIN DASHBOARD PAGE");

		vendorBtn = new Button("Go to Vendors");
		fanBtn = new Button("Go to Fans");
		influencerBtn = new Button("Go to Influencers");
		
		scene = new Scene(bp, 600, 600);
	}
	
	private void setLayout() {
		formContainer.getChildren().addAll(titleLabel, vendorBtn, fanBtn, influencerBtn);
		formContainer.setMaxWidth(300);
		formContainer.setAlignment(Pos.CENTER);
		
		bp.setCenter(formContainer);
	}
	
	private void actions(Stage stage){
		AdminController.fanBtnHandle(fanBtn, stage);
		AdminController.vendorBtnHandle(vendorBtn, stage);
		AdminController.influencerBtnHandle(influencerBtn, stage);
	}
	
	public AdminDashboardPage(Stage stage) {
		// TODO Auto-generated constructor stub
		this.con = Connect.getInstance();
		
		initialize();
		setLayout();
		actions(stage);
		
		stage.setScene(scene);
	}

}
