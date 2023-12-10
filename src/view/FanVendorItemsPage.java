package view;

import java.util.List;

import controller.ItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.Connect;
import model.Item;
import model.User;

public class FanVendorItemsPage {
    Connect con;
    User user;

    Scene scene;
    BorderPane bp;

    VBox formContainer;

    Label titleLabel;
    Button createNewItemBtn;
    public static Button buyButton;
    public static Spinner<Integer> quantitySpinner;

    List<Item> itemList;
    ObservableList<Item> observableList;

    TableView<Item> tableView;
    TableColumn<Item, Integer> itemIdColumn;
    TableColumn<Item, String> nameColumn;
    TableColumn<Item, Void> viewColumn;

    private void initialize(Stage stage) {
        tableView = new TableView<>();
        itemIdColumn = new TableColumn<>("ItemID");
        nameColumn = new TableColumn<>("ItemName");
        viewColumn = new TableColumn<>("View");

        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("ItemID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        viewColumn.setCellFactory(getButtonCell(stage));

        tableView.getColumns().addAll(itemIdColumn, nameColumn, viewColumn);

        itemList = ItemController.getAllItemByVendor(user.getUserID());
        observableList = FXCollections.observableArrayList(itemList);
        tableView.setItems(observableList);

        bp = new BorderPane();

        formContainer = new VBox();

        titleLabel = new Label("VENDOR HOME PAGE");

        createNewItemBtn = new Button("Create New Item");

        scene = new Scene(bp, 600, 600);
    }

    private void setLayout() {
        formContainer.getChildren().addAll(titleLabel, tableView, createNewItemBtn);
        formContainer.setMaxWidth(300);
        formContainer.setAlignment(Pos.CENTER);

        bp.setCenter(formContainer);
    }

    private void actions(Stage stage, User user) {
        ItemController.createNewItemBtnHandle(createNewItemBtn, stage, user);
    }

    public FanVendorItemsPage(Stage stage, User user) {
        this.con = Connect.getInstance();
        this.user = user;

        initialize(stage);
        setLayout();
        actions(stage, user);

        stage.setScene(scene);
    }

    private Callback<TableColumn<Item, Void>, TableCell<Item, Void>> getButtonCell(Stage stage) {
        return new Callback<TableColumn<Item, Void>, TableCell<Item, Void>>() {
            @Override
            public TableCell<Item, Void> call(final TableColumn<Item, Void> param) {
                final TableCell<Item, Void> cell = new TableCell<Item, Void>() {
                    private final Button viewButton = new Button("View");

                    {
                        viewButton.setOnAction(event -> {
                            Item item = getTableView().getItems().get(getIndex());
                            showItemDetailsPopup(stage, item);
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

	private void showItemDetailsPopup(Stage stage, Item item) {
	    // Create the details popup
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.initModality(Modality.APPLICATION_MODAL);
//	    alert.initModality(Modality.WINDOW_MODAL);
	    alert.initOwner(stage);
	    alert.setTitle("Item Details");
	
	    // Create JavaFX controls for item details
	    VBox detailsBox = new VBox();
	    Label nameLabel = new Label("Name: " + item.getItemName());
	    Label descLabel = new Label("Description: " + item.getItemDescription());
	    Label priceLabel = new Label("Price: " + item.getPrice());
	
	    quantitySpinner = new Spinner<>(0, Integer.MAX_VALUE, 1);
	
	    // Buy button
	    buyButton = new Button("Buy");
	    ItemController.addTransaction(user.getUserID(), item.getItemID());
	    
	    // Add controls to the details box
	    detailsBox.getChildren().addAll(nameLabel, descLabel, priceLabel, quantitySpinner, buyButton);
	
	    // Set the content of the alert
	    alert.getDialogPane().setContent(detailsBox);
	
	    // Show the popup
	    alert.showAndWait();
	}
}