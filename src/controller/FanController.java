package controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.User;

public class FanController {
 
 public static void fanHomePageBtnHandle(Button fanHomePageBtn, Stage stage, User user) {
  fanHomePageBtn.setOnMouseClicked(e -> {
   NavigateController.navigateFanHomePage(stage, user);
  });
 }
 
 public static void fanVendorListBtnHandle(Button fanVendorListBtn, Stage stage, User user) {
  fanVendorListBtn.setOnMouseClicked(e -> {
   NavigateController.navigateFanVendorPage(stage, user);
  });
 }

 public FanController() {
  // TODO Auto-generated constructor stub
 }

}