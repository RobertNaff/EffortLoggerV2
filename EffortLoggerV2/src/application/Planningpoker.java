package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Planningpoker extends Main{
	
	
	@FXML
	public Button backtoconsole;
	
	
	public void backtoconsoleaction(ActionEvent e) throws IOException {
		
		newInterface();
		backtoconsole.getScene().getWindow().hide();
	}
	
	

		
}
