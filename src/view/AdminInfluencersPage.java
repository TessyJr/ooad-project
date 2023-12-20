package view;

import java.util.List;

import controller.AdminController;
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

public class AdminInfluencersPage {
	Connect con;
	
	Scene scene;
	BorderPane bp;
	
	VBox formContainer;

	Label titleLabel;
	Button adminDashboardBtn, deleteBtn;
	
	List<User> fanList;
	ObservableList<User> observableList;
	
	TableView<User> tableView;
	TableColumn<User, Integer> userIdColumn;
	TableColumn<User, String> usernameColumn;
	TableColumn<User, String> emailColumn;
	TableColumn<User, String> passwordColumn;
	TableColumn<User, Button> deleteColumn;
	
	private void initialize(Stage stage) {
		tableView = new TableView<>();
		userIdColumn = new TableColumn<>("UserID");
        usernameColumn = new TableColumn<>("Username");
        emailColumn = new TableColumn<>("Email");
        passwordColumn = new TableColumn<>("Password");
        deleteColumn = new TableColumn<>("Delete");

        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("Password"));
        
//        kolom delete user
        deleteColumn.setCellFactory(column -> {
            return new TableCell<User, Button>() {
                private final Button deleteButton = new Button("Delete");
                
                {
                    deleteButton.setOnAction((ActionEvent event) -> {
                        User user = getTableView().getItems().get(getIndex());
                        UserController.deleteInfluencer(user.getUserID());
                        
                        new AdminInfluencersPage(stage);
                    });
                }

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }

            };
            
            
        });
        
        tableView.getColumns().addAll(userIdColumn, usernameColumn, emailColumn, passwordColumn, deleteColumn);

        fanList = UserController.getAllUsersInRole("Influencer"); 
        observableList = FXCollections.observableArrayList(fanList);
        tableView.setItems(observableList);
		
		bp = new BorderPane();
		
		formContainer = new VBox();
		
		titleLabel = new Label("ADMIN INFLUENCERS PAGE");

		adminDashboardBtn = new Button("Go back to Admin Dashboard");
		
		scene = new Scene(bp, 600, 600);
	}
	
	private void setLayout() {
		formContainer.getChildren().addAll(titleLabel, tableView, adminDashboardBtn);
		formContainer.setMaxWidth(300);
		formContainer.setAlignment(Pos.CENTER);
		
		bp.setCenter(formContainer);
	}
	
	private void actions(Stage stage){
		AdminController.adminDashboardBtnHandle(adminDashboardBtn, stage);
	}
	
	public AdminInfluencersPage(Stage stage) {
		// TODO Auto-generated constructor stub
		this.con = Connect.getInstance();
		
		initialize(stage);
		setLayout();
		actions(stage);
		
		stage.setScene(scene);
	}

}
