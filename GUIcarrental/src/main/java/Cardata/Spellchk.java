package Cardata;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Pattern;

import org.apache.hc.core5.http.message.BufferedHeader;


public class Spellchk extends utility{
	
	static String userwrd=null;
	static HashMap<Integer, String> hm = new HashMap<Integer, String>();
	
	public static void main(String[] args) throws Exception {
//		// TODO Auto-generated method stub
		System.out.println("Please enter a word: ");
		BufferedReader brui = new BufferedReader(new InputStreamReader(System.in));
		userwrd=brui.readLine();
		vocab(userwrd);
	}
	
	public static String[] vocab(String userwrd) throws Exception {
		String[] rtmp=new String[3];
//		FileReader fobj= new FileReader("/Users/ankitprajapati/Desktop/Ecllipse/CarRental/Cardata.csv");
		FileReader fobj= new FileReader("/Users/ankitprajapati/Desktop/Cardata temp.csv");
		String line;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(fobj);
//		String slst = Files.readString(fobj.toPath());
		while((line = br.readLine()) != null) {
			String[] tmp = line.split("[,\n]+");
			for (String word:tmp) {
				word=word.trim();
				if(!word.isEmpty() && Pattern.matches("[a-zA-Z -]+",word)) {
//					System.out.println(word);
					sb.append(word).append(",");
				}
			}
		}
//		System.out.println(sb.toString());
		
		String[] sl=sb.toString().split(",");
//		System.out.println(Arrays.toString(sl));
		int arsz = sl.length;
		arsz=Prime(arsz);
		DoubleHashTable dh = new DoubleHashTable(arsz);
		for (String wd:sl) {
			if(!dh.search(wd)) {
				dh.insert(wd);
//				System.out.println(wd);
			}
		}
		
		boolean iswexist=dh.search(userwrd);
//		System.out.println(dh.search(userwrd));
//		System.out.println(dh.size);
		
		if(!(iswexist)) {
			
			List<String> prefixMatches = new ArrayList<>();
	        Map<Integer, String> editDistanceMatches = new HashMap<>();
	        
	        // Look for words that start with the same prefix as userwrd
	        for (String eachword : sl) {
	            if (eachword.startsWith(userwrd)) {
	                prefixMatches.add(eachword);
	            } else {
	                // If no prefix match, use edit distance as backup
	                int temp = calculateEditDistance(userwrd, eachword);
	                editDistanceMatches.put(temp, eachword);
	            }
	        }
	        
	     // Sort prefix matches as top suggestions
	        if (!prefixMatches.isEmpty()) {
//	            System.out.println("\nWord not found. Showing top prefix-matching suggestions:");
	            for (int k = 0; k < 3 && k < prefixMatches.size(); k++) {
	                rtmp[k] = prefixMatches.get(k);
//	                System.out.println("Do you mean: " + prefixMatches.get(k) + " ?");
	            }
	        } else {
	            // If no prefix matches, sort by edit distance
	            int[] arr = editDistanceMatches.keySet().stream().mapToInt(i -> i).toArray();
	            Arrays.sort(arr);
//	            System.out.println("\nWord not found. Showing top edit distance-based suggestions:");
	            for (int k = 0; k < 3 && k < arr.length; k++) {
	                rtmp[k] = editDistanceMatches.get(arr[k]);
//	                System.out.println("Do you mean: " + editDistanceMatches.get(arr[k]) + " ?");
	            }
			}
		}
		System.out.println(Arrays.toString(rtmp));
		return rtmp;
	}

	public static class DoubleHashTable {
		String[] tbl;
		int sze;

		public DoubleHashTable(int sze) {
			this.sze = sze;
			tbl = new String[sze];
			Arrays.fill(tbl, null); 
		}

		private int h1ash(String key) {
			return (key.hashCode() % tbl.length + tbl.length) % tbl.length;
		}

		private int h2ash(String key) {
			return (7 - (key.hashCode() % 7));
		}

		public void insert(String key) {
			int h1ash = h1ash(key);
			int stepsz = h2ash(key);
			while (tbl[h1ash] != null) {
				h1ash = (h1ash + stepsz) % sze;
			}
			tbl[h1ash] = key;
		}

		public boolean search(String key) {
			int h1ash = h1ash(key);
			int stpsz = h2ash(key);
			int cnt = 0;
			while (tbl[h1ash] != null && cnt < sze) {
				if (tbl[h1ash].equals(key)) return true;
				h1ash = (h1ash + stpsz) % sze;
				cnt++;
			}
			return false;
		}

		public void printTable() {
			System.out.println("Values in the hash table:");
			for (int i = 0; i < sze; i++) {
				if (tbl[i] != null) {
					System.out.println("Slot " + i + ": " + tbl[i]);
				}
			}
		}
	}
	
	public static int calculateEditDistance(String w1ord, String w2ord) {
        int le1 = w1ord.length();
        int le2 = w2ord.length();

        int[][] dp = new int[le1 + 1][le2 + 1];

        for (int i = 0; i <= le1; i++) {
            for (int j = 0; j <= le2; j++) {
                if (i == 0) {
                    dp[i][j] = j; 
                }
                else if (j == 0) {
                    dp[i][j] = i; 
                }
                else if (w1ord.charAt(i - 1) == w2ord.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], 
                    Math.min(dp[i][j - 1],    
                    dp[i - 1][j - 1])); 
                }
            }
        }
        return dp[le1][le2];
    }
	
	public static int Prime(int num) {
		num=num*2;
		while (true) {
			boolean isPrime = true;
			if (num < 2) return 2; // Return 2 for numbers less than 2
			for (int i = 2; i * i <= num; i++) {
				if (num % i == 0) {
					isPrime = false;
					break;
				}
			}
			if (isPrime) 
				return num; 
			num++; 
		}
	}

    public static void mrgsrt(int[] ar, int lft, int rgt) {
        if (lft < rgt) {
            int mid = (lft + rgt) / 2;

            mrgsrt(ar, lft, mid);
            mrgsrt(ar, mid + 1, rgt);

            mrg(ar, lft, mid, rgt);
        }
    }

    public static void mrg(int[] ar,  int lft, int mid, int rgt) {
        int z1 = mid - lft + 1;
        int z2 = rgt - mid;

        int[] l = new int[z1];
        int[] r = new int[z2];


        for (int i = 0; i < z1; i++) {
            l[i] = ar[lft + i];
        }	
        
        for (int j = 0; j < z2; j++) {
            r[j] = ar[mid + 1 + j];
        }

        int i = 0, j = 0;

        int k = lft;
        while (i < z1 && j < z2) {
            if (l[i] <= r[j]) {
                ar[k] = l[i];
                i++;
            } else {
                ar[k] = r[j];
                j++;
            }
            k++;
        }

        while (i < z1) {
            ar[k] = l[i];
            i++;
            k++;
        }

        while (j < z2) {
            ar[k] = r[j];
            j++;
            k++;
        }
    }
	
}
