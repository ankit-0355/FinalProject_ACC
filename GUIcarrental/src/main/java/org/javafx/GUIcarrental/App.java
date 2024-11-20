package org.javafx.GUIcarrental;

import Cardata.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
	
	Homepage objhp = new Homepage();
	CarData objcd = new CarData();
	Spellchk objsck = new Spellchk();
	utility objuty = new utility();
	searchFrequency objSF = new searchFrequency();
	
//	Declare Variables for user input
	public String[] pickupdate,dropdate;
	public String pickuploc,pickuptime,droptime;
	public String csvFilePath="/Users/ankitprajapati/Desktop/Ecllipse/GUIcarrental/Cardatanew.csv";
	
    @Override
    public void start(Stage stage) {
        // Display Initial Scene
        stage.setScene(CreateInitialscene(stage));
        stage.setTitle("Data Crawler");
        stage.show();
    }
    
    public Scene CreateInitialscene(Stage stage) {
    	Scenesetup ss = new Scenesetup();
    	VBox initialLayout = new VBox(10);
        initialLayout.setPadding(new Insets(20));
        initialLayout.setAlignment(Pos.CENTER);

        Label questionLabel = new Label("Welcome to the Car Rental Recommendation System!");
        Label questionLabel1 = new Label("🌟 Let’s find the perfect car for your next trip! 🌟");
        Button startButton = new Button("Start");
        initialLayout.getChildren().addAll(questionLabel,questionLabel1, startButton);

        Scene initialScene = new Scene(initialLayout, 400, 200);
        
     // Event Handler for Start Button
        startButton.setOnAction(event -> 
      {  
//        stage.setScene(ss.Scene0(stage))
    	  ss.Scene0(stage);
    }
        );
        return initialScene;
    }
    
    public static void main(String[] args) {
        launch();
    }

}