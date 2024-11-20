package org.javafx.GUIcarrental;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Temp {

    public static String[] getCarsWithinBudget(double budget, String csvFilePath) {
        List<String[]> affordableCars = new ArrayList<>();
        String line;
        HashSet<String> cartypelst = new HashSet<String>(); 

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String headerLine = br.readLine(); // Read header line
            String[] headers = headerLine.split(",");
            int priceIndex=-1;
            int cartype = -1;

            // Find index of "Price" column
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equalsIgnoreCase("Price")) {
                    priceIndex = i;
                    break;
                }
                if (headers[i].equalsIgnoreCase("Car Type")) {
                    cartype = i;
                }
            }

            if (priceIndex == -1) {
                System.out.println("Price column not found in the CSV file.");
//                return affordableCars;
            }

            // Read and filter cars by budget
            while ((line = br.readLine()) != null) {
                String[] carData = line.split(",");
                if (carData[priceIndex].equalsIgnoreCase("Call to Book")) {
                    continue;
                }
                // Extract and process the price
                String priceText = carData[priceIndex].replace(" CAD", "").trim(); // Remove " CAD" suffix
                try {
                    // Parse numeric price, skip non-numeric values like "Call to Book"
                    double price = Double.parseDouble(priceText);
                    if (price <= budget) {
                    	cartypelst.add(carData[cartype]);
                        affordableCars.add(carData); // Store the entire row data
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Non-numeric price ignored: " + carData[priceIndex]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(cartypelst.toString());
//        return affordableCars;
        return cartypelst.toArray(new String[cartypelst.size()]);
    }

    // Main method for testing
    public static void main(String[] args) {
    	double budget = 70; // Example budget
    	String csvFilePath = "/Users/ankitprajapati/Desktop/Ecllipse/CarRental/Cardata1.csv";

        String[] carsWithinBudget = getCarsWithinBudget(budget, csvFilePath);
        
        // Print out the results
        for (String car : carsWithinBudget) {
            System.out.println(String.join(", ", car)); // Print each row that meets the budget condition
        }
    }
}
