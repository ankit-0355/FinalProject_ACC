package Cardata;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.assertthat.selenium_shutterbug.core.*;

public class utility {
	public static int carTypeIndex = -1;
	public static int LocIndex=-1;
	
	//	Method to Capture Screenshot and saving files
	public void captureScreenshot(String filePath, WebDriver driver) throws Exception {

		if(filePath.isBlank()) {
			Shutterbug.shootPage(driver, Capture.FULL,true).save();
		} else {

			PageSnapshot ss = Shutterbug.shootPage(driver, Capture.FULL,true);
			//        Create a destination file
			File destFile = new File("./screenshots/"+ filePath);
			ImageIO.write(ss.getImage(), "PNG", destFile);
			//        ImageIO.write(tkss.getImage(), "PNG", destFile);
		}
	}
	
//	Method TO Validate The Format of the TIme entered
	public boolean timechck (String tm) throws IOException {
		boolean flag=true;
		String timepattern="^([01][0-9]|2[0-3]):([0-5][0-9])$";
		while(true) {
//			tm = br.readLine();
			if(Pattern.matches(timepattern, tm))
				break;
			else
				flag=false;
				System.out.println("Incorrect Time please try again...");
				break;
			}
    return flag;
	}
	
	// Method to get all car details within the budget
    public static List<String[]> getAllCarDetails(String bud, String csvFilePath,String pickLoc) {

    	double budget = Double.parseDouble(bud);
        List<String[]> carDetailsWithinBudget = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String headerLine = br.readLine(); // Read header line
            String[] headers = headerLine.replaceAll("\"", "").split(",");
            int priceIndex = -1;
//            int carTypeIndex = -1;
//            int LocIndex = -1;
            
            System.out.println(headerLine);
            // Find indices of "Price" and "Car Type" columns
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equalsIgnoreCase("Price")) {
                    priceIndex = i;
                } else if (headers[i].equalsIgnoreCase("Car Type")) {
                    carTypeIndex = i;
                } else if (headers[i].equalsIgnoreCase("Location")) {
                	LocIndex = i;
                }
            }

            if (priceIndex == -1 || carTypeIndex == -1) {
                System.out.println("Required columns (Price or Car Type) not found in the CSV file.");
                return null;
            }

            // Read and filter cars by budget
            String line;
            while ((line = br.readLine()) != null) {
                String[] carData = line.split(",");
                if (carData[priceIndex].equalsIgnoreCase("Call to Book")) {
                    continue;
                }

                // Extract and process the price
                String priceText = carData[priceIndex].replace(" CAD", "").trim();
                String LocTxt = carData[LocIndex];
                System.out.println(LocTxt);

                try {
                    double price = Double.parseDouble(priceText);
                    if (price <= budget && LocTxt.equalsIgnoreCase(pickLoc)) {
                    	System.out.println(Arrays.toString(carData));
                        carDetailsWithinBudget.add(carData); // Add car details to the list
                    } 
                } catch (NumberFormatException e) {
                    System.out.println("Non-numeric price ignored: " + carData[priceIndex]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String[] ar:carDetailsWithinBudget) {
        	System.out.println(Arrays.toString(ar));
        }
        
        return carDetailsWithinBudget;
    }
    
//    Method to get Sorted Car Type Frequency
    public static LinkedHashMap<String, Integer> getSortedCarTypeFrequency(List<String[]> carDetails, int carTypeIndex) {
        HashMap<String, Integer> carTypeFrequencyMap = new HashMap<>();

        // Calculate frequencies for each car type
        for (String[] carData : carDetails) {
            String carType = (carData[carTypeIndex]);
            carTypeFrequencyMap.put(carType, carTypeFrequencyMap.getOrDefault(carType, 0) + 1);
        }

        // Sort by frequency in descending order
        LinkedHashMap<String, Integer> sortedCarTypeFrequencyMap = new LinkedHashMap<>();
        carTypeFrequencyMap.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .forEach(entry -> sortedCarTypeFrequencyMap.put(entry.getKey(), entry.getValue()));

        return sortedCarTypeFrequencyMap;
    }
    
//    Parse the page
    public static void parsePage(String url) {

        WebDriver driver = new ChromeDriver();
        try {
            // Load the page
            driver.get(url);
            System.out.println("Visiting: " + url);

            // Get the fully rendered page source
            String pageSource = driver.getPageSource();

            // Create the directory if it doesn't exist
            File directory = new File("Parsed_HTML");
            if (!directory.exists()) {
                directory.mkdir();
            }

            // Generate a safe filename using the URL
            String safeFileName = Paths.get(url.replace("https://", "").replaceAll("[^a-zA-Z0-9.-]", "_")).getFileName().toString();
            File file = new File("Parsed_HTML" + File.separator + safeFileName + ".html");

            // Save HTML content to a file
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(pageSource);
            }

            System.out.println("Saved page to: " + file.getAbsolutePath());

        } catch (Exception e) {
            System.err.println("Error accessing or saving file for URL: " + url + " - " + e.getMessage());
        } finally {
            // Close the browser
            driver.quit();
        }
    }
    
    //Convert html to text file
    public static void convertHtmlToText(String htmlFileName) throws IOException {
    	
    	String Directory="/Users/ankitprajapati/Desktop/Ecllipse/GUIcarrental/Parsed_HTML";
        // Create a File object from the base directory and filename
        File htmlFile = new File(Directory, htmlFileName);

        // Check if the file exists
        if (!htmlFile.exists()) {
            System.out.println("File not found: " + htmlFile.getAbsolutePath());
            return;
        }

        // Parse the HTML file and extract the text
        Document doc = Jsoup.parse(htmlFile, "UTF-8");
        String textContent = doc.text();

        // Define the output text file with the same name as the HTML file in a subdirectory
        String outputFileName = htmlFileName.replace(".html", ".txt");
        File outputDir = new File(Directory, "html_text");

        // Ensure the output directory exists
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }

        File textFile = new File(outputDir, outputFileName);

        // Write the text content to the output file
        try (FileWriter writer = new FileWriter(textFile)) {
            writer.write(textContent);
            System.out.println("Converted " + htmlFile.getName() + " to " + textFile.getAbsolutePath());
        }
    }

    //Retrive the list of location in th city
    public static String[] loclst(String cname) throws Exception {
    	String Url="https://www.hertz.com/rentacar/location?search="+cname;
    	StringBuilder loclist = new StringBuilder();
    	ChromeOptions options = new ChromeOptions();
    	options.addArguments("--headless");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

    	WebDriver driver = new ChromeDriver(options);
    	driver.get(Url);
    	Thread.sleep(Duration.ofSeconds(3).toMillis());
    	WebDriverWait waiting = new WebDriverWait(driver, Duration.ofSeconds(10));
//		Dealing with Cookies pop-up
    	try {
		waiting.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"cc-btn cc-allow cc-btn-format\"]")));
		driver.findElement(By.xpath("//*[@class=\"cc-btn cc-allow cc-btn-format\"]")).click();
    	} catch(Exception e) {}
    	List<WebElement> weloclist=driver.findElements(By.xpath("//*[@class=\"loc-results\"]//a[@class='loc-name']"));
		for(WebElement we:weloclist) {
//			System.out.println(we.getText());
			loclist.append(we.getText()).append(",");
		}
		driver.quit();
		return (loclist.toString().split(","));
    }
    
    public static boolean isDateInFuture(LocalDate pickupdate, LocalDate dropdate) {
        
    	boolean flag=false;
    	// Get the current date
        LocalDate currentDate = LocalDate.now();
        if(pickupdate.isAfter(currentDate) && dropdate.isAfter(pickupdate)) {
        	flag = true;
        } 
//        boolean flag = date.isAfter(currentDate);
//        System.out.println(flag);
        // Check if the given date is after the current date
        return flag;
    }
    
	public static void main(String[] args) throws Exception {
//		
			List<String[]> tem = getAllCarDetails("100","/Users/ankitprajapati/Desktop/Ecllipse/GUIcarrental/Cardatanew.csv","Edmonton - Comfort Inn");
//			List<String[]> tem = getAllCarDetails("100","/Users/ankitprajapati/Desktop/Ecllipse/GUIcarrental/Cardata1.csv","Calgary International Airport");
			
		getSortedCarTypeFrequency(tem,carTypeIndex);
//			String[] lst=loclst("calgary");
//		System.out.println(Arrays.toString(lst));
//		for(String tmp:lst) {
//			System.out.println(tmp);
//		demo();
//		}
	}
	
}