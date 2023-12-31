//This file configures Logger interface control configurations
package application;

import java.time.LocalTime;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoggerConfig extends Main{
	
	//Creation of lists for EffortLogger ChoiceBox's
	//
	ObservableList<String> projectList = FXCollections.observableArrayList("Business Project", "Development Project");
	ObservableList<String> lcsList = FXCollections.observableArrayList("Planning", "Information Gathering", "Information Understanding", "Verifying", "Outlining", "Drafting", "Finalizing", "Team Meeting", "Coach Meeting", "Stakeholder Meeting");
	ObservableList<String> ecList = FXCollections.observableArrayList("Plans", "Deliverables", "Interruptions", "Defects", "Others");
	ObservableList<String> defectList;
	ObservableList<String> ecAspectList;
	//
	
	//Import FXML features
	//
	@FXML
	private Button startButton;
	@FXML
	private Label informLabel;
	@FXML
	private ChoiceBox<String> projectBox;
	@FXML
	private ChoiceBox<String> lcsBox;
	@FXML
	private ChoiceBox<String> ecBox;
	@FXML
	private ChoiceBox<String> ecAspectBox;
	@FXML
	private Button stopButton;
	@FXML
	private Label aspectLabel;
	@FXML 
	private Label clockLabel;
	@FXML
	private TextField usernameTextField;
	@FXML 
	private TextField passwordTextField;
	@FXML
	public Button logEditorButton;
	@FXML
	public Button planningpokerButton;
	@FXML
	public Button defectConsoleButton;
	@FXML
	private TextField storyPointsTextField;
	
	//Clock variables
	//
	LocalTime startTime;
	LocalTime endTime;
	//
	
	//Initialize function that sets created list into respected Choicebox
	//
	@FXML
	private void initialize(){		
		projectBox.setItems(projectList);
		lcsBox.setItems(lcsList);
		ecBox.setItems(ecList);
		defectList = FXCollections.observableArrayList();
	}
	//
	
	public String getStoryPoints() {
		return storyPointsTextField.getText();
	}
	
	
	@FXML
    Label nameTag;
    public void displayUser(String username) {
            nameTag.setText("Welcome to Logger Home, "+username);
    }
	
	
	//When Effort Category ChoiceBox is selected, display the Effort Category Aspect Choicebox with respective list
	//n
	public void ecBoxOnAction(ActionEvent e) {
		switch (ecBox.getSelectionModel().getSelectedItem()) {
		case "Plans":
			aspectLabel.setText("Plan");
			ecAspectList=FXCollections.observableArrayList("Project Plan", "Risk Management Plan", "Conceptual Design Plan", "Detailed Design Plan", "Implementation Plan");
			ecAspectBox.setItems(ecAspectList);
			break;
		case "Deliverables":
			aspectLabel.setText("Deliverable");
			ecAspectList=FXCollections.observableArrayList("Conceptual Design", "Detailed Design", "Test Cases", "Solution", "Reflection", "Outline", "Draft", "Report", "User Defined", "Other");
			ecAspectBox.setItems(ecAspectList);
			break;
		case "Interruptions":
			aspectLabel.setText("Interruption");
			ecAspectList=FXCollections.observableArrayList("Break", "Phone", "Teammate", "Visitor", "Other");
			ecAspectBox.setItems(ecAspectList);
			break;
		case "Defects":
			aspectLabel.setText("Defect");
			ecAspectList = defectList;
			ecAspectBox.setItems(ecAspectList);
			break;
		case "Others":
			aspectLabel.setText("");
			ecAspectList=FXCollections.observableArrayList("");
			ecAspectBox.setItems(ecAspectList);
			break;
		}
	}
	//
	
	public void projectBoxOnAction() {
		defectList.clear();
		
		if(projectBox.getSelectionModel().getSelectedItem().equals("")) {
			return;
		}else {
			try {
				FileReader fr = new FileReader("DefectFile.txt"); //file stream
				BufferedReader br = new BufferedReader(fr); //buffered reader
				String line; //string to read the line
				
				//loops through the file until it hits the end
				while((line = br.readLine()) != null) {
					//if the defect that the reader is on contains the correct project, it adds it to the defect list choice box
					if(line.contains(projectBox.getSelectionModel().getSelectedItem())) {
						defectList.add(line);
					}
				}
				fr.close(); //closes file stream
			}catch(Exception r){
				
			}
			
		}
	}
	
	//When "Start an Activity" is clicked, record present time
	//
	public void startButtonOnAction() {
		clockLabel.setText("Clock is running");
		clockLabel.setTextFill(Color.rgb(34, 224, 28, 1));
		startTime=java.time.LocalTime.now();
		informLabel.setText("Start time is: " + startTime.toString());
	}
	//
	
	//when the "Effort Log Editor" button is pressed, opens a new tab to the log editor window
	public void logEditorButtonOnAction(ActionEvent e) {
		logEditorInterface();
	}
	
	//when the "defect Console" button is hit, it calls the method from main that opens the defect console
	public void defectConsoleButtonOnAction() {
		defectConsoleInterface();
	}

	//when the "Planning poker" button is pressed, opens planning poker window
	public void planningpokeraction(ActionEvent e) {
		planningpokerButton.getScene().getWindow().hide();
		planningpokerInterface();
	}

	
	//When "Stop this Activity" is clicked, record present time
	//Write the log's details towards the respective user
	//
	public void stopButtonOnAction() {
		clockLabel.setText("Clock is stopped");
		clockLabel.setTextFill(Color.rgb(233, 11, 11, 1));
		endTime=java.time.LocalTime.now();
		informLabel.setText("End time is: " + endTime.toString());
		try {
			FileWriter fw = new FileWriter("LogFile.txt",true);
			fw.write(nameTag.getText() + "'s Log: " + startTime.toString() + "->" + 
					endTime.toString()+";"+projectBox.getSelectionModel().getSelectedItem()+
					";"+lcsBox.getSelectionModel().getSelectedItem()+";"+
					ecBox.getSelectionModel().getSelectedItem()+";"+
					ecAspectBox.getSelectionModel().getSelectedItem()+ ";" + getStoryPoints() + ";" + "Accessible:Yes\n");
			fw.close();
		}catch(Exception a){}
	}
	//
	
	 @FXML
     private Button logoutbutton;
     @FXML
     private AnchorPane scene2;
     
     Stage primaryStage;
     
     public void logout(ActionEvent event) {
             
             Alert alert = new Alert(AlertType.CONFIRMATION);
             alert.setTitle("Logout");
             alert.setHeaderText("Are you sure of logging out?");
             alert.setContentText("Save before exiting?: ");
             
             if(alert.showAndWait().get() == ButtonType.OK){
                     primaryStage = (Stage) scene2.getScene().getWindow();
                     System.out.println("You have logged out!");
                     primaryStage.close();
             }
             
     }
}
