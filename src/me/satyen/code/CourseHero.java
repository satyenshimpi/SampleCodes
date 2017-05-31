package me.satyen.code;

import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class CourseHero {
	 public static void main(String[] args) {
//		 String[] arr = "CS111 Fall 2016".split("\\d+|[A-Za-z]+");
//		 for (int i = 0; i < arr.length; i++) {			
//			 System.out.println(arr[i]);
//		}
	      int iiii =1;
	      switch(iiii){
	      case 1: 
	    	  System.out.println("case 1");
	    	  break;
	      default: 
	    	  System.out.println("default");
	    	  break;
	      }
//		 System.out.println(Semester.valueOf("Fall"));
		 System.out.println(Semester.valueOf("SU").getName());
//		    parseInputRegExPatMat("CS111 Fall 2016");
//		    System.out.println("-----------------");
//		    parseInputRegExPatMat("CS111 Summer2016");
//		    System.out.println("-----------------");
//		    parseInputRegExPatMat("CS111 2016 S");
//		    System.out.println("-----------------");
//		    parseInputRegEx("CS111 2016w");
//		    System.out.println("-----------------");
		 parseInputRegExPatMat("C1 SU16");
//		    System.out.println("-----------------");
//		    parseInputRegEx("CS111 F16");
//		    System.out.println("-----------------");
//		    parseInputRegEx(" CS-111 2016 Fall");
//		    System.out.println("-----------------");
//		    parseInputRegEx("CS111 2016Fall");
		  }
		  
		  //Constants for field names
		  public static final String DEPT_FIELD = "Department";
		  public static final String COURSE_NUM_FIELD = "Course Number";
		  public static final String YEAR_FIELD = "Year";
		  public static final String SEMESTER_FIELD = "Semester";
		  
		  /* Parse given input course string and print the data in Normalized form.
		  for each input format, identify and prints the Department, Course Number, Year and Semester
		  */
		  private static void parseInput(String input){
		    if(input == null || input.length() == 0){
		      //log the invalid input
		      System.out.println("Returning from here. Empty input. input=" + input);
		      return;
		    }

		    //get all chars as char array
		    char[] cArr = input.toCharArray();
		    boolean spaceFound = false;
		    
		    String deptStr = "";
		    String courseStr = "";  //can be a long
		    String yearStr = "";
		    String semesterStr = "";
		    
		    int startIndex = 0;
		    int endIndex = 0;
		    for(int i=0; i<cArr.length; i++){
		      if(spaceFound && Character.isLetter(cArr[i])){
		          ;
		      }else if(Character.isDigit(cArr[i])){
		        if(startIndex==0){ //means the end of dept
		          deptStr = input.substring(startIndex,i);
		          startIndex = i;
		        }else if(spaceFound){  //sem+year stated
		          ;
		        }
		      }else if(Character.isSpace(cArr[i])){
		        if(startIndex==0){ //means the end of dept
		          deptStr = input.substring(startIndex,i);
		          startIndex = i+1;   //skip the space 
		        }else if(spaceFound){  //sem+year stated
		          yearStr = input.substring(startIndex, i);
		        }else{
		          courseStr = input.substring(startIndex,i);
		          startIndex = i+1;
		          spaceFound = true;
		        }
		      }
		    }
		  }
		  

		  private static void parseInputRegExPatMat(String input) {
				System.out.println("input=" + input);
				String deptStr = "";
				String courseStr = ""; // can be a long
				String yearStr = "";
				String semesterStr = "";
				input = input.trim();			

			  Pattern p = Pattern.compile("\\d+|[A-Za-z]+");
		         Matcher m = p.matcher(input);
		         int cnt = 0;
		         
		         while (m.find()){
		        	 cnt++;
		        	 switch(cnt){
		        	 case 1: 
		        		 deptStr = m.group();
		        		 break;
		        	 case 2: 
		        		 courseStr = m.group();
		        		 break;
		        	 default:
		        		 String tmp = m.group();
		        		 if (Character.isAlphabetic(tmp.charAt(0))) {
		        			 semesterStr = tmp;
		        		 }else{
		        			 yearStr = tmp;
		        		 }
		        		 break;
		        	 }
		        	 //System.out.println(m.group());
		         }
		 		if(semesterStr.length() < 3) semesterStr = Semester.valueOf(semesterStr.toUpperCase()).getName(); 
				if(yearStr.length()<3) yearStr = String.valueOf(Integer.valueOf(yearStr) + 2000);
				System.out.println(deptStr);
				System.out.println(courseStr);
				System.out.println(yearStr);
				System.out.println(semesterStr);
		  }
		  
		  private static void parseInputRegExStack(String input) {
				System.out.println("input=" + input);
				String deptStr = "";
				String courseStr = ""; // can be a long
				String yearStr = "";
				String semesterStr = "";
				input = input.trim();
				char[] cArr = input.toCharArray();
				Stack<Character> st = new Stack<Character>();
				
				for (int i = 0; i < cArr.length; i++) {
					if(!st.isEmpty()){
						if(Character.isDigit(st.peek()) && Character.isDigit(cArr[i])){
							st.push(cArr[i]);
						} else if(Character.isAlphabetic(st.peek()) && Character.isAlphabetic(cArr[i])){
							st.push(cArr[i]);
						} else {
							getStringFromStack(st);
							st.clear();
							if(Character.isDigit(cArr[i]) || Character.isAlphabetic(cArr[i])){
								st.push(cArr[i]);
							}
						}
					} else if(Character.isDigit(cArr[i]) || Character.isAlphabetic(cArr[i])){
						st.push(cArr[i]);
					}
				}
				if(!st.isEmpty()){
					getStringFromStack(st);
				}
		  }

		private static void getStringFromStack(Stack<Character> st) {
			Character[] arr = new Character[1];
			arr = st.toArray(arr);
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < arr.length; j++) {
				sb.append(arr[j]);
			}
			System.out.println(sb.toString());
		}
		  
	private static void parseInputRegEx(String input) {
		System.out.println("input=" + input);
		String deptStr = "";
		String courseStr = ""; // can be a long
		String yearStr = "";
		String semesterStr = "";
		input = input.trim();			//added later .*********
		String[] splits = input.split("[\\s:-]*[0-9].*");
		deptStr = splits[0].trim();
		
		input = input.substring(deptStr.length()).trim();
		if(input.matches("^[\\s:-]+.*")){
			splits = input.split("^[\\s:-]+");
			input = splits[1].trim();
		}
		splits = input.split("\\s+.*");
		courseStr = splits[0];

		input = input.substring(courseStr.length()).trim();
		// if the next string starts with sem
		if (Character.isAlphabetic(input.charAt(0))) {
			splits = input.split("[0-9]+");
			semesterStr = splits[0];
			yearStr = input.substring(semesterStr.length()).trim();
		} else { // starts with year
			splits = input.split("[a-zA-Z]+");
			yearStr = splits[0];
			semesterStr = input.substring(yearStr.length()).trim();
		}

		if(semesterStr.length() < 3) semesterStr = Semester.valueOf(semesterStr.toUpperCase()).getName(); 
		if(yearStr.length()<3) yearStr = String.valueOf(Integer.valueOf(yearStr) + 2000);
		System.out.println(deptStr);
		System.out.println(courseStr);
		System.out.println(yearStr);
		System.out.println(semesterStr);

	}
	
	enum Semester{
		F("Fall"), W("Winter"), S ("Spring"), SU("Summer");
		private String name;
		Semester(String s){
			name = s;
		}
		
		public String getName(){
			return this.name;
		}
	}
}
