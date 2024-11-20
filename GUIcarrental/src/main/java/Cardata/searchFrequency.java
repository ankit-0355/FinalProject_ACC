package Cardata;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class searchFrequency {


	    private TreeMap<String, Integer> searchTree;
	    private static final String FILE_PATH = "search_frequencies.txt";

	    public searchFrequency() {
	        searchTree = new TreeMap<>();
	        loadFrequenciesFromFile();
	    }

	    // Load frequencies from a file to restore previous search data
	    private void loadFrequenciesFromFile() {
	        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] parts = line.split(":");
	                if (parts.length == 2) {
	                    String word = parts[0].trim();
	                    int frequency = Integer.parseInt(parts[1].trim());
	                    searchTree.put(word, frequency);
	                }
	            }
//	            System.out.println("Loaded previous search frequencies from file.");
	        } catch (IOException e) {
	            System.out.println("No previous data found. Starting fresh.");
	        }
	    }

	    // Save frequencies to a file for persistence
	    private void saveFrequenciesToFile() {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
	            for (Map.Entry<String, Integer> entry : searchTree.entrySet()) {
	                writer.write(entry.getKey() + ":" + entry.getValue());
	                writer.newLine();
	            }
	            System.out.println("Search frequencies saved to file.");
	        } catch (IOException e) {
	            System.err.println("Error saving frequencies to file: " + e.getMessage());
	        }
	    }

	    // Method to search a word and update its frequency
	    public void searchWord(String word) {
	    	System.out.println("In the searchWord method"+word);
	        searchTree.put(word, searchTree.getOrDefault(word, 0) + 1);
	        System.out.println("Search Frequency for \"" + word + "\": " + searchTree.get(word));
	        saveFrequenciesToFile(); 
	    }// Update file after each search
	        
	        public int getWordFrequency(String word) {
	            return searchTree.getOrDefault(word, 0);
	    }

//	    public static void main(String[] args) throws IOException {
//	    	searchFrequency tracker = new searchFrequency();
//	        
//	        // Example with user input for word search
//	        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//	        System.out.println("Enter words to search. Type 'exit' to quit.");
//	        while (true) {
//	            System.out.print("Enter word: ");
//	            String word = reader.readLine();
//	            if ("exit".equalsIgnoreCase(word)) {
//	                break;
//	            }
//	            tracker.searchWord(word); // Track search frequency
//	        }
//	    }
	}
