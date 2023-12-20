package view;

import java.sql.Date;
import java.util.List;

import controller.AdminController;
import controller.PanelController;
import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.Connect;
import model.Panel;
import model.User;

public class FanHomePage {
	Connect con;
    User user;

    Scene scene;
    BorderPane bp;

    VBox formContainer;

    Label titleLabel, subtitleLabel;
    Button fanVendorPageBtn, logoutBtn;
    public static Button attendBtn, alreadyAttendedBtn;

    List<Panel> panelList;
    ObservableList<Panel> observableList;

    TableView<Panel> tableView;
    TableColumn<Panel, Integer> panelIdColumn;
    TableColumn<Panel, String> titleColumn;
    TableColumn<Panel, Boolean> isFinishedColumn;
    TableColumn<Panel, Void> viewColumn;
    
//    function untuk memunculkan modal pop up
	private void showPanelDetailsPopup(Stage stage, Panel panel) {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.initModality(Modality.APPLICATION_MODAL);
	    alert.initOwner(stage);
	    alert.setTitle("Panel Details");
	
	    VBox detailsBox = new VBox();
	    detailsBox.getChildren().add(new Label("Title: " + panel.getPanelTitle()));
	    detailsBox.getChildren().add(new Label("Description: " + panel.getPanelDescription()));
	    detailsBox.getChildren().add(new Label("Location: " + panel.getLocation()));
	    detailsBox.getChildren().add(new Label("Start Time: " + panel.getStartTime()));
	    detailsBox.getChildren().add(new Label("End Time: " + panel.getEndTime()));
	
	    if (Panel.hasAttended(panel.getPanelID(), user.getUserID())) {
	        alreadyAttendedBtn = new Button("Panel already attended");
	        detailsBox.getChildren().add(alreadyAttendedBtn);
	        alreadyAttendedBtn.setDisable(true);
	    } else {
	        attendBtn = new Button("Attend");
	        PanelController.addAttendee(panel.getPanelID(), user.getUserID());
	        
	        detailsBox.getChildren().add(attendBtn);
	    }
	
	    alert.getDialogPane().setContent(detailsBox);
	
	    alert.showAndWait();
	}
	
	
    private Callback<TableColumn<Panel, Void>, TableCell<Panel, Void>> getButtonCell(Stage stage) {
    return new Callback<TableColumn<Panel, Void>, TableCell<Panel, Void>>() {
        @Override
        public TableCell<Panel, Void> call(final TableColumn<Panel, Void> param) {
            final TableCell<Panel, Void> cell = new TableCell<Panel, Void>() {

                private final Button viewButton = new Button("View");

                {
                    viewButton.setOnAction((ActionEvent event) -> {
                        Panel panel = getTableView().getItems().get(getIndex());
                        showPanelDetailsPopup(stage, panel);
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        Panel panel = getTableView().getItems().get(getIndex());
                        if (!panel.getIsFinished()) {
                            setGraphic(viewButton);
                        } else {
                            setGraphic(null);
                        }
                    }
                }
            };
            return cell;
        }
    };
}
   
   
	private void initialize(Stage stage) {
		tableView = new TableView<>();
		panelIdColumn = new TableColumn<>("PanelID");
        titleColumn = new TableColumn<>("Title");
        isFinishedColumn = new TableColumn<>("IsFinished");

        panelIdColumn.setCellValueFactory(new PropertyValueFactory<>("PanelID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("PanelTitle"));
        isFinishedColumn.setCellValueFactory(new PropertyValueFactory<>("IsFinished"));
        viewColumn = new TableColumn<>("View");
        viewColumn.setCellFactory(getButtonCell(stage));

        tableView.getColumns().addAll(panelIdColumn, titleColumn, isFinishedColumn, viewColumn);

        panelList = PanelController.getAllPanels();
        observableList = FXCollections.observableArrayList(panelList);
        tableView.setItems(observableList);
        
        logoutBtn = new Button("Logout");
		
		bp = new BorderPane();
		
		formContainer = new VBox();
		
		titleLabel = new Label("FAN PAGE");
		subtitleLabel = new Label("PANEL LIST");

		fanVendorPageBtn = new Button("Go to Vendor List");
		
		scene = new Scene(bp, 600, 600);
	}
	
	private void setLayout() {
		formContainer.getChildren().addAll(titleLabel, subtitleLabel, tableView, fanVendorPageBtn, logoutBtn);
		formContainer.setMaxWidth(300);
		formContainer.setAlignment(Pos.CENTER);
		
		bp.setCenter(formContainer);
	}
	
	private void actions(Stage stage, User user){
		PanelController.fanVendorPageBtnHandle(fanVendorPageBtn, stage, user);
		UserController.logoutBtnHandle(logoutBtn, stage);
	}
	
	public FanHomePage(Stage stage, User user) {
		// TODO Auto-generated constructor stub
		this.con = Connect.getInstance();
		this.user = user;
		
		initialize(stage);
		setLayout();
		actions(stage, user);
		
		stage.setScene(scene);
	}

}
