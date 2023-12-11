package view;

import java.util.List;

import controller.ItemController;
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
import model.User;

public class FanTransactionHistoryPage {
    Connect con;
    User user;

    Scene scene;
    BorderPane bp;

    VBox formContainer;

    Label titleLabel, subtitleLabel;
    Button fanHomePageBtn, deleteBtn;

    List<String> transactionList;
    ObservableList<String> observableList;

    TableView<String> tableView;
    TableColumn<String, String> itemNameColumn;
    TableColumn<String, Integer> priceColumn;
    TableColumn<String, Integer> quantityColumn;

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
        transactionList = ItemController.getTransactionByFan(user.getUserID());
        observableList = FXCollections.observableArrayList(transactionList);
        tableView.setItems(observableList);

        bp = new BorderPane();

        formContainer = new VBox();

        titleLabel = new Label("FAN PAGE");
        subtitleLabel = new Label("VIEW TRANSACTION HISTORY");

        fanHomePageBtn = new Button("Go Fan Home Page");

        scene = new Scene(bp, 600, 600);
    }

    private void setLayout() {
        formContainer.getChildren().addAll(titleLabel, subtitleLabel, tableView, fanHomePageBtn);
        formContainer.setMaxWidth(300);
        formContainer.setAlignment(Pos.CENTER);

        bp.setCenter(formContainer);
    }

    private void actions(Stage stage) {
        ItemController.homePageBtnHandle(fanHomePageBtn, stage, user);
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
