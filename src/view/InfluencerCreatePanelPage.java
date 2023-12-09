package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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

public class InfluencerCreatePanelPage {
	Connect con;
	User user;
	
	Scene scene;
	BorderPane bp;
	
	VBox formContainer;
	public static TextField panelTitleTF, descTF, locationTF;
	public static DatePicker startTimeDP, endTimeDP;
	
	public static Label titleLabel, panelTitleLabel, descLabel, locationLabel, startTimeLabel, endTimeLabel, messageLabel;

     // Create a Spinner for selecting hour
     public static Spinner<Integer> startHourSpinner, endHourSpinner;

     // Create a Spinner for selecting minute
     public static Spinner<Integer> startMinuteSpinner, endMinuteSpinner;
	
	public static Button createPanelBtn, influencerHomeBtn;
	
	private void initialize() {
		bp = new BorderPane();
		
		formContainer = new VBox();
		
		// Labels
		titleLabel = new Label("CREATE PANEL PAGE");
        panelTitleLabel = new Label("Title:");
        descLabel = new Label("Description:");
        locationLabel = new Label("Location:");
        startTimeLabel = new Label("Start Time::");
        endTimeLabel = new Label("End Time:");
        messageLabel = new Label("");

        // TextFields
        panelTitleTF = new TextField();
        descTF = new TextField();
        locationTF = new TextField();
        startTimeDP = new DatePicker(LocalDate.now());
        endTimeDP = new DatePicker(LocalDate.now());
        
        // Spinner
        startHourSpinner = new Spinner<>(0, 23, 12);
        endHourSpinner = new Spinner<>(0, 23, 12);
        startMinuteSpinner = new Spinner<>(0, 59, 0);
        endMinuteSpinner = new Spinner<>(0, 59, 0);
		
		createPanelBtn = new Button("Create Panel");
		influencerHomeBtn = new Button("Go to Influencer Home Page");
		
		scene = new Scene(bp, 600, 600);
	}
	
	private void setLayout() {
		formContainer.getChildren().addAll(titleLabel, panelTitleLabel, panelTitleTF, descLabel, descTF, locationLabel, locationTF, startTimeLabel, startTimeDP, startHourSpinner, startMinuteSpinner, endTimeLabel, endTimeDP, endHourSpinner, endMinuteSpinner, messageLabel, createPanelBtn, influencerHomeBtn);
		formContainer.setMaxWidth(300);
		formContainer.setAlignment(Pos.CENTER);
		
		panelTitleTF.setPromptText("Insert panel title");
		descTF.setPromptText("Insert panel description");
		locationTF.setPromptText("Insert panel location");
		
		bp.setCenter(formContainer);
	}
	
	private void actions(Stage stage, User user){
		CreatePanelController.createPanelBtnHandle(createPanelBtn, stage, user);
		CreatePanelController.influencerHomeBtnHandle(influencerHomeBtn, stage, user);
		}
		
	public InfluencerCreatePanelPage(Stage stage, User user) {
		// TODO Auto-generated constructor stub
		this.con = Connect.getInstance();
		this.user = user;
		
		initialize();
		setLayout();
		actions(stage, user);
		
		stage.setScene(scene);
	}

}
