package com.webscraping.CarRental;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TimePicker extends App {
	
//	Method to pick the time entered by User
	public void PickTime(String time, WebElement PickTime) {
		PickTime.click();
		if(PickTime.getAttribute("id").equalsIgnoreCase("pickup-time"))
		{
			WebElement Time = driver.findElement(By.xpath("//*[@class='time-box']//option[@value='"+time+"']"));
			Time.click();
		} else {
			driver.findElement(By.xpath("(//*[@class='time-box']//option[@value='"+time+"'])[2]")).click();
		}
	}
	
//	Method TO Validate The Format of the TIme entered
	public String timechck (BufferedReader br) throws IOException {
		String timepattern="^([01][0-9]|2[0-3]):([0-5][0-9])$";
		String tm;
		while(true) {
			tm = br.readLine();
			if(Pattern.matches(timepattern, tm))
				break;
			else
				System.out.println("Incorrect Time please try again...");
			}
        return tm;
		}
}
