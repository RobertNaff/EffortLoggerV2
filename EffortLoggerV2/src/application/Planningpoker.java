package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
	@FXML
	public Button clearButton;
	@FXML
	public Button displayButton;
	@FXML
	public Button deleteButton;
	@FXML
	public Button updateButton;
	
		
	int[] storyPoint = null;
	//High or low estimate
	String estimate = "LOW"; 
	
	
	
	
	public  String getKeyword() {
		return keyword.getText();
	}
	
	public String getAttributeSaveTextField() {
		return attributeSaveTextField.getText();
	}
	public void resetAttributeTextField() //reset "attributes have been saved" 
	{
		attributeSaveTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;"); 
	}
	
	public void backtoconsoleaction(ActionEvent e) throws IOException {
		
		newInterface();
		backtoconsole.getScene().getWindow().hide();
	}
	//returns the number of user story logs currently recorded
	public int logCount()
	{
		int count = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader("PlanningPokerLog.txt"));
			String ln = br.readLine();
			while(ln != null)
			{
				count ++;
				ln = br.readLine();
			}
            br.close();
		}
		catch (IOException e2)
		{
			e2.printStackTrace();
		}
		return count;
	}
	
	//when import button trigged, it shows the previous content 
	//then with or without modified,the content will be store into the new file for the new project
	public void importdataAction(ActionEvent e) throws IOException {
		resetAttributeTextField();
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

		primaryStage.setOnCloseRequest(event -> {
            event.consume();
          //alert to user 
    		Alert saveAlert = new Alert(AlertType.CONFIRMATION);
    		saveAlert.setTitle("Leaving the page?");
    		saveAlert.setHeaderText("Finish the modification?");
            saveAlert.setContentText("Save before exiting");
            
            if(saveAlert.showAndWait().get() == ButtonType.OK){
            	System.out.println("You modified and data already have been saved");
            	tarea.getScene().getWindow().hide();
            }
        });
		
	}
	
	public void requestButtonOnAction(ActionEvent e) {
		resetAttributeTextField();
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
	
	//clears data entry during planning poker session
	//clear all text fields in scene
	public void clearButtonOnAction(ActionEvent e)
	{
		resetAttributeTextField();
		projectname.clear();
		as.clear();
		want.clear();
		so.clear();
		keyword.clear();
		note.clear();
		adjustTextField.clear();
	}
	//display data
	//display imported data with estimates attached
	public void displayButtonOnAction(ActionEvent e) throws IOException
	{
		resetAttributeTextField();
		TextArea display = new TextArea();
		display.setEditable(false);
		display.setPrefHeight(400);
		display.setPrefWidth(1000);
		display.setWrapText(true);
		display.setFont(Font.font(12));
		
		FlowPane pane = new FlowPane();
		pane.getChildren().add(display);
		Stage primaryStage = new Stage();
		primaryStage.setScene(new Scene(pane));
		primaryStage.show();
		
		//Reads the log file
		BufferedReader br = new BufferedReader(new FileReader("LogFile.txt"));
				
		String line = br.readLine();
		//add in estimation values
		while (line != null) {
			display.appendText(line + '\n');
			//append historical estimations
			display.appendText("Projected Estimate: " + estimate + ", ");
			line = br.readLine();
		}
		//closes file stream
		br.close();
		
		display.textProperty().addListener((observable, oldValue, newLog) -> {
		try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("newLog.txt"));
            bw.write(newLog);
            bw.flush();
            bw.close();
        } catch (IOException e4) {
            e4.printStackTrace();
        }
		 });
	}

	//delete data
	public void deleteButtonOnAction(ActionEvent e)
	{
		resetAttributeTextField();
		try {
			BufferedReader br = new BufferedReader(new FileReader("PlanningPokerLog.txt"));
			String ln = br.readLine();
			StringBuffer updateFile = new StringBuffer();
			//reads from all the user stories, except the most recent
			for(int i = 0; i < logCount() - 1; i++)
			{
				updateFile.append(ln + "\n");
				ln = br.readLine();
			}
            br.close();
			//rewriting, not appending
            //copy all user stories except the last one, back into the log
			FileWriter fw = new FileWriter("PlanningPokerLog.txt", false); 
			fw.write(updateFile.toString());
			
			//close stream 
			fw.close();
		}
		catch (IOException e2)
		{
			e2.printStackTrace();
		}
		
	}
	//update data
	public void updateLogButtonOnAction(ActionEvent e) {
		
		try {
			BufferedWriter lg = new BufferedWriter(new FileWriter("PlanningPokerLog.txt", true));
			lg.write(projectname.getText() + ";" + " As a " + as.getText() 
			+ ", I want " + want.getText() 
			+ ", so that " + so.getText() + "; " + getKeyword() + "; " + adjustTextField.getText() + "; " + estimateLabel.getText() +  "\n");
			
			lg.close();
			attributeSaveTextField.setStyle("-fx-text-fill: green; -fx-font-size: 12px;");
			
		} catch (IOException e1) {
            e1.printStackTrace();
        }
		
		
	}
	
}