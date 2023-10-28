package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class Main extends Application {
	@FXML
	private Button signinButton;
	@Override
	public void start(Stage primaryStage) {
		try {
			UserLoginConfig userLogin = new UserLoginConfig();
			//Load in user login/signup page to verify valid user
			Parent root = FXMLLoader.load(getClass().getResource("/UserLogin.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void newInterface() {
		try {
			Stage primaryStage=new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/Logger.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void handle(String[] args) {
		launch(args);
	}
}
