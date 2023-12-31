package controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Connect;
import model.Panel;
import model.User;
import view.FanHomePage;
import view.InfluencerCreatePanelPage;
import view.RegisterPage;

public class PanelController {
	private static Connect con = Connect.getInstance();
	
	public static void finishPanel(int panelID) {
	   Panel.finishPanel(panelID);
	}

	public static ObservableList<Panel> getAllPanelByInfluencer(Integer id){
    	return Panel.getAllPanelByInfluencer(id);
    }
	
	public static ObservableList<Panel> getAllPanels(){
    	return Panel.getAllPanels();
    }
	
	public static void createNewPanelBtnHandle(Button createNewPanelBtn, Stage stage, User user) {
		createNewPanelBtn.setOnMouseClicked(e -> {
			NavigateController.navigateCreatePanelPage(stage, user);
		});
	}
	
	public static void fanVendorPageBtnHandle(Button fanVendorPageBtn, Stage stage, User user) {
		fanVendorPageBtn.setOnMouseClicked(e -> {
			NavigateController.navigateFanVendorPage(stage, user);
		});
	}
	
//	function untuk mereturn validasi error pada atribut panel
	private static String validatePanelAttributes(String title, String desc, String location, LocalDateTime start, LocalDateTime end) {
	    if (title.isEmpty()) {
	        return "Panel Title must be filled.";
	    }

	    if (desc.isEmpty()) {
	        return "Panel Description must be filled.";
	    }
	    if (desc.length() > 250) {
	        return "Panel Description must be maximum 250 characters.";
	    }

	    if (location.split("\\s+").length < 2) {
	        return "Location must have at least 2 words.";
	    }

	    if (start == null || end == null) {
	        return "Start Time and End Time cannot be null.";
	    }

	    LocalTime startHourOfDay = start.toLocalTime();
	    if (startHourOfDay.isBefore(LocalTime.of(9, 0)) || startHourOfDay.isAfter(LocalTime.of(21, 0))) {
	        return "Start Time must be between 09:00 and 21:00.";
	    }

	    LocalTime endHourOfDay = end.toLocalTime();
	    if (endHourOfDay.isBefore(LocalTime.of(9, 0)) || endHourOfDay.isAfter(LocalTime.of(23, 0))) {
	        return "End Time must be between 09:00 and 23:00.";
	    }

	    if (end.isBefore(start)) {
	        return "End Time must be after Start Time.";
	    }

	    return "Success!";
	}
	
//	function untuk memanggil validasi panel saat create panel
	public static String addPanel(String title, String desc, String location, LocalDateTime start, LocalDateTime end, Integer userID) {
		String validationError = validatePanelAttributes(title, desc, location, start, end);
        if (!validationError.equals("Success!")) {
            return validationError;
        }

        Panel.addPanel(title, desc, location, start, end, userID);

        return "Success!";
	}
	
//	function untuk fan dapat attend panel yang tersedia
	public static void addAttendee(Integer panelId, Integer userId) {
		FanHomePage.attendBtn.setOnAction(e -> {
	        Panel.addAttendee(panelId, userId);
//	        setelah di klik maka button akan berubah
	        FanHomePage.attendBtn.setText("Panel already attended");
            FanHomePage.attendBtn.setDisable(true);
	    });
	}
	
	public static boolean hasAttended(Integer panelId, Integer userId) {
	    return Panel.hasAttended(panelId, userId);
	}
	
	public static ObservableList<String> getAllAttendees(Integer panelId) {
	    return Panel.getAllAttendees(panelId);
	}

	public PanelController() {
		// TODO Auto-generated constructor stub
	}

}
