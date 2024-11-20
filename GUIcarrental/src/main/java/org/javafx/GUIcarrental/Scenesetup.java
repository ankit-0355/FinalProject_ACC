package org.javafx.GUIcarrental;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Scenesetup extends App{
	
	String cityName;
	
	public HashMap<String, Integer> temp;
	public List<String[]> CarDetails;
	
//	UserInput for city name
	public void Scene0(Stage stage) {
        VBox initialLayout = new VBox(10);
        initialLayout.setPadding(new Insets(20));
        initialLayout.setAlignment(Pos.CENTER);

        // Title label
        Label citynamelable = new Label("Enter the Name of the City:");
        TextField citynamefield = new TextField();
        if(cityName!=null) {
        	citynamefield.setText(cityName);
        }
        Button startButton = new Button("Search");

        // Frequency check elements
        Label frequencyLabel = new Label(); // Label to display frequency count
        Button checkFrequencyButton = new Button("CheckWordFrequency");

        initialLayout.getChildren().addAll(citynamelable, citynamefield, startButton, checkFrequencyButton, frequencyLabel);

        // Scene setup
        Scene layout1 = new Scene(initialLayout, 300, 300);
        stage.setScene(layout1);
        stage.setTitle("Data Crawler");
        stage.show();

        // Event handler for Start button
        startButton.setOnAction(event -> {
            cityName = citynamefield.getText().trim();
            if (!cityName.isEmpty()) {
            	try {
            		String[] sugglst=objsck.vocab(cityName);
            		if(sugglst.length>0 && sugglst[0]!=null) {
            			Scene1(stage,sugglst);
            			return;
            		}
				} catch (Exception e) {}
            	
                // Increment frequency for the entered city name
                objSF.searchWord(cityName.toLowerCase());
                try {
                	Scene2(stage);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                frequencyLabel.setText("Please enter a city name.");
            }
        });

        // Event handler for CheckWordFrequency button
        checkFrequencyButton.setOnAction(event -> {
            String word = citynamefield.getText().trim();
            if (!word.isEmpty()) {
                int frequency = objSF.getWordFrequency(word.toLowerCase());
                frequencyLabel.setText("Frequency of \"" + word + "\": " + frequency);
            } else {
                frequencyLabel.setText("Please enter a word.");
            }
        });
    }
	
//	Invalid Location handling
	public void Scene1(Stage stage, String[] sugwrd) {
		
		VBox messageLayout = new VBox(10);
        messageLayout.setPadding(new Insets(20));
        messageLayout.setAlignment(Pos.CENTER);
        
        Label nextForwardLabel = new Label("Invalid Location! Please try :");
        messageLayout.getChildren().add(nextForwardLabel);

        // Create labels for each word in sugwrd and add to the layout
        for (String word : sugwrd) {
            Label wordLabel = new Label(word);
            messageLayout.getChildren().add(wordLabel);
        }

        Button backButton = new Button("Back");
        messageLayout.getChildren().add(backButton);
        Scene messageScene = new Scene(messageLayout, 300, 200);

        // Set the new scene to the stage
        stage.setScene(messageScene);

        // Add an action for the back button to return to the previous form scene
        backButton.setOnAction(e -> 
        {
        	Scene0(stage);    	
        });	
	}
	
//	Displayng Available locations for the city name S0 ->S2
	public Scene Scene2(Stage stage) throws Exception{
	    VBox initialLayout = new VBox(10);
	    initialLayout.setPadding(new Insets(20));
	    initialLayout.setAlignment(Pos.CENTER);

	    // Title label
	    Label questionLabel = new Label("Available Locations");
	    initialLayout.getChildren().add(questionLabel);
	    String[] loclst = objuty.loclst(cityName);

	    // ComboBox for selecting a location
	    ComboBox<String> locationComboBox = new ComboBox<>();
	    for(String loc:loclst) {
	    	locationComboBox.getItems().add(loc);
	    }
	    locationComboBox.setPromptText("Select a location");

	    // Add ComboBox to the layout
	    initialLayout.getChildren().add(locationComboBox);

	    HBox buttons = new HBox(20);
	    
	    Button startButton = new Button("Submit");
	    Button backButton = new Button("Back");
	    
	    buttons.getChildren().addAll(startButton,backButton);
	    initialLayout.getChildren().add(buttons);

	    // Create the scene
	    Scene layout1 = new Scene(initialLayout, 300, 200);

	    // Set the scene to the stage
	    stage.setScene(layout1);
	    stage.setTitle("Data Crawler");
	    stage.show();

	    // Event handler for Submit button
	    startButton.setOnAction(event -> {
	        String selectedLocation = locationComboBox.getValue();
	        if (selectedLocation != null) {
	            pickuploc=selectedLocation;
	            stage.setScene(CreateFormscene(stage)); // Move to the next scene
	        } else {
	            System.out.println("No location selected.");
	        }
//	        System.out.println("pickloc----"+pickuploc);
	    });
	    backButton.setOnAction(event -> {
	    	Scene0(stage);
	    });

	    return layout1;
	}
	
    public Scene CreateFormscene (Stage stage) {
    	 
    	Scenesetup ss = new Scenesetup();
    	// Form Layout
        GridPane formLayout = new GridPane();
        formLayout.setPadding(new Insets(20));
        formLayout.setVgap(10);
        formLayout.setHgap(10);
        formLayout.setAlignment(Pos.CENTER);

        Label pickupLocationLabel = new Label("Pickup Location:");
        TextField pickupLocationField = new TextField();
        if (pickuploc!=null) 
        	pickupLocationField.setText(pickuploc);
        pickupLocationField.setEditable(false);
        formLayout.add(pickupLocationLabel, 0, 0);
        formLayout.add(pickupLocationField, 1, 0);

        Label pickupDateLabel = new Label("Pickup Date:");
        DatePicker pickupDatePicker = new DatePicker();
        if (pickupdate!=null) {
        	int day = Integer.parseInt(pickupdate[2]);  
        	int month = Integer.parseInt(pickupdate[1]); 
        	int year = Integer.parseInt(pickupdate[0]); 
        	pickupDatePicker.setValue(LocalDate.of(year,month,day));
        }
        formLayout.add(pickupDateLabel, 0, 1);
        formLayout.add(pickupDatePicker, 1, 1);

        Label pickupTimeLabel = new Label("Pickup Time:");
        TextField pickupTimeField = new TextField();
        if (pickuptime!=null) 
        	pickupTimeField.setText(pickuptime);
        formLayout.add(pickupTimeLabel, 0, 2);
        formLayout.add(pickupTimeField, 1, 2);

        Label dropDateLabel = new Label("Drop Date:");
        DatePicker dropDatePicker = new DatePicker();
        if (dropdate!=null) {
        	int day = Integer.parseInt(dropdate[2]);  
        	int month = Integer.parseInt(dropdate[1]); 
        	int year = Integer.parseInt(dropdate[0]); 
        	dropDatePicker.setValue(LocalDate.of(year,month,day));
        }
        formLayout.add(dropDateLabel, 0, 3);
        formLayout.add(dropDatePicker, 1, 3);

        Label dropTimeLabel = new Label("Drop Time:");
        TextField dropTimeField = new TextField();
        if (droptime!=null) 
        	dropTimeField.setText(droptime);
        formLayout.add(dropTimeLabel, 0, 4);
        formLayout.add(dropTimeField, 1, 4);

        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");
        formLayout.add(submitButton, 1, 5);
        formLayout.add(backButton, 0, 5);

        Scene formScene = new Scene(formLayout, 400, 300); 
        //Event handler for Submit button
        submitButton.setOnAction(event -> {
			try {
				pickuploc = pickupLocationField.getText();
				pickupdate = pickupDatePicker.getValue().toString().split("-");
				pickuptime = pickupTimeField.getText();
				dropdate = dropDatePicker.getValue().toString().split("-");
				droptime = dropTimeField.getText();
				
				if(!(objuty.timechck(pickuptime) && objuty.timechck(droptime) && 
						objuty.isDateInFuture(pickupDatePicker.getValue(),dropDatePicker.getValue()))) {
					ss.Scene3(stage,this);
					return;
				}
//				boolean np=false;
				boolean np = objhp.websitepages(pickuploc, pickupdate, pickuptime, dropdate, droptime);
				if(np) {
				objcd.scrapdata(pickuploc, pickupdate, pickuptime, dropdate, droptime);
				}
				objhp.closebrowser();
				ss.Scene4(stage,np,this);
			} catch (Exception e) {
			}
		});
        
        backButton.setOnAction(event -> {
//        	stage.setScene(Scene0(stage));
        	Scene0(stage);
        });
    	return formScene;
    }
	
//    Invalid Time
	public void Scene3(Stage stage, Scenesetup ss) {
		
		VBox messageLayout = new VBox(10);
        messageLayout.setPadding(new Insets(20));
        messageLayout.setAlignment(Pos.CENTER);
        
        Label nextForwardLabel = new Label("Incorrect Time or Date Format! Please use HH:MM format and Date greater than current date ");
        messageLayout.getChildren().add(nextForwardLabel);

        Button backButton = new Button("Back");
        messageLayout.getChildren().add(backButton);
        Scene messageScene = new Scene(messageLayout, 600, 200);

        // Set the new scene to the stage
        stage.setScene(messageScene);

        // Add an action for the back button to return to the previous form scene
        backButton.setOnAction(e -> stage.setScene(ss.CreateFormscene(stage)));
  		
	}
	
	public void Scene4(Stage stage, boolean nextPage, App ap) {

	    // Create the main GridPane for form layout
		
	    GridPane formLayout = new GridPane();
	    formLayout.setPadding(new Insets(20));
	    formLayout.setVgap(15);
	    formLayout.setHgap(10);
	    formLayout.setAlignment(Pos.CENTER);
	    
	    HBox hb = new HBox(10);
		Label Error = new Label("Website Blocked due to Bot detection");
		hb.getChildren().add(Error);
		formLayout.add(hb, 0, 0);

	    // Add Labels and TextFields in a structured layout
	    Label carBudgetLabel = new Label("Enter Your Budget:");
	    TextField carBudgetField = new TextField();
	    formLayout.add(carBudgetLabel, 0, 1);
	    formLayout.add(carBudgetField, 1, 1);

	    Label passengerNumLabel = new Label("Enter Number of Passengers:");
	    TextField passengerNumField = new TextField();
	    formLayout.add(passengerNumLabel, 0, 2);
	    formLayout.add(passengerNumField, 1, 2);

	    // Create an HBox for the buttons to keep them aligned horizontally
	    HBox buttonBox = new HBox(10);
	    buttonBox.setAlignment(Pos.CENTER);
	    Button submitButton = new Button("Submit");
	    Button backButton = new Button("Back");
	    buttonBox.getChildren().addAll(submitButton, backButton);

	    // Add buttons to the GridPane below the TextFields
	    formLayout.add(buttonBox, 1, 3);
	    
	    // Create a new scene and set it to the stage
	    Scene formScene = new Scene(formLayout, 500, 250);
	    stage.setScene(formScene);

	    // Define actions for buttons
	    submitButton.setOnAction(e -> {
//	    	System.out.println(carBudgetField.getText());
	    	CarDetails = objuty.getAllCarDetails(carBudgetField.getText(), csvFilePath,ap.pickuploc);
		    temp = objuty.getSortedCarTypeFrequency(CarDetails,objuty.carTypeIndex);
		    String[] carwithinBudget = temp.keySet().toString().replaceAll("[\\[\\]]", "") .split(",");
	    	Scene5(stage,carwithinBudget,temp);
	        
	    });
	    backButton.setOnAction(e -> {
	    	try {
				Scene0(stage);
			} catch (Exception e1) {}
	    }); 
	}
	
	public void Scene5(Stage stage, String[] cartypelst, HashMap<String,Integer> temp) {

	    // Create main layout
	    VBox mainLayout = new VBox(15);
	    mainLayout.setPadding(new Insets(10));
	    mainLayout.setAlignment(Pos.CENTER);

	    // "Select Car Type" title
	    Label carTypeLabel = new Label("Select Car Type");
	    carTypeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

	    // Dropdown for car types
	    ComboBox<String> carTypeDropdown = new ComboBox<>();
	    carTypeDropdown.getItems().addAll(cartypelst);
	    carTypeDropdown.setPromptText("Choose a Car Type");

	    // Layout for car type selection
	    VBox carTypeSelectionLayout = new VBox(5, carTypeLabel, carTypeDropdown);
	    carTypeSelectionLayout.setAlignment(Pos.CENTER);

	    // "Trending Cars" title
	    Label trendingCarsLabel = new Label("Available cars");
	    trendingCarsLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
	    
	 // List of trending cars with search frequencies (placeholders)
	    VBox trendingCarsList = new VBox(5);
	    trendingCarsList.setAlignment(Pos.CENTER_LEFT);
	    int i=0;
	    for (Map.Entry<String, Integer> entry : temp.entrySet()) {
	        Label carLabel = new Label("CarType: "+entry.getKey()); // Car type
	        Label frequencyLabel = new Label(" ->  Available Cars: " + entry.getValue()); // Frequency count
	        if(i++==5)
	        	break;
	        // HBox to hold the car name and frequency count
	        HBox carFrequencyBox = new HBox(10, carLabel, frequencyLabel);
	        carFrequencyBox.setAlignment(Pos.CENTER_LEFT);

	        // Add the HBox to the trending cars list
	        trendingCarsList.getChildren().add(carFrequencyBox);
	        
	    }

	    // Layout for trending cars section
	    VBox trendingCarsLayout = new VBox(5, trendingCarsLabel, trendingCarsList);
	    trendingCarsLayout.setAlignment(Pos.CENTER);

	    // Add all sections to main layout
	    mainLayout.getChildren().addAll(carTypeSelectionLayout, trendingCarsLayout);
	    
	    // Create the Submit button
	    Button submitButton = new Button("Submit");
	    
	    //Create Back button
	    Button backButton = new Button("Back");

	    // Position it at the end of the main layout
	    mainLayout.getChildren().addAll(submitButton,backButton);
	    submitButton.setAlignment(Pos.CENTER);

	    // Set up scene and stage
	    Scene carTypeScene = new Scene(mainLayout, 400, 300);
	    stage.setScene(carTypeScene);
	   
	    submitButton.setOnAction(e -> {
	        String selectedCarType = carTypeDropdown.getValue();
	        if (selectedCarType != null) {
	            Scene6(stage, selectedCarType); // Pass the selected car type to Scene6
	        } else {
	            System.out.println("Please select a car type.");
	        }
	    });
	    backButton.setOnAction(e -> {
	    Scene4(stage,true,this);	
	    });
	    
	}
	
	public void Scene6(Stage stage, String selectedCarType) {
	    // Create main layout
	    VBox mainLayout = new VBox(20);
	    mainLayout.setPadding(new Insets(20)); // Increase padding for better spacing
	    mainLayout.setAlignment(Pos.TOP_CENTER);

	    // "Search Result" title
	    Label carTypeLabel = new Label("Search Result for: " + selectedCarType);
	    carTypeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

	    // "Available List of Cars" title
	    Label trendingCarsLabel = new Label("Available Cars Matching Selection");
	    trendingCarsLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

	    // Create a structured GridPane for car details
	    GridPane carDetailsGrid = new GridPane();
	    carDetailsGrid.setPadding(new Insets(10));
	    carDetailsGrid.setHgap(15); // Increased gap between columns for readability
	    carDetailsGrid.setVgap(10);
	    carDetailsGrid.setAlignment(Pos.CENTER);

	    // Add column headers with bold style
	    carDetailsGrid.add(new Label("Model"), 0, 0);
	    carDetailsGrid.add(new Label("Seats"), 1, 0);
	    carDetailsGrid.add(new Label("Type"), 2, 0);
	    carDetailsGrid.add(new Label("Transmission"), 3, 0);
	    carDetailsGrid.add(new Label("Price"), 4, 0);
	    carDetailsGrid.add(new Label("Location"), 5, 0);
	    carDetailsGrid.add(new Label("Date"), 6, 0);
	    carDetailsGrid.add(new Label("Lugguage"), 7, 0);
	    carDetailsGrid.add(new Label("Fuel Efficiency"), 8, 0);
	    carDetailsGrid.add(new Label("Website"), 9, 0);

	    // Populate the grid with car details
	    int row = 1;
	    for (String[] carDetail : CarDetails) {
	        if (carDetail[objuty.carTypeIndex].equalsIgnoreCase(selectedCarType.trim())) {
	            carDetailsGrid.add(new Label(carDetail[0]), 0, row); // Model
	            carDetailsGrid.add(new Label(carDetail[1]), 1, row); // Seats
	            carDetailsGrid.add(new Label(carDetail[2]), 2, row); // Type
	            carDetailsGrid.add(new Label(carDetail[3]), 3, row); // Transmission
	            carDetailsGrid.add(new Label(carDetail[4]), 4, row); // Price
	            carDetailsGrid.add(new Label(carDetail[5]), 5, row); // Location
	            carDetailsGrid.add(new Label(carDetail[6]), 6, row); // Date
	            carDetailsGrid.add(new Label(carDetail[7]), 7, row); // Luggage
	            carDetailsGrid.add(new Label(carDetail[8]), 8, row); // Fuel Efficiency
	            carDetailsGrid.add(new Label(carDetail[9]), 9, row); // Website	            
	            row++;
	        }
	    }
	    
	    // Layout for trending cars section
	    VBox trendingCarsLayout = new VBox(10, trendingCarsLabel, carDetailsGrid);
	    trendingCarsLayout.setAlignment(Pos.CENTER);
	    
	    Button homeButtom = new Button("Home");

	    // Add all sections to main layout
	    mainLayout.getChildren().addAll(carTypeLabel, trendingCarsLayout,homeButtom);
	    mainLayout.setAlignment(Pos.TOP_CENTER);

	    // Set up scene and stage
	    Scene carTypeScene = new Scene(mainLayout, 1000, 600);
	    stage.setScene(carTypeScene);
	    
	    homeButtom.setOnAction(e-> Scene0(stage));
	    
	    
	}
}
