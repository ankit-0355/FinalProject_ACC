package com.webscraping.CarRental;


import java.io.File;
import java.io.FileWriter;
import java.util.List;

import com.opencsv.CSVWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class CarData extends App {
	
//	Method to scrap data from the List of Cars Webpage 
	public void scrapdata() throws Exception {
	
//		Handling FIle creation of CSV file
		FileWriter write = null;
		File f = new File("Cardata.csv");
		if(f.exists()) {
			write = new FileWriter("Cardata.csv",true);
		} else {
//			System.out.println(f.exists());
			write = new FileWriter("Cardata.csv");
			String[] header= {"Car Name", "PassengerCapacity",
					"Car Type","Transmission","Price","Location","pickupDate","Luggage","Range or Mileage"};
			CSVWriter writecsv = new CSVWriter(write);
			writecsv.writeNext(header);
		}
		
//		Selecting Vehicle Grid Box available on the Pages
		List<WebElement> vehicles = driver.findElements(By.xpath("//*[@class='gtm-vehicle']"));
		System.out.println("Total Number Of Cars available to rent: "+vehicles.size());
		
//		Looping through Each Vehicle box to Fetch the car details
		for(WebElement vehicle:vehicles) {
			String vetype=vehicle.findElement(By.xpath(".//*[@class='gtm-vehicle-type']")).getText();
			String vepassengers = vehicle.findElement(By.xpath(".//*[@class='gtm-vehFeature-passengers']")).getText();
			String vetitle = vehicle.findElement(By.xpath(".//*[@class='gtm-vehicle-title']")).getText();
			String vetransmission =vehicle.findElement(By.xpath(".//*[@class='gtm-vehFeature-transmission']//*[@class='gtm-vehFeatureDesc']")).getText();
			String price=vehicle.findElement(By.xpath(".//*[@class='gtm-price']")).getText();
			String luggage = vehicle.findElement(By.xpath(".//*[@class='gtm-vehFeature-suitcases']//*[@class='gtm-vehFeatureDesc']")).getText();
			
//			Writing the data fetched into the CSV
			write.append(vetype.split(" ")[0]+" ").append(vetype.split(" ")[1]).append(",");
			write.append(vepassengers).append(",");
			write.append(vetitle).append(",");
			write.append(vetransmission).append(",");
			if(price.isEmpty())
				write.append("Call to Book").append(",");
			else
				write.append(price+" CAD").append(",");
			write.append("\""+pickuploc+"\"").append(",");
			write.append(pickupdate[0]+"-"+pickupdate[1]+"-"+pickupdate[2]).append(",");
			write.append(luggage).append(",");
					try {
				String verange = vehicle.findElement(By.xpath(".//*[@class='gtm-vehFeature-battery']")).getText();
				write.append(verange).append(",");
			} catch (Exception be) {
//					System.out.println("Exception occoured: "+be);
				try {
					String vefuel = vehicle.findElement(By.xpath(".//*[@class='gtm-vehFeature-fuel']//*[@class='gtm-vehFeatureDesc']")).getText();
					write.append(vefuel).append(",");
				} catch (Exception fe) {
					write.append("N/A");
				}
			}
				write.append("\n");
		}
//		Closing CSV file
		write.close();
		System.out.println("CSV writing completed === Webscraping completed");
	}
}