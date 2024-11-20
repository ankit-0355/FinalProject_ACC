package Cardata;

import java.io.File;
import java.io.FileWriter;
import java.time.Duration;
import java.util.List;

import com.opencsv.CSVWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class CarData extends Homepage {
	
//	public static void main(String[] args) throws Exception {
//		String[] demo2= {"2024","11","18"}; 
//		String[] demo3= {"2024","11","19"}; 
//		ChromeOptions opt = new ChromeOptions();
//		opt.addArguments("--incognito");
//		opt.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
//		driver=new ChromeDriver(opt);
//		driver.manage().deleteAllCookies();
//		driver.manage().window().maximize();
//		driver.get("https://www.hertz.com/rentacar/location");
//		Thread.sleep(Duration.ofSeconds(60).toMillis());
//		
//		scrapdata("Calgary International Airport",demo2,"10:00",demo3,"10:00");
////	for(String df:pntdemo) {
////		System.out.println(df);
////	}
//}
	
//	Method to scrap data from the List of Cars Webpage 
	public static void scrapdata(String pickuploc, String[] pickupdate, String pickuptime, 
			String[] dropdate, String droptime ) throws Exception {
	
//		Handling FIle creation of CSV file
		String fname="Cardatanew.csv";
		FileWriter write = null;
		File f = new File(fname);
		if(f.exists()) {
			write = new FileWriter(fname,true);
			write.append("\n");
		} else {
//			System.out.println(f.exists());
			write = new FileWriter(fname);
			String[] header= {"Car Name", "PassengerCapacity", "Car Type", "Transmission", "Price", 
	                   "Location", "pickupDate", "Luggage", "Range or Mileage", "Website"};
			CSVWriter writecsv = new CSVWriter(write);
			writecsv.writeNext(header);
		}
		
//		Selecting Vehicle Grid Box available on the Pages
		List<WebElement> vehicles = driver.findElements(By.xpath("//*[@class='gtm-vehicle']"));
		System.out.println("Total Number Of Cars available to rent: "+vehicles.size());
		
		if(vehicles.size()<1)
			System.out.println("No Car available at this location/date. Please try again");
		
//		Looping through Each Vehicle box to Fetch the car details
		for(WebElement vehicle:vehicles) {
			String vetype=vehicle.findElement(By.xpath(".//*[@class='gtm-vehicle-type']")).getText();
			String vepassengers = vehicle.findElement(By.xpath(".//*[@class='gtm-vehFeature-passengers']")).getText();
			String vetitle = vehicle.findElement(By.xpath(".//*[@class='gtm-vehicle-title']")).getText();
			String vetransmission =vehicle.findElement(By.xpath(".//*[@class='gtm-vehFeature-transmission']//*[@class='gtm-vehFeatureDesc']")).getText();
			String price=vehicle.findElement(By.xpath(".//*[@class='gtm-price']")).getText();
			String luggage = vehicle.findElement(By.xpath(".//*[@class='gtm-vehFeature-suitcases']//*[@class='gtm-vehFeatureDesc']")).getText();
			
//			Writing the data fetched into the CSV
			write.append(vetype.split("or similar")[0].split("\\)")[1]).append(",");
			write.append(vepassengers).append(",");
			write.append(vetitle).append(",");
			write.append(vetransmission).append(",");
			if(price.isEmpty())
				write.append("Call to Book").append(",");
			else
				write.append(price+" CAD").append(",");
			write.append(pickuploc).append(",");
//			write.append("\""+pickuploc+"\"").append(",");
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
					write.append("NA").append(",");
				}
			}
				write.append("https://www.hertz.com/rentacar").append(",");
				write.append("\n");
		}
//		Closing CSV file
		write.close();
//		System.out.println("CSV writing completed === Webscraping completed");
	}
}