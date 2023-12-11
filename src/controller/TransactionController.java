package controller;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Item;
import model.Transaction;
import view.FanVendorItemsPage;

public class TransactionController {
	public static ObservableList<Transaction> getTransactionByFan(Integer userId) {
	    return Transaction.getTransactionByFan(userId);
	}
	
	public static void addTransaction(Integer userId, Integer itemId) {
	    FanVendorItemsPage.buyButton.setOnAction(event -> {
	        try {
	            Integer quantity = FanVendorItemsPage.quantitySpinner.getValue();

	            // Call addTransaction with the retrieved quantity
	            Transaction.addTransaction(userId, itemId, quantity);
	        } catch (NumberFormatException e) {
	            // Handle invalid quantity input
	            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
	            errorAlert.setTitle("Error");
	            errorAlert.setHeaderText(null);
	            errorAlert.setContentText("Invalid quantity input. Please enter a valid number.");
	            errorAlert.showAndWait();
	        }
	    });
	}

}
