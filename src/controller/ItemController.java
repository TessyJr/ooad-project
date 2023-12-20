package controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Connect;
import model.Item;
import model.Panel;
import model.Transaction;
import model.User;
import view.FanVendorItemsPage;
import view.InfluencerCreatePanelPage;
import view.RegisterPage;

public class ItemController {
	private static Connect con = Connect.getInstance();

	public static void DeleteItem(Integer id) {
		  Item.DeleteItem(id);
		 }
	
	public static ObservableList<Item> getAllItemByVendor(Integer id){
    	return Item.getAllItemByVendor(id);
    }
	
	public static void createNewItemBtnHandle(Button createNewItemBtn, Stage stage, User user) {
		createNewItemBtn.setOnMouseClicked(e -> {
			NavigateController.navigateCreateItemPage(stage, user);
		});
	}
	
	public static void updateItemBtnHandle(Button updateButton,  Stage stage,Item item, User user) {
		updateButton.setOnMouseClicked(e -> {
			NavigateController.navigateUpdateItemPage( stage,item, user);
		});
	}
	
	public static void homePageBtnHandle(Button createNewItemBtn, Stage stage, User user) {
		createNewItemBtn.setOnMouseClicked(e -> {
			NavigateController.navigateFanHomePage(stage, user);
		});
	}
	
	public static void fanTransactionHistoryPageBtnHandle(Button createNewItemBtn, Stage stage, User user) {
		createNewItemBtn.setOnMouseClicked(e -> {
			NavigateController.navigateFanTransactionHistoryPage(stage, user);
		});
	}
	
//	function berisi validasi dari atribut item
	public static String validateItemAttributes(String itemName, String itemDescription, double price) {
        if (itemName == null || itemName.isEmpty()) {
            return "Item Name must be filled.";
        }

        if (itemDescription == null || itemDescription.isEmpty()) {
            return "Item Description must be filled.";
        }
        if (itemDescription.length() > 250) {
            return "Item Description must be maximum 250 characters.";
        }

        if (price <= 0) {
            return "Price must be above 0.";
        }

        return "Success!";
    }

//	function untuk menambahkan item dengan memanggil function validasi item sesuai dengan atributnya
	public static String addItem(String name, String desc, Integer price, Integer userID) {
		String validationError = validateItemAttributes(name, desc, price);
        if (!validationError.equals("Success!")) {
            return validationError;
        }

        Item.addItem(name, desc, price, userID);

        return "Success!";
	}
	
//	function untuk update item beserta functino validasi item
	public static String updateItem(Integer itemId, String name, String desc, Integer price) {
		String validationError = validateItemAttributes(name, desc, price);
        if (!validationError.equals("Success!")) {
            return validationError;
        }

        Item.updateItem(itemId, name, desc, price);

        return "Success!";
	}
	
	public ItemController() {
		// TODO Auto-generated constructor stub
	}

}
