package view;

import java.util.List;

import controller.ItemController;
import controller.PanelController;
import controller.TransactionController;
import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Connect;
import model.Item;
import model.Transaction;
import model.User;

public class FanTransactionHistoryPage {
    Connect con;
    User user;

    Scene scene;
    BorderPane bp;

    VBox formContainer;

    Label titleLabel, subtitleLabel;
    Button fanVendorPageBtn, deleteBtn;

    List<Transaction> transactionList;
    ObservableList<Transaction> observableList;

    TableView<Transaction> tableView;
    TableColumn<Transaction, String> itemNameColumn;
    TableColumn<Transaction, Integer> priceColumn;
    TableColumn<Transaction, Integer> quantityColumn;

    private void initialize(Stage stage) {
        tableView = new TableView<>();
        itemNameColumn = new TableColumn<>("ItemName");
        priceColumn = new TableColumn<>("Price");
        quantityColumn = new TableColumn<>("Quantity");

        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));

        tableView.getColumns().addAll(itemNameColumn, priceColumn, quantityColumn);

        // Fetch the transaction history for the user
        transactionList = TransactionController.getTransactionByFan(user.getUserID());
        observableList = FXCollections.observableArrayList(transactionList);
        tableView.setItems(observableList);

        bp = new BorderPane();

        formContainer = new VBox();

        titleLabel = new Label("FAN PAGE");
        subtitleLabel = new Label("TRANSACTION HISTORY");

        fanVendorPageBtn = new Button("Go to Vendor List");

        scene = new Scene(bp, 600, 600);
    }

    private void setLayout() {
        formContainer.getChildren().addAll(titleLabel, subtitleLabel, tableView, fanVendorPageBtn);
        formContainer.setMaxWidth(300);
        formContainer.setAlignment(Pos.CENTER);

        bp.setCenter(formContainer);
    }

    private void actions(Stage stage) {
    	PanelController.fanVendorPageBtnHandle(fanVendorPageBtn, stage, user);
    }

    public FanTransactionHistoryPage(Stage stage, User user) {
        this.con = Connect.getInstance();
        this.user = user;

        initialize(stage);
        setLayout();
        actions(stage);

        stage.setScene(scene);
    }
}
