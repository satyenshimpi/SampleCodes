package me.satyen.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Strings2 {
	public static void main(String[] args){
		String[] arr = new String[]{"This", 
		 "is", 
		 "an", 
		 "example", 
		 "of", 
		 "text", 
		 "justification."};
		int L= 16;
//		System.out.println(sameDigitNumber(0));
		int[][] matrix = {{1, 0, 0, 2},{0, 5, 0, 1},{0, 0, 3, 5}};
//		int[][] ret = constructSubmatrix(matrix, new int[]{1}, new int[]{0,2});
//		for (int i = 0; i < ret.length; i++) {
//			for (int j = 0; j < ret[i].length; j++) {
//				System.out.print(ret[i][j] + ",");
//			}
//			System.out.println();
//		}
		
		List<String> lst = recGetpermu("abc");
		for (String string : lst) {
			System.out.println(string);
		}
//		System.out.println(14 & 1);
//		System.out.println(14 & 15);
//		System.out.println(14 & 16);
//		System.out.println(14 & 30);
//		System.out.println(14 % 30);
//		System.out.println(30 % 14);
////		String[] op = textJustification(arr, L);
////		for(int i=0; i<op.length; i++){
////			System.out.println(op[i]);
////		}
//		CacheBuilder<Object, Object> bldr = CacheBuilder.newBuilder();
//		bldr.build(new CacheLoader<Object, Object>() {
//			@Override
//			public Object load(Object key) throws Exception {
//				// TODO Auto-generated method stub
//				return null;
//			}
//		});
		//System.out.println(strstr("abcdefghabcdefghi", "ghi"));
	}
	
	static String[] textJustification(String[] words, int L) {
	    if(words==null || words.length < 0) return words;
	    int len = 0;
	    int x=0;
	    int wc = 0; //word count
	    int spaceCt = 0;  //in between spaces Count
	    ArrayList<String> ret = new ArrayList();
	    StringBuffer line = new StringBuffer();
	    for(int i=0; i<words.length; i++, x++){
	        if((len + words[i].length()) < (L - x)){
	            len += words[i].length();
	            wc++;
	        }else{
	            spaceCt = wc != 0 ? (L - len) / (wc-1): 1;
	            for(int j=0; j < wc ; j++){		//we need to enter spaces for wc - 1 times
	                line.append(words[j + i-wc]);
	                if(j != wc-1) line.append(spaces(spaceCt));
	            }
	            ret.add(line.toString());
	            line = new StringBuffer();
	            len = 0;
	            wc=0;
	            x=-1;
	            i--;
	        }
	        if(i==words.length-1){
	        	if(words[i].length() < L)
	        		ret.add(words[i] + spaces(L-words[i].length()));
	        }
	    }
	    
	    return ret.toArray(new String[ret.size()]);
	}

	static String spaces(int spaceCt){
	    StringBuffer ret = new StringBuffer();
	    for(int i=0; i<spaceCt; i++){
	        ret.append(" ");
	    }
	    return ret.toString();
	}

	
	static int strstr(String s, String x) {
	    if(s==null || x==null || s.length() < x.length()) return -1;
	    
	    boolean flag = false;
	    int i=0;
	    for(i=0; i< s.length(); i++){
	        if((s.length() == 1 || i < s.length()-1) && s.charAt(i)==x.charAt(0)){
	        	flag = true;
	            //check if remaining chars to check are th
	            if(s.length() > 1 && s.length()-i < x.length()) return -1;
	            for(int j=1; j< x.length(); j++){
	                if(s.charAt(i+j)!=x.charAt(j)){
	                    flag = false;
	                    break;
	                }
	                flag = true;
	            }
	        }
	        if(flag) break;
	    }
	    if(flag) return i;
	    return -1;
	}
	
	static int strstr2(String s, String x) {
		for (int i = 0; ; i++) {
			for (int j = 0; ; j++) {
				if(j==x.length()) return i;
				if(i+j == s.length()) return -1;
				if(x.charAt(j)!=s.charAt(i+j)) break;
			}
		}
	}
	
    public static ArrayList<String> recGetpermu(String str) {
        ArrayList<String> ret = new ArrayList<String>();
        if (str == null) {
            return null;
        } else if (str.length() == 0) {
            ret.add("");
            return ret;
        }
        char c = str.charAt(0);
        String remaining = str.substring(1);
        ArrayList<String> perms = recGetpermu(remaining);
        for (String sss : perms) {
            for (int i = 0; i <= sss.length(); i++) {
                ret.add(insertCharAtIndex(sss, c, i));
            }
        }
        return ret;
    }
    public static String insertCharAtIndex(String str, char c, int pos) {
        return str.substring(0, pos) + c + str.substring(pos, str.length());
    }


    static boolean sameDigitNumber(int n) {
    	int[][] ret = {{1,2},{2,3},{1,2}};
		System.out.println(ret.length);
        if(n < 0 || n > 10000000) return false;
        int prev = n%10;
        boolean flag = false;
        while(n > 0){
            if(prev != (n%10)){
                flag = false;
                break;
            }
            flag= true;
            prev = n%10;
            n=(n/10);
        }
        return flag;
	}

	static int[][] constructSubmatrix(int[][] matrix, int[] rowsToDelete, int[] columnsToDelete) {
		int[][] ret = new int[matrix[0].length - rowsToDelete.length][matrix[0].length - columnsToDelete.length];
		int[][] retRows = new int[(matrix.length) - (rowsToDelete.length)][];
		
		int rowCounter = 0;
		for(int i=0; i< matrix.length; i++){
			for(int j =0; j<rowsToDelete.length; j++){
				if(rowsToDelete[j]!=i){
					retRows[rowCounter++] = matrix[i];
				}
			}
		}
		//delete cols from final rows
		int[][] retCols = new int[retRows.length][(retRows[0].length) - (columnsToDelete.length)];
		int colCounter = 0;
		for (int j = 0; j < columnsToDelete.length; j++) {
			for (int x = 0; x < retRows.length; x++) {
				colCounter = 0;
				for (int i = 0; i < retRows[x].length; i++) {
					if(columnsToDelete[j]!=i){
						retCols[x][colCounter++] = retRows[x][i];
					}
			}
		}
		}
		return retCols;
	}

}
