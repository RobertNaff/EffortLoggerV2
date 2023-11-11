package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Planningpoker extends Main{
	
	//To return to the effort logger page
	@FXML
	public Button backtoconsole;
	
	
	public void backtoconsoleaction(ActionEvent e) throws IOException {
		
		newInterface();
		backtoconsole.getScene().getWindow().hide();
	}

	@FXML
	public Button importdatabutton;
	
	//when import button trigged, it shows the previous content 
	//then with or without modified,the content will be store into the new file for the new project
	public void importdataAction(ActionEvent e) throws IOException {
		TextArea tarea=new TextArea();
		double height=400;
		double width=1000;
		tarea.setPrefHeight(height);
		tarea.setPrefWidth(width);
		tarea.setWrapText(true);
		tarea.setFont(Font.font(12));
		tarea.setEditable(true);
		
		FlowPane pane=new FlowPane();
		pane.getChildren().add(tarea);
		Stage primaryStage=new Stage();
		primaryStage.setScene(new Scene(pane));
		primaryStage.show();
		
		//Reads the log file		
		FileReader fr = new FileReader("LogFile.txt");
		BufferedReader reader = new BufferedReader(fr);
				
		String line = null;
		while ((line = reader.readLine()) != null) {
			tarea.appendText(line + '\n');
		}
		//closes file stream
		reader.close();
		fr.close();
		
		//get the new content when the areatext modified
		tarea.textProperty().addListener((observable, oldValue, newLog) -> {
            // whenever the log modified in the TextArea, it will print in the console
            System.out.print(newLog);
    
		try {
            BufferedWriter bf = new BufferedWriter(new FileWriter("newLog.txt"));
            bf.write(newLog);
            bf.flush();
            bf.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
		 });
	}
	

		
}
