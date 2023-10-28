//This file configures UserLogin interface control configurations
package application;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserLoginConfig{
	//load in UserLogin controls
	@FXML
	private Label loginMessageLabel;
	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordPasswordField;
	
	boolean check = true;
	public boolean getCheck() {
		return check;
	}
	public boolean setCheck(boolean check) {
		this.check = check;
		return check;
	}
	String userName;
	String passWord;
	
	//assign action to when signinButton is pressed
	//if either username or password empty, inform user
	//if both username and password filled with anything, for the sake of producing information, we will move to EffortLogger Console
	public void signinButtonOnAction(ActionEvent e) {
		
		if(usernameTextField.getText().isBlank() == false && passwordPasswordField.getText().isBlank() == false) {
			userName = usernameTextField.getText();
			passWord = passwordPasswordField.getText();
			try {
				FileReader fr = new FileReader("login.txt");
				BufferedReader br = new BufferedReader(fr);
				String line;
				while((line=br.readLine())!=null) {
					if(line.equals(userName + ";" + passWord)){
						setCheck(true);
						break;
					}
				}
				fr.close();
			}catch(Exception a){}
			if (check) {
				loginMessageLabel.setText("login success!");
			}
			else {
				loginMessageLabel.setText("Please enter a valid user");
			}
		} 
		else {
			loginMessageLabel.setText("Please enter login information");
		}
	}
	
	

	public void signupButtonOnAction(ActionEvent e) {
		loginMessageLabel.setText("Please enter new user information");
		if(usernameTextField.getText().isBlank() == false && passwordPasswordField.getText().isBlank() == false) {
			try {
				FileWriter fw = new FileWriter("login.txt",true);
				fw.write(usernameTextField.getText() + ";" + passwordPasswordField.getText() + "\n\n");
				fw.close();
			}catch(Exception a){}
			loginMessageLabel.setText("New user entered!");
		}
		else {
			loginMessageLabel.setText("Please enter login information");
		}
	}
}