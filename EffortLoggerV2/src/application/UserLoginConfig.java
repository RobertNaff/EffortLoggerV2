//This file configures UserLogin interface control configurations
package application;

import java.io.*;
import java.security.NoSuchAlgorithmException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserLoginConfig extends Main{
	//import FXML features
	//
	@FXML
	private Label loginMessageLabel;
	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordPasswordField;
	@FXML
	private Button signinButton;
	//
	
	
	//Getter and Setter method for boolean variable that checks if user's log in is valid
	//
	boolean check;
	public boolean getCheck() {
		return check;
	}
	public boolean setCheck(boolean check) {
		this.check = check;
		return check;
	}
	//
	
	//variables to store username and password
	//
	String userName;
	String passWord;
	String hashUsername;
	String hashPassword;
	//
	
	//Create an object from the HashMaker class
	//
	HashMaker hashMaker = new HashMaker();
	//
	
	public String getUsername() {
		return usernameTextField.getText();
	}
	
	public String getPassword() {
		return passwordPasswordField.getText();
	}
	
	public void getHashusername() {
		try {
			hashUsername = hashMaker.createMD5Hash(getUsername());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public void getHashpassword() {
		try {
			hashPassword = hashMaker.createMD5Hash(getPassword());
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	//assign action to when signinButton is pressed
	//if either username or password empty, inform user
	//if both username and password are valid, we will move to EffortLogger Console
	//
	public void signinButtonOnAction(ActionEvent e) {
		
		if(getUsername().isBlank() == false && getPassword().isBlank() == false) {
			userName = getUsername();
			passWord = getUsername();
			try {
				getHashusername();
				getHashpassword();
				FileReader fr = new FileReader("login.txt");
				BufferedReader br = new BufferedReader(fr);
				String line;
				while((line=br.readLine())!=null) {
					if(line.equals(hashUsername + " ; " + hashPassword)){
						setCheck(true);
						
						break;
					}
				}
				fr.close();
			}catch(Exception a){}
			if (check) {
				loginMessageLabel.setText("login success!");
				usernameTextField.getScene().getWindow().hide();
				newInterface();
				setCheck(false);
				
			}
			else {
				loginMessageLabel.setText("Please enter a valid user");
			}
		} 
		else {
			loginMessageLabel.setText("Please enter login information");
		}
	}
	//
	
	//assign action to when signupButton is pressed
	//write new user information to text file
	public void signupButtonOnAction(ActionEvent e) {
		loginMessageLabel.setText("Please enter new user information");
		if(getUsername().isBlank() == false && getPassword().isBlank() == false) {
			try {
				getHashusername();
				getHashpassword();
				FileWriter fw = new FileWriter("login.txt",true);
				fw.write("\n" + hashUsername + " ; " + hashPassword + "\n");
				fw.close();
			}catch(Exception a){}
			loginMessageLabel.setText("New user entered!");
		}
		else {
			loginMessageLabel.setText("Please enter login information");
		}
	}
	//
}
