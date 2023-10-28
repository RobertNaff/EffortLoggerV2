package application;

import java.io.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class EffortLoggerTutorial extends Main{
	@FXML
	private Label tutorialInfo;	
	@FXML
	private Button nextPage;
	@FXML
	private Button previousPage;
	
	//when user clicks on nextpage button, tutorialinfo must change
	//when user clicks on previouspage button, tutorial info must go back
	private int pageIndex;
	
	
	
	public void nextPageButtonOnAction(ActionEvent e) {
		pageIndex++;
		switch(pageIndex) {
		case 1:
			tutorialInfo.setText("First, to get started, you must sign up, then sign in");
		    break;
		case 2:
			tutorialInfo.setText("Next, choose the Project, and the Life Cycle Step related to it");
		    break;
		case 3:
			tutorialInfo.setText("You can also add labels and select from predefined effort categories");
		    break;	    
		case 4:
			tutorialInfo.setText("Once done, click on the 'Start Activity' to begin logging your effort");
			nextPage.setText("Finish");
		    break;	 
		default:
			closeScreen();
			break;
		}
		
		
	}
	
	public void closeScreen() { //closes tutorial screen, and opens up the main screen
			nextPage.getScene().getWindow().hide();
			Stage mainStage = new Stage();
            try {
                initializeMainScene(mainStage);
            } catch(IOException e) {
                e.printStackTrace();
            }
		
			
	}
	

	
	public void previousPageButtonOnAction(ActionEvent e) {
		pageIndex--;
		
		switch(pageIndex) {
			case 0:
				tutorialInfo.setText("Welcome to the Effort Logger Tutorial, lets get started with selecting your project\"");
	            previousPage.setText("Back");
	            break;
	        case 1:
	            tutorialInfo.setText("First, to get started, you must sign up, then sign in");
	            previousPage.setText("Previous");
	            nextPage.setText("Next");
	            break;
	        case 2:
	            tutorialInfo.setText("Next, choose the Project, and the Life Cycle Step related to it");
	            previousPage.setText("Previous");
	            nextPage.setText("Next");
	            break;
	        case 3:
	            tutorialInfo.setText("You can also add labels and select from predefined effort categories");
	            previousPage.setText("Previous");
	            nextPage.setText("Next");
	            break;
	        default:
	        	closeScreen();
	        	break;
		}
		
		
		
	}
	
}
