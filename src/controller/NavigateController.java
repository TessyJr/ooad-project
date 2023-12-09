package controller;

import javafx.stage.Stage;
import model.User;
import view.AdminDashboardPage;
import view.AdminFansPage;
import view.AdminInfluencersPage;
import view.AdminVendorsPage;
import view.InfluencerCreatePanelPage;
import view.InfluencerHomePage;
import view.LoginPage;
import view.RegisterPage;
import view.VendorCreateItemPage;
import view.VendorHomePage;

public class NavigateController {
	
	public static void navigateAdminDashbaord(Stage stage) {
		new AdminDashboardPage(stage);
	}
	
	public static void navigateRegister(Stage stage) {
		new RegisterPage(stage);
	}
	
	public static void navigateLogin(Stage stage) {
		new LoginPage(stage);
	}
	
	public static void navigateAdminFans(Stage stage) {
		new AdminFansPage(stage);
	}
	
	public static void navigateAdminVendors(Stage stage) {
		new AdminVendorsPage(stage);
	}

	public static void navigateAdminInfleuncers(Stage stage) {
		new AdminInfluencersPage(stage);
	}
	
	public static void navigateInfluencerHomePage(Stage stage, User user) {
		new InfluencerHomePage(stage, user);
	}
	
	public static void navigateCreatePanelPage(Stage stage, User user) {
		new InfluencerCreatePanelPage(stage, user);
	}
	
	public static void navigateVendorHomePage(Stage stage, User user) {
		new VendorHomePage(stage, user);
	}
	
	public static void navigateCreateItemPage(Stage stage, User user) {
		new VendorCreateItemPage(stage, user);
	}

	public NavigateController() {
		// TODO Auto-generated constructor stub
	}

}
