package com.webscraping.CarRental;


import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;


public class App {
	
//	Declaring WebDriver and Waiting
	static WebDriver driver;
	static WebDriverWait waiting;
	
//	Object creations
	static DatePicker DP = new DatePicker();
	static TimePicker TP =new TimePicker();
	static CarData CD = new CarData();
	static utility utility = new utility();
	
//	Declare Variables for user input
	static String pickuploc;
	static String[] pickupdate;
	static String pickuptime;
	static String[] dropdate;
	static String droptime;
    
//	Method to Enter user details in the Web Form
	public static void websitepages() throws Exception{
		
//		Calling the URL in browser
		ChromeOptions opt = new ChromeOptions();
		opt.addArguments("--incognito");
		driver=new ChromeDriver(opt);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get("https://www.hertz.com/rentacar/reservation");
		waiting = new WebDriverWait(driver, Duration.ofSeconds(30));
		
//		Dealing with Cookies pop-up
		waiting.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath( "//*[@class=\"cc-btn cc-allow cc-btn-format\"]")));
		driver.findElement(By.xpath("//*[@class=\"cc-btn cc-allow cc-btn-format\"]")).click();
		
//		Entering pickup location
		WebElement Loc = driver.findElement(By.xpath("//input[@id=\"pickup-location\"]"));
		Loc.sendKeys(pickuploc);
		waiting.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class=\"wL\"]")));
		driver.findElement(By.xpath("//div[@class=\"ww-item\"][1]")).click();
		pickuploc=Loc.getAttribute("value");
		
//		Setting pickup date and time
		WebElement PickupDate = driver.findElement(By.xpath("//*[@id='pickup-date-box']"));
		DP.PickDate(Integer.parseInt(pickupdate[0]),Integer.parseInt(pickupdate[1]),Integer.parseInt(pickupdate[2]),driver, PickupDate);
		WebElement PickupTime = driver.findElement(By.xpath("//*[@id='pickup-time']"));
		TP.PickTime(pickuptime,PickupTime);
		System.out.println("Pickup time and date selected");
		
		Thread.sleep(Duration.ofSeconds(2));
		
//		Setting Drop date and  time
		WebElement DropDate = driver.findElement(By.xpath("//*[@id='dropoff-date-box']"));
		DP.PickDate(Integer.parseInt(dropdate[0]),Integer.parseInt(dropdate[1]),Integer.parseInt(dropdate[2]), driver, DropDate);
		WebElement DropTime = driver.findElement(By.xpath("//*[@id='dropoff-time']"));
		TP.PickTime(droptime,DropTime);
		System.out.println("Drop Off time and date selected");
		
		Thread.sleep(Duration.ofSeconds(2));
		
//		Selecting Age of Driver
		driver.findElement(By.xpath("(//*[@id='ageSelector']//option[@value='25'])[2]")).click();
		
//		Calling method to capture screenshot of the web page 
		utility.captureScreenshot("Homepage.png",driver);
		
//		Searching cars with the filled Information
		driver.findElement(By.xpath("//*[@id='new-resSubmit']")).click();
		
		Thread.sleep(Duration.ofSeconds(5));
		
//		Calling method to capture screenshot of the web page
		utility.captureScreenshot("CarList.png",driver);
	}
}
