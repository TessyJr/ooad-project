package controller;

import java.sql.Date;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Connect;
import model.Panel;
import model.User;

public class PanelController {
	private static Connect con = Connect.getInstance();
	
	public static String addPanel(Integer panelID, String title, String desc, String location, Date start,
			Date end, Boolean isFinished, User user) {
		
//        String validationError = validateUserAttributes(username, email, password, confirmPassword, role);
//        if (!validationError.equals("Success!")) {
//            return validationError;
//        }
		
		Integer userId = user.getUserID();

		Panel.addPanel(title, desc, location, start, end,  userId);

        return "Success!";
    }
	
	public static void createPanelBtnHandle(Button createPanelBtn, Stage stage, User user) {
		createPanelBtn.setOnMouseClicked(e -> {
			NavigateController.navigateCreatePanelPage(stage, user);
		});
	}

	public PanelController() {
		// TODO Auto-generated constructor stub
	}

}
