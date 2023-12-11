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

public class InfluencerHomePage {
	Connect con;
    User user;

    Scene scene;
    BorderPane bp;

    VBox formContainer;

    Label titleLabel;
    Button createNewPanelBtn;

    List<Panel> panelList;
    ObservableList<Panel> observableList;

    TableView<Panel> tableView;
    TableColumn<Panel, Integer> panelIdColumn;
    TableColumn<Panel, String> titleColumn;
    TableColumn<Panel, Boolean> isFinishedColumn;
    TableColumn<Panel, Void> viewColumn;
    TableColumn<Panel, Void> finishedColumn;
    
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

        // Add attendees
        List<String> attendees = PanelController.getAllAttendees(panel.getPanelID());
        if (!attendees.isEmpty()) {
            Label attendeesLabel = new Label("Attendees:");
            attendeesLabel.setStyle("-fx-font-weight: bold;");
            detailsBox.getChildren().add(attendeesLabel);

            for (String attendee : attendees) {
                detailsBox.getChildren().add(new Label("- " + attendee));
            }
        } else {
            detailsBox.getChildren().add(new Label("No attendees."));
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
                            setGraphic(viewButton);
                        }
                    }
                };
                return cell;
            }
        };
    }
    
    private Callback<TableColumn<Panel, Void>, TableCell<Panel, Void>> getFinishedButtonCell() {
        return new Callback<TableColumn<Panel, Void>, TableCell<Panel, Void>>() {
            @Override
            public TableCell<Panel, Void> call(final TableColumn<Panel, Void> param) {
                return new TableCell<Panel, Void>() {

                    private final Button finishedButton = new Button("Finished");

                    {
                        finishedButton.setOnAction((ActionEvent event) -> {
                            Panel panel = getTableView().getItems().get(getIndex());
                            PanelController.finishPanel(panel.getPanelID());
                            panelList = PanelController.getAllPanelByInfluencer(user.getUserID());
                            observableList = FXCollections.observableArrayList(panelList);
                            tableView.setItems(observableList);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Panel panel = getTableView().getItems().get(getIndex());
                            if (panel.getIsFinished()) {
                                // Panel is finished, hide the button
                                setGraphic(null);
                            } else {
                                // Panel is not finished, show the button
                                setGraphic(finishedButton);
                            }
                        }
                    }
                };
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

        finishedColumn = new TableColumn<>("Finished");
        finishedColumn.setCellFactory(getFinishedButtonCell());

        tableView.getColumns().addAll(panelIdColumn, titleColumn, isFinishedColumn, viewColumn, finishedColumn);

        panelList = PanelController.getAllPanelByInfluencer(user.getUserID()); 
        observableList = FXCollections.observableArrayList(panelList);
        tableView.setItems(observableList);
		
		bp = new BorderPane();
		
		formContainer = new VBox();
		
		titleLabel = new Label("INFLUENCER HOME PAGE");

		createNewPanelBtn = new Button("Create New Panel");
		
		scene = new Scene(bp, 600, 600);
	}
	
	private void setLayout() {
		formContainer.getChildren().addAll(titleLabel, tableView, createNewPanelBtn);
		formContainer.setMaxWidth(300);
		formContainer.setAlignment(Pos.CENTER);
		
		bp.setCenter(formContainer);
	}
	
	private void actions(Stage stage, User user){
		PanelController.createNewPanelBtnHandle(createNewPanelBtn, stage, user);
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
