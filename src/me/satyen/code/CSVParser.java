package me.satyen.code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CSVParser {
	public static String DELIMITER = ",";
	public static String QUALIFIER = "\"";
	public static char DELIMITER_CHAR = ',';
	public static char QUALIFIER_CHAR = '"';
	
	public static void main(String[] args){
		try {
			readFile("C:\\Users\\Svara\\workspace\\CodingPractise\\src\\me\\satyen\\code\\sample.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void readFile(String filename) throws Exception{
		File file = new File(filename);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while( (line = br.readLine()) != null){
//			String [] arr = line.split(DELIMITER);
//			printSplits(arr);
			parseLine(line);
		}
		br.close();
		fr.close();
	}
	
	static void parseLine(String line){
		int i =0;
		int wordStart = 0;
		boolean qualifierStart = false;
		String word = "";
		while(i< line.length()){
			char c = line.charAt(i);
			
			if(c==QUALIFIER_CHAR){
				if(!qualifierStart){
					wordStart = i;
					qualifierStart = true;
				}else{
					//end of Qualifier
					qualifierStart = false;
				}
			}else if((!qualifierStart && c==DELIMITER_CHAR)){
				//word complete
				word = line.substring(wordStart, i);
				wordStart = i+1;
				System.out.print(word + "|");
			}
			i++;
			//get the last word 
			if(i == line.length()){
				word = line.substring(wordStart, i);
				wordStart = 0;
				System.out.print(word);
			}
		}
		System.out.println();
	}
	
	static void parseLineStringTokenizer(String line){
		StringTokenizer st = new StringTokenizer(line, ",");
		st.nextToken();
	}
	
	static void printSplits(String[] arr){
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + "|\t|"
					+ "");
		}
		System.out.println();
	}
}
