package com.webscraping.CarRental;

import java.io.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.regex.Pattern;
import java.util.*;


class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEndOfWord = false;
}

class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    //word inserted in the Trie
    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toLowerCase().toCharArray()) {
            if (ch < 'a' || ch > 'z') {
                continue; // Skip characters that are not between 'a' and 'z'
            }
            int index = ch - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isEndOfWord = true;
    }
    
    // Check word exists?
    public boolean search(String word) {
        TrieNode node = root;
        for (char ch : word.toLowerCase().toCharArray()) {
            int index = ch - 'a';
            if (node.children[index] == null) {
                return false;
            }
            node = node.children[index];
        }
        return node.isEndOfWord;
    }

    // Get all words in Trie (for suggestions)
    public void collectWords(TrieNode node, String prefix, Set<String> words) {
        if (node == null) return;
        if (node.isEndOfWord) words.add(prefix);

        for (char c = 'a'; c <= 'z'; c++) {
            TrieNode nextNode = node.children[c - 'a'];
            if (nextNode != null) {
                collectWords(nextNode, prefix + c, words);
            }
        }
    }

    public Set<String> getAllWords() {
        Set<String> words = new HashSet<>();
        collectWords(root, "", words);
        return words;
    }
}

public class temp {
    private Trie trie;

    public temp() {
        trie = new Trie();
    }

    // Load vocabulary from CSV file
    public void loadVocab(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String li;
        while ((li = br.readLine()) != null) {
            String[] wds = li.split("[ ,\n\"]+");
            for (String wd : wds) {
//            	System.out.println(word);
            	if(Pattern.matches("[a-zA-Z -\"()]+", wd)) {
//            		System.out.println(wd);
            		trie.insert(wd.trim().toLowerCase());
            	}
            }
        }
        br.close();
    }   
    
    // Suggestions based on Edit Distance 
    public String[] suggestAlternatives(String word) {
        HashMap<String, Integer> distMap = new HashMap<>();

        // Populate the distanceMap with words and their edit distances
        for (String vocabWord : trie.getAllWords()) {
            int distance = calcED(word.toLowerCase(), vocabWord);
            distMap.put(vocabWord, distance);
        }

        // Use a PriorityQueue to sort words by their edit distance (smallest distance first)
        PriorityQueue<SimpleEntry<String, Integer>> suggestionQueue = new PriorityQueue<>(
            Comparator.comparingInt(SimpleEntry::getValue)
        );

        // Add each entry from the distanceMap to the PriorityQueue
        for (Map.Entry<String, Integer> entry : distMap.entrySet()) {
            suggestionQueue.add(new SimpleEntry<>(entry.getKey(), entry.getValue()));
        }

        String[] suggestions = new String[5];
        int i = 0;

        // Retrieve the top 5 closest matches from the PriorityQueue
        while (i < 5 && !suggestionQueue.isEmpty()) {
            suggestions[i] = suggestionQueue.poll().getKey();
            i++;
        }

        return suggestions;
    }

    // ED method for diff b/w words
    private int calcED(String word1, String word2) {
        int[][] ed = new int[word1.length() + 1][word2.length() + 1];
        
        for (int g = 0; g <= word1.length(); g++) {
            for (int h = 0; h <= word2.length(); h++) {
                if (g == 0) {
                    ed[g][h] = h;
                } else if (h == 0) {
                    ed[g][h] = g;
                } else if (word1.charAt(g - 1) == word2.charAt(h - 1)) {
                    ed[g][h] = ed[g - 1][h - 1];
                } else {
                    ed[g][h] = 1 + Math.min(ed[g - 1][h - 1], Math.min(ed[g - 1][h], ed[g][h - 1]));
                }
            }
        }
        return ed[word1.length()][word2.length()];
    }

    // Check word and suggest alternatives if not found
    public void checkWord(String wd) {
        if (trie.search(wd.toLowerCase())) {
            System.out.println(wd + " present in vocabulary.");
        } else {
            System.out.println(wd + " absent in the vocabulary. Suggestions:");
            String[] suggestions = suggestAlternatives(wd);
            for (String suggest : suggestions) {
                if (suggest != null)   // Check to avoid printing null values
                    System.out.println(suggest);
            }
        }
    }

    public static void main(String[] args)  {
        temp spellChecker = new temp();
        try {
            // Creating vocab from CSV file
            spellChecker.loadVocab("/Users/ankitprajapati/Desktop/Ecllipse/CarRental/Cardata.csv");

            // Enter words to check
            System.out.println("Enter a Word for spell check");
            Scanner scrd = new Scanner(System.in);
            String wordtochk = scrd.next();
            spellChecker.checkWord(wordtochk);

        } catch (IOException e) {
            System.out.println("Error loading vocabulary: " + e.getMessage());
        }
    }
}
