package view;

import java.util.List;

import controller.AdminController;
import controller.PanelController;
import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.Connect;
import model.User;

public class InfluencerHomePage {
	Connect con;
	User user;
	
	Scene scene;
	BorderPane bp;
	
	VBox formContainer;

	Label titleLabel;
	Button createPanelBtn;
	
	List<User> fanList;
	ObservableList<User> observableList;
	
	TableView<User> tableView;
	TableColumn<User, Integer> panelIdColumn;
	TableColumn<User, String> titleColumn;
	TableColumn<User, String> descColumn;
	TableColumn<User, String> locationColumn;
	TableColumn<User, Button> startColumn;
	TableColumn<User, Button> endColumn;
	
	private void initialize(Stage stage) {
		tableView = new TableView<>();
		panelIdColumn = new TableColumn<>("PanelID");
        titleColumn = new TableColumn<>("Title");
        descColumn = new TableColumn<>("Description");
        locationColumn = new TableColumn<>("Location");
        startColumn = new TableColumn<>("Start Time");
        endColumn = new TableColumn<>("End Time");

//        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("UserID"));
//        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));
//        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
//        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("Password"));
//        
//        tableView.getColumns().addAll(userIdColumn, usernameColumn, emailColumn, passwordColumn, deleteColumn);

        fanList = UserController.getAllUsersInRole("Fan"); 
        observableList = FXCollections.observableArrayList(fanList);
        tableView.setItems(observableList);
		
		bp = new BorderPane();
		
		formContainer = new VBox();
		
		titleLabel = new Label("INFLUENCER HOME PAGE");

		createPanelBtn = new Button("Create Panel");
		
		scene = new Scene(bp, 600, 600);
	}
	
	private void setLayout() {
		formContainer.getChildren().addAll(titleLabel, tableView, createPanelBtn);
		formContainer.setMaxWidth(300);
		formContainer.setAlignment(Pos.CENTER);
		
		bp.setCenter(formContainer);
	}
	
	private void actions(Stage stage, User user){
		PanelController.createPanelBtnHandle(createPanelBtn, stage, user);
	}
	
	public InfluencerHomePage(Stage stage, User user) {
		// TODO Auto-generated constructor stub
		this.con = Connect.getInstance();
		this.user = user;
		
		initialize(stage);
		setLayout();
		actions(stage, user);
		
		stage.setScene(scene);
	}

}
