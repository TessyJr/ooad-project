package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import controller.CreateItemController;
import controller.CreatePanelController;
import controller.PanelController;
import controller.RegisterController;
import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Connect;
import model.User;

public class VendorCreateItemPage {
	Connect con;
	User user;
	
	Scene scene;
	BorderPane bp;
	
	VBox formContainer;
	public static TextField nameTF, descTF;
	public static Spinner<Integer> priceSpinner;
	
	public static Label titleLabel, nameLabel, descLabel, priceLabel, messageLabel;
	
	public static Button createItemBtn, vendorHomeBtn;
	
	private void initialize() {
		bp = new BorderPane();
		
		formContainer = new VBox();
		
		// Labels
		titleLabel = new Label("CREATE PANEL PAGE");
        nameLabel = new Label("Name:");
        descLabel = new Label("Description:");
        priceLabel = new Label("Price:");
        messageLabel = new Label("");

        // TextFields
        nameTF = new TextField();
        descTF = new TextField();
        
        // Spinner
        priceSpinner = new Spinner<>(0, Integer.MAX_VALUE, 0);
        priceSpinner.setEditable(true); 
		
		createItemBtn = new Button("Create Item");
		vendorHomeBtn = new Button("Go to Vendor Home Page");
		
		scene = new Scene(bp, 600, 600);
	}
	
	private void setLayout() {
		formContainer.getChildren().addAll(titleLabel, nameLabel, nameTF, descLabel, descTF, priceLabel, priceSpinner, messageLabel, createItemBtn, vendorHomeBtn);
		formContainer.setMaxWidth(300);
		formContainer.setAlignment(Pos.CENTER);
		
		nameTF.setPromptText("Insert item name");
		descTF.setPromptText("Insert item description");
		
		bp.setCenter(formContainer);
	}
	
	private void actions(Stage stage, User user){
		CreateItemController.createItemBtnHandle(createItemBtn, stage, user);
		CreateItemController.vendorHomeBtnHandle(vendorHomeBtn, stage, user);
		}
		
	public VendorCreateItemPage(Stage stage, User user) {
		// TODO Auto-generated constructor stub
		this.con = Connect.getInstance();
		this.user = user;
		
		initialize();
		setLayout();
		actions(stage, user);
		
		stage.setScene(scene);
	}

}
