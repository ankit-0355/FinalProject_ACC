package com.webscraping.CarRental;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;


public class AppTest extends App{
		
    @Test
    public void testApp() throws Exception  {
//		Getting user inputs
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Please enter Pickup Location: ");
		pickuploc=br.readLine();
		
		System.out.print("Please enter Pickup Date in DD-MM-YYYY format: ");
		 pickupdate= DP.datechck(br);
		
		System.out.print("Please Enter Pickup time in HH:MM format: ");
		pickuptime = TP.timechck(br);
		
		System.out.print("Please enter Dropoff Date in DD-MM-YYYY format: ");
		dropdate= DP.datechck(br);
		
		System.out.print("Please Enter Drop time in HH:MM format: ");
		droptime = TP.timechck(br);
		
		System.out.println("========End Of User input======");
		
		websitepages();
		CD.scrapdata();
        	
		String s = "Hello";
    	assertEquals(s, "Hello");
    	}
    
    @AfterAll
    public void closebrowser() {
    	driver.close();
    	driver.quit();
    }
    
}
