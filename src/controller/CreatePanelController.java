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

public class CreatePanelController {
	
	public static void influencerHomeBtnHandle(Button influencerHomeBtn, Stage stage, User user) {
		influencerHomeBtn.setOnMouseClicked(e -> {
			NavigateController.navigateInfluencerHomePage(stage, user);
		});
	}
	
	public static void createPanelBtnHandle(Button createPanelBtn, Stage stage, User user
			) {
		createPanelBtn.setOnMouseClicked(e -> {
			String title = InfluencerCreatePanelPage.panelTitleTF.getText();
			String desc = InfluencerCreatePanelPage.descTF.getText();
			String location = InfluencerCreatePanelPage.locationTF.getText();
			
			LocalDate startDate = InfluencerCreatePanelPage.startTimeDP.getValue();
			Integer startHour = InfluencerCreatePanelPage.startHourSpinner.getValue();
			Integer startMinute = InfluencerCreatePanelPage.startMinuteSpinner.getValue();
			LocalDateTime startTime = LocalDateTime.of(startDate, LocalTime.of(startHour, startMinute));
			
			LocalDate endDate = InfluencerCreatePanelPage.endTimeDP.getValue();
			Integer endHour = InfluencerCreatePanelPage.endHourSpinner.getValue();
			Integer endMinute = InfluencerCreatePanelPage.endMinuteSpinner.getValue();
			LocalDateTime endTime = LocalDateTime.of(endDate, LocalTime.of(endHour, endMinute));
			
			Integer userID = user.getUserID();
			
			InfluencerCreatePanelPage.messageLabel.setText( PanelController.addPanel(title, desc, location, startTime, endTime, userID));				
			if(InfluencerCreatePanelPage.messageLabel.getText().equals("Success!")) {
				NavigateController.navigateInfluencerHomePage(stage, user);
			}
		});
	}
	

	public CreatePanelController() {
		// TODO Auto-generated constructor stub
	}

}
