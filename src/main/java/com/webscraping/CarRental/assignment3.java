package com.webscraping.CarRental;


import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.webscraping.Triest.TrieST;




public class assignment3 {
	
	static String wrd;
	static StringBuilder stbr = new StringBuilder();
	static TrieST<Integer> st = new TrieST<Integer>();

	public static void main(String[] args) throws Exception {
		
//		System.out.println("Please Enter a word: ");
//		Scanner sc = new Scanner(System.in);
//		wrd=sc.nextLine();
//		System.out.println(wrd);

		Scanner scfr = new Scanner(new File("/Users/ankitprajapati/Desktop/Ecllipse/CarRental/Cardata 2.csv"));
		while(scfr.hasNextLine()) {
			String nextline=scfr.nextLine();
//			System.out.println(nextline);
			String[] tmp = nextline.split("[,\n\"]+");
			for (int i=0;i<tmp.length;i++) {
//				System.out.println(tmp[i]);
				if(Pattern.matches("[a-zA-Z -\"()]+", tmp[i])) {
					stbr.append(tmp[i]).append(",");
					st.put(tmp[i], i);
				}
			}
		}
		
		for (String key : st.keys()) {
            System.out.println(key + " " + st.get(key));
		}
			
//			System.out.println(Arrays.toString(tmp));
		System.out.println(stbr.toString());
		}
	}
