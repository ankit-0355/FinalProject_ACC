package com.webscraping.CarRental;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Pattern;


public class Spellchk extends utility{
	
	static String userwrd=null;
	static HashMap<Integer, String> hm = new HashMap<Integer, String>();
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Please enter a word: ");
		BufferedReader brui = new BufferedReader(new InputStreamReader(System.in));
		userwrd=brui.readLine();
		vocab("/Users/ankitprajapati/Desktop/Ecllipse/CarRental/Cardata.csv");
	}
	
	public static void vocab(String fname) throws Exception {
		File fobj= new File(fname);
		String slst = Files.readString(fobj.toPath());
		String[] tmp = slst.split("[,\n]+");
//		System.out.println(Arrays.toString(tmp));
		
		StringBuilder sb = new StringBuilder();
		for (String word:tmp) {
			word=word.trim();
			if(!word.isEmpty() && Pattern.matches("[a-zA-Z -]+",word)) {
//				System.out.println(word);
				sb.append(word).append(",");
			}
		}
		String[] sl=sb.toString().split(",");
//		System.out.println(Arrays.toString(sl));
		int arsz = sl.length;
		arsz=Prime(arsz);
		DoubleHashTable dh = new DoubleHashTable(arsz);
		for (String wd:sl) {
				dh.insert(wd);
//				System.out.println(wd);
		}
		
		boolean iswexist=dh.search(userwrd);
//		System.out.println(dh.search(userwrd));
//		System.out.println(dh.size);
		
		if(!(iswexist)) {
			int[] arr = new int[sl.length];
			int i=0;
			for (String eachword:sl) {
				int temp=calculateEditDistance(userwrd, eachword);
				hm.put(temp, eachword);
				arr[i++]=temp;
			}
			
//			System.out.println(hm.keySet());
//			System.out.println(hm.values());
			mrgsrt(arr,0, arr.length-1);
//			System.out.println(Arrays.toString(arr));
			System.out.println("\nWord not found. Following are the top 5 words suggested: ");
			for(int k=0;k<5 && k<arr.length;k++) {
			System.out.println("Do you mean: "+hm.get(arr[k])+" ?");
			}
//			System.out.println("Invalid word. Do you mean: "+hm.get(arr[1])+" ?");
//			System.out.println("Invalid word. Do you mean: "+hm.get(arr[2])+" ?");
			
		}
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
