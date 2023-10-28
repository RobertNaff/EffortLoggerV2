package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class Main extends Application {
	
	@FXML
    private TextField usernameTextField;
    
    private Parent root;

	@Override
	public void start(Stage primaryStage) {
		try {
            initializeMainScene(primaryStage);
        } catch(Exception e) {
            e.printStackTrace();
        }
	}
	
	 public void initializeMainScene(Stage primaryStage) throws IOException { //changed this so that I could call the main screen faster and easier
	        //Load in user login/signup page to verify valid user
	        Parent root = FXMLLoader.load(getClass().getResource("/UserLogin.fxml"));
	        Scene scene = new Scene(root);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        primaryStage.setTitle("EffortLogger Application");
	        primaryStage.setScene(scene);
	        primaryStage.show();

	        primaryStage.setOnCloseRequest(event -> {
	            event.consume();
	            logout(primaryStage);        
	        });
	    }
	 
	 //I changed the initialization of the EL Console to match the MainScene function
	 //so that I could call it after the Privacy Consent screen without an exception
	public void initializeConsole(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Logger.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setTitle("EffortLogger Console");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            logout(primaryStage);        
        });
	}
	
	//EffortLogger Console
	//
	public void newInterface() {
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Logger.fxml"));
            root=loader.load();        
            LoggerConfig loggerConfig = loader.getController();        
            loggerConfig.displayUser(usernameTextField.getText());			
            Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//
	private void logout(Stage primaryStage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are your sure of logging out ?");
        alert.setContentText("Save before exiting?");
        
        if (alert.showAndWait().get() == ButtonType.OK){
                System.out.println("You have logged out");
                primaryStage.close();
        }
        
	}
	
	public void logEditorInterface() {
		try {
			Stage logEditorStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogEditor.fxml"));
			root = loader.load();
			LogEditor logEditor = loader.getController();
			logEditor.setUserName("Example");
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			logEditorStage.setScene(scene);
			logEditorStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}	
	
	public void tutorialInterface() {
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tutorial.fxml"));
			root=loader.load();        		
            Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public void privacyConsent() {
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/PrivacyConsent.fxml"));
			root=loader.load();        		
            Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			//logout if they exit
	        primaryStage.setOnCloseRequest(event -> {
	            event.consume();
	            logout(primaryStage);        
	        });
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	

	
	public static void handle(String[] args) {
		launch(args);
	}
}
