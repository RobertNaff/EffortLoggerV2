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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;


public class LogEditor extends Main {
	
	ObservableList<String> projectList = FXCollections.observableArrayList("Business Project", "Development Project");
	ObservableList<String> lcsList = FXCollections.observableArrayList("Planning", "Information Gathering", "Information Understanding", "Verifying", "Outlining", "Drafting", "Finalizing", "Team Meeting", "Coach Meeting", "Stakeholder Meeting");
	ObservableList<String> ecList = FXCollections.observableArrayList("Plans", "Deliverables", "Interruptions", "Defects", "Others");
	ObservableList<String> logList;
	ObservableList<String> ecAspectList;
	
	@FXML
	public Label aspectLabel;
	@FXML
	public Label numberOfLogLabel;
	@FXML
	public Label updatedEntryLabel;
	
	@FXML
	public ChoiceBox<String> projectBox;
	@FXML
	public ChoiceBox<String> selectLogBox;
	@FXML
	public ChoiceBox<String> lifeCycleBox;
	@FXML
	public ChoiceBox<String> effortCategoryBox;
	@FXML
	public ChoiceBox<String> effortCategoryAspectBox;
	@FXML
	public DatePicker dateBox;
	@FXML
	public TextField startTimeTextBox;
	@FXML
	public TextField stopTimeTextBox;
	@FXML
	public Button clearLogButton;
	@FXML
	public Button updateButton;
	@FXML
	public Button deleteButton;
	@FXML
	public Button splitButton;
	@FXML
	public Button consoleButton;
	
	private String username;
	private String currentLog; //log that is being edited
	private String tempLog;
	
	@FXML
	public void initialize() {
		projectBox.setItems(projectList);
		lifeCycleBox.setItems(lcsList);
		effortCategoryBox.setItems(ecList);
		logList = FXCollections.observableArrayList();
	}
	
	//activates on selecting an option in the effort category. determines the text in 
	public void effortCategoryBoxOnAction(ActionEvent e) {
		switch (effortCategoryBox.getSelectionModel().getSelectedItem()) {
		case "Plans":
			aspectLabel.setText("Plan");
			ecAspectList=FXCollections.observableArrayList("Project Plan", "Risk Management Plan", "Conceptual Design Plan", "Detailed Design Plan", "Implementation Plan");
			effortCategoryAspectBox.setItems(ecAspectList);
			break;
		case "Deliverables":
			aspectLabel.setText("Deliverable");
			ecAspectList=FXCollections.observableArrayList("Conceptual Design", "Detailed Design", "Test Cases", "Solution", "Reflection", "Outline", "Draft", "Report", "User Defined", "Other");
			effortCategoryAspectBox.setItems(ecAspectList);
			break;
		case "Interruptions":
			aspectLabel.setText("Interruption");
			ecAspectList=FXCollections.observableArrayList("Break", "Phone", "Teammate", "Visitor", "Other");
			effortCategoryAspectBox.setItems(ecAspectList);
			break;
		case "Defects":
			aspectLabel.setText("Defect");
			ecAspectList=FXCollections.observableArrayList("-Defect Information-");
			effortCategoryAspectBox.setItems(ecAspectList);
			break;
		case "Others":
			aspectLabel.setText("");
			ecAspectList=FXCollections.observableArrayList("");
			effortCategoryAspectBox.setItems(ecAspectList);
			break;
		}
	}
	
	//this method triggers upon selecting an item in the Project selection choice box
	//reads the lines in the LogFile, selects the ones in the respective project, and adds them to the list
	//this list is then put in the Log choice box
	public void projectBoxOnAction() {
		logList.clear(); //clears it upon every activation
		
		
		//if the box is empty, no logs are given
		if(projectBox.getSelectionModel().getSelectedItem().equals("")) {
			return;
		}else {
			try {
				FileReader fr = new FileReader("LogFile.txt"); //file stream
				BufferedReader br = new BufferedReader(fr); //buffered reader
				String line; //string to read the line
				
				//loops throught the file until it hits the end
				while((line = br.readLine()) != null) {
					//if the log that the reader is on contains the correct project, it adds it to the Log list choice box
					if(line.contains(projectBox.getSelectionModel().getSelectedItem())) {
						logList.add(line);
					}
				}
				fr.close(); //closes file stream
			}catch(Exception r){
				
			}
			
			//if the list is null, it displays nothing, program will crash if this if statement is not here
			if(logList != null)
				selectLogBox.setItems(logList);
		}
	}
	
	public void selectLogBoxOnAction() {
		if(selectLogBox.getSelectionModel().getSelectedItem() == null) {
			return;
		}
		
		if(selectLogBox.getSelectionModel().getSelectedItem().equals(currentLog)) {
			return;
		}
		
		if(!selectLogBox.getSelectionModel().getSelectedItem().contains("Accessible:Yes")) {
			selectLogBox.setValue(currentLog);
			Alert editAlert = new Alert(AlertType.ERROR);
			editAlert.setTitle("Edit Error");
			editAlert.setHeaderText("Cannot edit log at this time");
            editAlert.setContentText("Another user is currently editing this log");
            editAlert.showAndWait();
            
		}else {
			try {
				this.tempLog = currentLog;
				
				//Reads the log file
				FileReader fr = new FileReader("LogFile.txt");
				BufferedReader br = new BufferedReader(fr);
				
				//string that stores the new file
				StringBuffer newFileString = new StringBuffer();
				
				String line = br.readLine(); //current line in the file
				//loops till the end of the file
				while (line!=null) {
					//checks to see if the current line is the same as the current/previously edited log
					//if it is not the same, then the file string buffer will skip
					//if it is, then the line is replaced by it and its accessibility is changed, and continued
					
					if(this.tempLog!=null) {
						if(line.equals(tempLog)) {
							tempLog = tempLog.replace("Accessible:No", "Accessible:Yes");
							newFileString.append(tempLog + "\n");
							line = br.readLine();
							continue;
						}
					}
					
					//checks to see if the current line is the same as the one selected in the box
					//if it is not the same, then the file string buffer storing the new file appends the next line in the file
					//if it is, then the line is replaced by the same line but with changed accessibility
					if(line.equals(selectLogBox.getSelectionModel().getSelectedItem())){
						line = line.replace("Accessible:Yes", "Accessible:No");
						currentLog = line;
						newFileString.append(line + "\n");
					}else {
						newFileString.append(line + "\n");
					}
					
					line = br.readLine(); //next line
				}
				
				//closes file stream
				br.close();
				fr.close();
				
				//write file stream
				FileWriter fw = new FileWriter("LogFile.txt",false);
				fw.write(newFileString.toString()); //replaces the current file with the new one
				
				fw.close(); //closes file stream
			}catch(Exception r) {
				
			}
			
			
			System.out.println(currentLog);
			projectBoxOnAction();
			selectLogBox.setValue(currentLog);
		}
	}
	
	//updates the log based on the text fields in 3.a
	public void updateButtonOnAction(ActionEvent e) {
		//if the log choice box is not null then the method executes
		if(selectLogBox.getSelectionModel().getSelectedItem() != null) {
			try {
				//Reads the log file
				FileReader fr = new FileReader("LogFile.txt");
				BufferedReader br = new BufferedReader(fr);
				
				//string that stores the new file
				StringBuffer newFileString = new StringBuffer();
				
				String line = br.readLine(); //current line in the file
				
				//loops till the end of the file
				while (line!=null) {
					//checks to see if the current line is the same as the one selected in the box
					//if it is not the same, then the file string buffer storing the new file appends the next line in the file
					//if it is, then the line is replaced by recording all the fields that will be changed
					if(line.equals(selectLogBox.getSelectionModel().getSelectedItem())){
						String updatedLog = this.username + "'s Log: " 
								+ startTimeTextBox.getText() + "->"
								+ stopTimeTextBox.getText() + ";"
								+ projectBox.getSelectionModel().getSelectedItem() + ";"
								+ lifeCycleBox.getSelectionModel().getSelectedItem() + ";"
								+ effortCategoryBox.getSelectionModel().getSelectedItem() + ";"
								+ effortCategoryAspectBox.getSelectionModel().getSelectedItem() + ";"
								+ "Accessible:Yes";
						newFileString.append(updatedLog + "\n");
						currentLog = updatedLog;
							
					}else {
						newFileString.append(line + "\n");
					}
					
					line = br.readLine(); //next line
				}
				
				//closes file stream
				br.close();
				fr.close();
				
				//write file stream
				FileWriter fw = new FileWriter("LogFile.txt",false);
				fw.write(newFileString.toString()); //replaces the current file with the new one
				
				fw.close(); //closes file stream
			}catch(Exception r) {
				
			}
			projectBoxOnAction(); //resets the log selection box
		}
	}
	
	//works the same as the update function but instead of replacing a line, it skips over it during copying
	public void deleteButtonOnAction(ActionEvent e) {
		//if the log choice box is not null then the method executes
				if(selectLogBox.getSelectionModel().getSelectedItem() != null) {
					try {
						this.currentLog = null;
						//Reads the log file
						FileReader fr = new FileReader("LogFile.txt");
						BufferedReader br = new BufferedReader(fr);
						
						//string that stores the new file
						StringBuffer newFileString = new StringBuffer();
						
						String line = br.readLine(); //current line in the file
						
						//loops till the end of the file
						while (line!=null) {
							//checks to see if the current line is the same as the one selected in the box
							//if it is not the same, then the file string buffer storing the new file appends the next line in the file
							//if it is, then nothing is appended
							if(line.equals(selectLogBox.getSelectionModel().getSelectedItem())){
	
							}else {
								newFileString.append(line + "\n");
							}
							
							line = br.readLine(); //next line
						}
						
						//closes file stream
						br.close();
						fr.close();
						
						//write file stream
						FileWriter fw = new FileWriter("LogFile.txt",false);
						fw.write(newFileString.toString()); //replaces the current file with the new one
						
						fw.close(); //closes file stream
					}catch(Exception r) {
						
					}
					
					projectBoxOnAction(); //resets the log selection box
				}
	}
	
	//exits the editor
	//issues warning if there are boxes that are not empty
	public void consoleButtonOnAction(ActionEvent e) {
		if(!((startTimeTextBox.getText().equals(""))
				&&(stopTimeTextBox.getText().equals(""))
				&&(dateBox.getPromptText() == null)
				&&(lifeCycleBox.getSelectionModel().getSelectedItem() == null)
				&&(effortCategoryBox.getSelectionModel().getSelectedItem() == null)
				&&(effortCategoryAspectBox.getSelectionModel().getSelectedItem() == null))) {
			
			//issues alert to user
			Alert saveAlert = new Alert(AlertType.CONFIRMATION);
			saveAlert.setTitle("Unsaved Effort Log Edits");
			saveAlert.setHeaderText("There are unsaved edits in the EffortLogger editor");
            saveAlert.setContentText("Are you sure you want to leave?");
            
            if(saveAlert.showAndWait().get() == ButtonType.OK){
            	consoleButton.getScene().getWindow().hide();
            }
		}
		
		try {			
			tempLog = currentLog;
			//Reads the log file
			FileReader fr = new FileReader("LogFile.txt");
			BufferedReader br = new BufferedReader(fr);
			
			//string that stores the new file
			StringBuffer newFileString = new StringBuffer();
			
			String line = br.readLine(); //current line in the file
			//loops till the end of the file
			while (line!=null) {

				if(tempLog == null) {
					break;
				}
				
				if(line.equals(tempLog)){
					tempLog = tempLog.replace("Accessible:No", "Accessible:Yes");
					newFileString.append(tempLog + "\n");
				}else {
					newFileString.append(line + "\n");
				}
				
				line = br.readLine(); //next line
			}
			
			//closes file stream
			br.close();
			fr.close();
			
			if(tempLog!= null) {
				FileWriter fw = new FileWriter("LogFile.txt",false);
				fw.write(newFileString.toString()); //replaces the current file with the new one
				fw.close(); //closes file stream
			}
			
	
		}catch(Exception r) {
			
		}
		
    	consoleButton.getScene().getWindow().hide();

	}
	
	public void clearLogButtonOnAction(ActionEvent e) {
		
	}
	
	public void setUserName(String newUserName) {
		this.username = newUserName;
	}
	
}