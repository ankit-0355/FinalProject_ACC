package com.webscraping.CarRental;


import java.io.File;
import java.util.Arrays;

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
//	public static int Prime(int num) {
//		while (true) {
//			boolean isPrime = true;
//			if (num < 2) return 2; // Return 2 for numbers less than 2
//			for (int i = 2; i * i <= num; i++) {
//				if (num % i == 0) {
//					isPrime = false;
//					break;
//				}
//			}
//			if (isPrime) 
//				return num; // Return if it's prime
//			num++; // Increment and check the next number
//		}
//	}
//	// Double Hashing Hash Table
//	public static class DoubleHashingHashTable {
//		String[] table;
//		int size;
//
//		public DoubleHashingHashTable(int size) {
//			this.size = size;
//			table = new String[size];
//			Arrays.fill(table, null); // 0 indicates an empty slot
//		}
//
//		private int hash1(String key) {
//			return (key.hashCode() % table.length + table.length) % table.length;
//		}
//
//		private int hash2(String key) {
//			return (7 - (key.hashCode() % 7));
//		}
//
//		public void insert(String key) {
//			int hash = hash1(key);
//			int stepSize = hash2(key);
//			while (table[hash] != null) {
//				hash = (hash + stepSize) % size;
//			}
//			table[hash] = key;
//		}
//
//		public boolean search(String key) {
//			int hash = hash1(key);
//			int stepSize = hash2(key);
//			int count = 0;
//			while (table[hash] != null && count < size) {
//				if (table[hash].equals(key)) return true;
//				hash = (hash + stepSize) % size;
//				count++;
//			}
//			return false;
//		}
//
//		public void printTable() {
//			System.out.println("Values in the hash table:");
//			for (int i = 0; i < size; i++) {
//				if (table[i] != null) {
//					System.out.println("Slot " + i + ": " + table[i]);
//				}
//			}
//		}
//	}
}
