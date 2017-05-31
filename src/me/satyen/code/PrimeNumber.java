package me.satyen.code;

public class PrimeNumber {
	public static void main(String[] args){
		int[] arr = getAllPrimes(15);
		System.out.println(isPrime(47));
	}
	
	static boolean isPrime(int n){
		for(int i=2; i*i <= n; i++){
			if(n % i == 0) return false;
		}
		return true;
	}
	
	static int[] getAllPrimes(int n){
		boolean[] bArr = new boolean[n+1];
		bArr = makeAllElementsAsTrue(bArr);
		
		for (int i = 2; i*i <= n; i++) {
			if(bArr[i]==true){
				//all muliples should be crpssed out
				for(int p = i*2 ; p<=n; p += i){
					bArr[p]= false;
				}
			}
		}
		
		// Print all prime numbers
	    for (int p=2; p<=n; p++)
	       if (bArr[p])
	          System.out.print(p + " ");
		
	    System.out.println();
		return null;
	}
	
	static boolean[] makeAllElementsAsTrue(boolean[] bArr){
		for (int i = 0; i < bArr.length; i++) {
			bArr[i] = true;
		}
		return bArr;
	}
}
