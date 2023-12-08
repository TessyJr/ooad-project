package controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.User;

public class AdminController {
	
	public static void fanBtnHandle(Button fanBtn, Stage stage) {
		fanBtn.setOnMouseClicked(e -> {
			NavigateController.navigateAdminFans(stage);
		});
	}
	
	public static void vendorBtnHandle(Button vendorBtn, Stage stage)
	{
		vendorBtn.setOnMouseClicked(e -> {
			NavigateController.navigateAdminVendors(stage);
		});	
	}
	
	public static void influencerBtnHandle(Button influencerBtn, Stage stage)
	{
		influencerBtn.setOnMouseClicked(e -> {
			NavigateController.navigateAdminInfleuncers(stage);
		});
	}
	
	public static void adminDashboardBtnHandle(Button adminDashboardBtn, Stage stage) {
		adminDashboardBtn.setOnMouseClicked(e ->{
			NavigateController.navigateAdminDashbaord(stage);
		});
	}

	public AdminController() {
		// TODO Auto-generated constructor stub
	}

}
