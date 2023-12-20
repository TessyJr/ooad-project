package controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Panel;
import model.User;
import view.InfluencerCreatePanelPage;
import view.RegisterPage;
import view.VendorCreateItemPage;

public class CreateItemController {
	
	public static void vendorHomeBtnHandle(Button vendorHomeBtn, Stage stage, User user) {
		vendorHomeBtn.setOnMouseClicked(e -> {
			NavigateController.navigateVendorHomePage(stage, user);
		});
	}
	
//	function untuk menambahkan item
	public static void createItemBtnHandle(Button createItemBtn, Stage stage, User user
			) {
		createItemBtn.setOnMouseClicked(e -> {
			String name = VendorCreateItemPage.nameTF.getText();
			String desc = VendorCreateItemPage.descTF.getText();
			
			Integer price = VendorCreateItemPage.priceSpinner.getValue();
			
			Integer userID = user.getUserID();
			
			VendorCreateItemPage.messageLabel.setText( ItemController.addItem(name, desc, price, userID));				
			if(VendorCreateItemPage.messageLabel.getText().equals("Success!")) {
				NavigateController.navigateVendorHomePage(stage, user);
			}
		});
	}
	

	public CreateItemController() {
		// TODO Auto-generated constructor stub
	}

}
