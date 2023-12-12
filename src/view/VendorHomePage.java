package view;

import java.sql.Date;
import java.util.List;

import controller.AdminController;
import controller.ItemController;
import controller.PanelController;
import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import model.Item;
import model.Panel;
import model.User;

public class VendorHomePage {
    Connect con;
    User user;

    Scene scene;
    BorderPane bp;

    VBox formContainer;

    Label titleLabel;
    Button createNewItemBtn, logoutBtn;
    
    
    List<Item> itemList;
    ObservableList<Item> observableList;

    TableView<Item> tableView;
    TableColumn<Item, Integer> itemIdColumn;
    TableColumn<Item, String> nameColumn;
    TableColumn<Item, String> descColumn;
    TableColumn<Item, Integer> priceColumn;
    TableColumn<Item, Void> updateColumn;
    TableColumn<Item, Button> deleteColumn;
    
    private Callback<TableColumn<Item, Void>, TableCell<Item, Void>> getUpdateButtonCell(Stage stage) {
        return new Callback<TableColumn<Item, Void>, TableCell<Item, Void>>() {
            @Override
            public TableCell<Item, Void> call(final TableColumn<Item, Void> param) {
                return new TableCell<Item, Void>() {
                	
                	private final Button updateButton = new Button("Update");
                    {
                        updateButton.setOnAction((ActionEvent event) -> {
                            Item item = getTableView().getItems().get(getIndex());
                            ItemController.updateItemBtnHandle(updateButton, stage, item, user);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(updateButton);
                        }
                    }
                };
            }
        };
    }


    private void initialize(Stage stage) {
        tableView = new TableView<>();
        itemIdColumn = new TableColumn<>("ItemID");
        nameColumn = new TableColumn<>("ItemName");
        descColumn = new TableColumn<>("ItemDescription");
        priceColumn = new TableColumn<>("Price");
        updateColumn = new TableColumn<>("Update");
        deleteColumn = new TableColumn<>("Delete");

        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("ItemID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("ItemDescription"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));

        updateColumn.setCellFactory(getUpdateButtonCell(stage));

        deleteColumn.setCellFactory(column -> {
            return new TableCell<Item, Button>() {
                private final Button deleteButton = new Button("Delete");

                {
                    deleteButton.setOnAction((ActionEvent event) -> {
                        Item item = getTableView().getItems().get(getIndex());
                        ItemController.DeleteItem(item.getItemID());
getTableView().getItems().remove(item);
                        getTableView().refresh();
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

        tableView.getColumns().addAll(itemIdColumn, nameColumn, descColumn, priceColumn, updateColumn, deleteColumn);

        itemList = ItemController.getAllItemByVendor(user.getUserID());
        observableList = FXCollections.observableArrayList(itemList);
        tableView.setItems(observableList);
        
        logoutBtn = new Button("Logout");

        bp = new BorderPane();

        formContainer = new VBox();

        titleLabel = new Label("VENDOR HOME PAGE");

        createNewItemBtn = new Button("Create New Item");

        scene = new Scene(bp, 600, 600);
    }
	
	private void setLayout() {
		formContainer.getChildren().addAll(titleLabel, tableView, createNewItemBtn, logoutBtn);
		formContainer.setMaxWidth(300);
		formContainer.setAlignment(Pos.CENTER);
		
		bp.setCenter(formContainer);
	}
	
	private void actions(Stage stage, User user){
		ItemController.createNewItemBtnHandle(createNewItemBtn, stage, user);
		UserController.logoutBtnHandle(logoutBtn, stage);
	}
	
	public VendorHomePage(Stage stage, User user) {
		// TODO Auto-generated constructor stub
		this.con = Connect.getInstance();
		this.user = user;
		
		initialize(stage);
		setLayout();
		actions(stage, user);
		
		stage.setScene(scene);
	}

}
