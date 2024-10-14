package com.webscraping.CarRental;


import java.io.File;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;

import com.assertthat.selenium_shutterbug.core.*;

public class utility {
	
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
}
