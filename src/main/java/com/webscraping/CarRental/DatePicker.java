package com.webscraping.CarRental;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Duration;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DatePicker {
	
//Method to Select date using Date Picker
	public void PickDate(int selday, int selmonth, int selyear, WebDriver driver, WebElement PickDate) throws Exception {
		
		PickDate.click();
		
		int Curyear;
		int Curmon;
		
		while(true) {
			WebElement curdate=driver.findElement(By.xpath("//*[@class='month dual'][1]//div//header//h1"));
			WebElement rightarrow = driver.findElement(By.xpath("//*[@class=\"month dual\"][2]/div/header/span[@class=\"next arrow\"]"));
			WebElement leftarrow = driver.findElement(By.xpath("//*[@class=\"month dual\"][1]/div/header/span[@class=\"prev arrow\"]"));
			
			Curyear =Integer.parseInt(curdate.getText().split(" ")[1]);
			Curmon = Integer.parseInt(curdate.getAttribute("data-automation-id"));
			
			if (Curyear == selyear && Curmon == selmonth) {
				WebElement day = driver.findElement(By.xpath("//*[@class='month dual'][1]//td[text()="+selday+"]"));
				day.click();
				break;
			} 
			if (selyear > Curyear) {
				rightarrow.click();
			} 
			if (selyear < Curyear) {
				leftarrow.click();
			}
			if (selyear == Curyear  && selmonth > Curmon) {
				rightarrow.click();
			}
			if (selyear == Curyear  && selmonth < Curmon) {
				leftarrow.click();
			}
		}
	}
	
//	Method To Validate the Format of the Date Entered
	public String[] datechck (BufferedReader br) throws IOException {
		String datepattern="^(0[1-9]|[12][0-9]|30)-(0[1-9]|1[0-2])-202[4-5]$";
		String date;
		while(true) {
			date = br.readLine();
			if(Pattern.matches(datepattern, date))
				break;
			else
				System.out.println("Incorrect Date please try again...");
			}
		String[] ans=date.split("-");
        return ans;
		}
}
