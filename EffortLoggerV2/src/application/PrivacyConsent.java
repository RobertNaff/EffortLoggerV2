package application;

import java.time.LocalTime;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;


public class PrivacyConsent extends Main {
	
	@FXML
	public Button acceptAll;
	@FXML
	public Button rejectAll;
	@FXML
	public CheckBox check;
	
	//if checkbox is checked, agree = true, otherwise false
	private boolean agree = false;
	
	
	@FXML
	public void initialize() {
	}
	
	public void closeScreen() { //closes tutorial screen, and opens up the main screen
		acceptAll.getScene().getWindow().hide();
		Stage mainStage = new Stage();
        try {
            initializeMainScene(mainStage);
        } catch(IOException e) {
            e.printStackTrace();
        }	
}
	
	public void nextScreen() { //closes tutorial screen, and opens up the main screen
		acceptAll.getScene().getWindow().hide();
		Stage mainStage = new Stage();
        try {
            initializeConsole(mainStage);
        } catch(IOException e) {
            e.printStackTrace();
        }
}
	//reminds user to check the terms and conditions
	private void reminder() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Accept Terms and Conditions");
        alert.setHeaderText("You must accept terms and conditions before continuing.");
        alert.show();
	}
	
	//if user rejects the privacy policy, return to sign in page
	public void rejectAllButtonOnAction(ActionEvent e) {
		closeScreen();
	}
	//if user accepts, check that they accepted terms and conditions
	//if they checked the box, open log editor
	//if they did not check the box, pop up screen
	public void acceptAllButtonOnAction(ActionEvent e) {
		if(agree)
			nextScreen();
		else
			reminder();
	}
	public void checkOnAction()
	{
		if(check.isSelected())
			agree = true;
		else
			agree = false;
	}
	

	
	
	
	
	
}
