package controller;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Item;
import model.Transaction;
import view.FanVendorItemsPage;

public class TransactionController {
//	function untuk mendapatkan transaksi sesuai dengan fan
	public static ObservableList<Transaction> getTransactionByFan(Integer userId) {
	    return Transaction.getTransactionByFan(userId);
	}
	
//	function untuk dapat menambahkan transaksi sesuai dengan quantity yang diinput fan
	public static void addTransaction(Integer userId, Integer itemId) {
	    FanVendorItemsPage.buyButton.setOnAction(event -> {
	        try {
	            Integer quantity = FanVendorItemsPage.quantitySpinner.getValue();

	            Transaction.addTransaction(userId, itemId, quantity);
	            FanVendorItemsPage.messageLabel = new Label("Thankyou for purchasing!");
	            FanVendorItemsPage.detailsbox.getChildren().add(FanVendorItemsPage.messageLabel);
	            
	        } catch (NumberFormatException e) {

	            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
	            errorAlert.setTitle("Error");
	            errorAlert.setHeaderText(null);
	            errorAlert.setContentText("Invalid quantity input. Please enter a valid number.");
	            errorAlert.showAndWait();
	        }
	    });
	}

}
