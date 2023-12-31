package controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Item;
import model.User;
import view.VendorCreateItemPage;
import view.VendorUpdateItemPage;

public class UpdateItemController {
	
//	function untuk mengaupdate item pada vendor berdasarkan atribut yang diubah
	public static void updateItemBtnHandle(Button updateItemBtn, Item item, Stage stage, User user) {
		updateItemBtn.setOnMouseClicked(e -> {
			Integer itemId = item.getItemID();
			String name = VendorUpdateItemPage.nameTF.getText();
			String desc = VendorUpdateItemPage.descTF.getText();
			
			Integer price = VendorUpdateItemPage.priceSpinner.getValue();
			
			VendorUpdateItemPage.messageLabel.setText( ItemController.updateItem(itemId, name, desc, price));				
			if(VendorUpdateItemPage.messageLabel.getText().equals("Success!")) {
				NavigateController.navigateVendorHomePage(stage, user);
			}
		});
	}
	
	public static void vendorHomeBtnHandle(Button vendorHomeBtn, Stage stage, User user) {
		vendorHomeBtn.setOnMouseClicked(e -> {
			NavigateController.navigateVendorHomePage(stage, user);
		});
	}

	public UpdateItemController() {
		// TODO Auto-generated constructor stub
	}

}
