package view;

import java.util.List;

import controller.AdminController;
import controller.ItemController;
import controller.NavigateController;
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

public class FanVendorPage {
	Connect con;
	User user;
	
	Scene scene;
	BorderPane bp;
	
	VBox formContainer;

	Label titleLabel, subtitleLabel;
	Button fanHomePageBtn, deleteBtn, fanTransactionHistoryPageBtn;
	
	List<User> vendorList;
	ObservableList<User> observableList;
	
	TableView<User> tableView;
	TableColumn<User, Integer> userIdColumn;
	TableColumn<User, String> usernameColumn;
	TableColumn<User, Button> viewItemsColumn;
	
	private void initialize(Stage stage) {
		tableView = new TableView<>();
		userIdColumn = new TableColumn<>("UserID");
        usernameColumn = new TableColumn<>("Username");
        viewItemsColumn = new TableColumn<>("View Items");

        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));
        
        viewItemsColumn.setCellFactory(column -> {
            return new TableCell<User, Button>() {
                private final Button viewItemsBtn = new Button("View Items");
                
                {
                    viewItemsBtn.setOnAction((ActionEvent event) -> {
                        User vendor = getTableView().getItems().get(getIndex());
                        
                        NavigateController.navigateFanVendorItemsPage(stage, vendor);
                    });
                }

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(viewItemsBtn);
                    }
                }

            };
            
            
        });
        
        tableView.getColumns().addAll(userIdColumn, usernameColumn, viewItemsColumn);

        vendorList = UserController.getAllUsersInRole("Vendor"); 
        observableList = FXCollections.observableArrayList(vendorList);
        tableView.setItems(observableList);
		
		bp = new BorderPane();
		
		formContainer = new VBox();
		
		titleLabel = new Label("FAN PAGE");
		subtitleLabel = new Label("VENDOR LIST");

		fanHomePageBtn = new Button("Go Fan Home Page");
		fanTransactionHistoryPageBtn = new Button("Go Fan Transaction History");
		
		scene = new Scene(bp, 600, 600);
	}
	
	private void setLayout() {
		formContainer.getChildren().addAll(titleLabel, subtitleLabel, tableView, fanHomePageBtn, fanTransactionHistoryPageBtn);
		formContainer.setMaxWidth(300);
		formContainer.setAlignment(Pos.CENTER);
		
		bp.setCenter(formContainer);
	}
	
	private void actions(Stage stage){
		ItemController.homePageBtnHandle(fanHomePageBtn, stage, user);
		ItemController.fanTransactionHistoryPageBtnHandle(fanTransactionHistoryPageBtn, stage, user);
	}
	
	public FanVendorPage(Stage stage, User user) {
		// TODO Auto-generated constructor stub
		this.con = Connect.getInstance();
		this.user = user;
		
		initialize(stage);
		setLayout();
		actions(stage);
		
		stage.setScene(scene);
	}

}