package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Planningpoker extends Main{
	
	//PlanningPoker
	@FXML
	public Button backtoconsole;
	@FXML
	private Button requestButton;
	@FXML
	private TextField storyPointsTextField;
	@FXML
	private TextField projectname;
	@FXML 
	private TextField want;
	@FXML
	private TextField so;
	@FXML
	private TextField as;
	@FXML
	public TextField keyword;
	@FXML
	private TextField note;
	@FXML
	private TextField attributeSaveTextField;
	@FXML
	private Label estimateCheckLabel;
	@FXML
	private TextField adjustTextField;
	@FXML
	private Label estimateLabel;
	@FXML
	public Button importdatabutton;
		
	int[] storyPoint = null;	
	
	public  String getKeyword() {
		return keyword.getText();
	}
	
	public String getAttributeSaveTextField() {
		return attributeSaveTextField.getText();
	}
	
	public void backtoconsoleaction(ActionEvent e) throws IOException {
		
		newInterface();
		backtoconsole.getScene().getWindow().hide();
	}
	
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
	
	public void requestButtonOnAction(ActionEvent e) {
		
		String keyWords = getKeyword();
		List<String> keywordList = Arrays.asList(keyWords.split(", "));
		int sum = 0;
		int count = 0;
		try {
			FileReader fr = new FileReader("logFile.txt");
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line=br.readLine())!=null) {
				for(int i=0; i < keywordList.size(); i++ ) {
					if(line.contains(keywordList.get(i))){
						List<String> logList = Arrays.asList(line.split(";"));
						System.out.print(logList + "\n");
						sum += Integer.parseInt(logList.get(5));
						count++;
					}
				}
			}
			estimateLabel.setTextFill(Color.rgb(34, 224, 28, 1));
			estimateLabel.setText("Estimate: " + sum/count);
			keywordList = null;
			fr.close();
		}catch(Exception a){}
	}	
}
