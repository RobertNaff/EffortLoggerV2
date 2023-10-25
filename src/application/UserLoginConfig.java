//This file configures UserLogin interface control configurations
package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;

public class UserLoginConfig{
	//load in UserLogin controls
	@FXML
	private Label loginMessageLabel;
	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordPasswordField;
	
	//assign action to when signinButton is pressed
	//if either username or password empty, inform user
	//if both username and password filled with anything, for the sake of producing information, we will move to EffortLogger Console
	public void signinButtonOnAction(ActionEvent e) {
		if(usernameTextField.getText().isBlank() == false && passwordPasswordField.getText().isBlank() == false) {
			
			loginMessageLabel.setText("login success!");
			//I will add EffortLogger Console interactions soon
		} 
		else {
			loginMessageLabel.setText("Please enter login information");
		}
	}

}