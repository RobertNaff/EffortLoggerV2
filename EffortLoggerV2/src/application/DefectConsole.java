package application;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DefectConsole extends Main {
	ObservableList<String> projectList = FXCollections.observableArrayList("Business Project", "Development Project");
	ObservableList<String> stepList = FXCollections.observableArrayList("Planning", "Information Gathering", "Information Understanding", "Verifying", "Outlining", "Drafting", "Finalizing", "Team Meeting", "Coach Meeting", "Stakeholder Meeting");
	ObservableList<String> defectCategoryList = FXCollections.observableArrayList("Not Specified", "Documentation", "Syntax", "Build, Package", "Assignment");
	ObservableList<String> defectList;
	
	@FXML
	public ChoiceBox<String> projectBox;
	@FXML
	public ChoiceBox<String> defectBox;
	@FXML
	public ChoiceBox<String> stepInjectBox;
	@FXML
	public ChoiceBox<String> stepRemoveBox;
	@FXML
	public ChoiceBox<String> defectCategoryBox;
	@FXML
	public ChoiceBox<String> fixBox;
	@FXML
	public TextField defectNameField;
	@FXML
	public TextArea defectDescriptionArea;
	@FXML
	public Button clearButton;
	@FXML
	public Button updateButton;
	@FXML
	public Button deleteButton;
	@FXML
	public Button closeDefectButton;
	@FXML
	public Button reopenButton;
	@FXML
	public Button createButton;
	@FXML
	public Button consoleButton;
	
	private String username;
	
	//initializes some choice boxes
	@FXML
	public void initialize() {
		projectBox.setItems(projectList);
		stepInjectBox.setItems(stepList);
		stepRemoveBox.setItems(stepList);
		defectCategoryBox.setItems(defectCategoryList);
		defectList = FXCollections.observableArrayList();
	}
	
	//called upon choosing an option in the projects choice box
	//loads the list of defects the user can chooose from
	public void projectBoxOnAction() {
		defectList.clear(); //clears it upon every activation
		
		
		//if the box is empty, no defects are given
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
			
			//if the list is null, it displays nothing, program will crash if this if statement is not here
			if(defectList != null)
				defectBox.setItems(defectList);
		}
	}
	
	//called upon clicking the clear button
	//clears all the menus
	public void clearButtonOnAction() {
		stepInjectBox.setValue(null);
		stepRemoveBox.setValue(null);
		defectCategoryBox.setValue(null);
		defectNameField.setText("");
		defectDescriptionArea.setText("");
	}
	
	//called upon hitting the update button
	//updates defects
	public void updateButtonOnAction() {
		boolean updated = false;
		
		//updated defect line, takes data from all the fields
		String newDefect = defectNameField.getText() 
				+ ";" + projectBox.getSelectionModel().getSelectedItem()
				+ ";" + defectDescriptionArea.getText()
				+ ";" + stepInjectBox.getSelectionModel().getSelectedItem()
				+ ";" + stepRemoveBox.getSelectionModel().getSelectedItem()
				+ ";" + defectCategoryBox.getSelectionModel().getSelectedItem()
				+ ";" + fixBox.getSelectionModel().getSelectedItem() + "\n";
		try {
			//Reads the defect file
			FileReader fr = new FileReader("DefectFile.txt");
			BufferedReader br = new BufferedReader(fr);
			
			//string that stores the new file
			StringBuffer newFileString = new StringBuffer();
			
			String line = br.readLine(); //current line in the file
			
			//loops till the end of the file
			while (line!=null) {
				//replaces the old content with the new content
				if(line.equals(defectBox.getSelectionModel().getSelectedItem()) && !updated){
					updated = true;
					newFileString.append(newDefect);
						
				}else {
					newFileString.append(line + "\n");
				}
				
				line = br.readLine(); //next line
			}
			
			//closes file stream
			br.close();
			fr.close();
			
			//write file stream
			FileWriter fw = new FileWriter("DefectFile.txt",false);
			fw.write(newFileString.toString()); //replaces the current file with the new one
			
			fw.close(); //closes file stream
		}catch(Exception r) {
			
		}
		projectBoxOnAction();
			
	}
	
	//called upon hitting the delete button
	//deletes a defect from the file
	public void deleteButtonOnAction() {
		boolean deleted = false; //whether or not this loop has deleted or not
		
		try {
			//Reads the defect file
			FileReader fr = new FileReader("DefectFile.txt");
			BufferedReader br = new BufferedReader(fr);
			
			//string that stores the new file
			StringBuffer newFileString = new StringBuffer();
			
			String line = br.readLine(); //current line in the file
			
			//loops till the end of the file
			while (line!=null) {
				//when it finds the defect, it skips over the line in writing the new string, basically deleting it
				if(line.equals(defectBox.getSelectionModel().getSelectedItem()) && !deleted){
						deleted = true;
				}else {
					newFileString.append(line + "\n");
				}
				
				line = br.readLine(); //next line
			}
			
			//closes file stream
			br.close();
			fr.close();
			
			//write file stream
			FileWriter fw = new FileWriter("DefectFile.txt",false);
			fw.write(newFileString.toString()); //replaces the current file with the new one
			
			fw.close(); //closes file stream
		}catch(Exception r) {
			
		}
		
		projectBoxOnAction();
		clearButtonOnAction();
	}
	
	//changes the status of a defect from open to closed
	public void closeDefectButtonOnAction() {
		
	}
	
	public void reopenButtonOnAction() {
		
	}
	
	//called upon clicking the create new defect buton
	//creates a new empty defect that needs to be edited
	public void createButtonOnAction() {
		try {
			FileWriter fw = new FileWriter("DefectFile.txt",true);//writes to the Defect File, the 'true' means the new lines simply add on and do not replace
			fw.write("- new defect -;" + projectBox.getSelectionModel().getSelectedItem() + ";;;;;\n"); //new defect format is written to the end of the file
			fw.close(); //file stream is closed
		}catch(Exception a){
			
		}
		
		projectBoxOnAction();
	}
	
	//called upon clikcing the proceed to console button
	//closes the window and returns the user to the main log console
	public void consoleButtonOnAction() {
		consoleButton.getScene().getWindow().hide();
	}
	
	
}
