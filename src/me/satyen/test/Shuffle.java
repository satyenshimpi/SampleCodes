package me.satyen.test;

import java.util.Collections;
import java.util.Random;

public class Shuffle {
	public static void main(String[] args) {
		Card[] ca = Card.getCards();
		for (int i = 0; i < ca.length; i++) {
			System.out.println(ca[i]);			
		}
		System.out.println("\n--================================\n");
		shuffleCards(ca);
		for (int i = 0; i < ca.length; i++) {
			System.out.println(ca[i]);			
		}
	}
	
	public static void shuffleCards(Card[] arr){
//		Collections.shuffle(list);
		Random rnd = new Random();
		for(int i = arr.length; i > 1; i--){
			swap(arr, i-1, rnd.nextInt(i));   //random is exclusive of i. so gives till i-1
		}
	}
    private static void swap(Object[] arr, int i, int j) {
        Object tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}

class Card{
	char type;
	int val;
	public Card(char c, int x){
		type = c;
		val = x;
	}
	
	public static Card[] getCards(){
		Card[] cards = new Card[52];
		int cnt = 0;
		char[] carrs = {'H','S','D','C'};
		for(char c : carrs){
			for(int j=1; j<=13; j++){
				cards[cnt] = new Card(c, j);
				cnt++;
			}
		}
		return cards;
	}
	
	@Override
	public String toString(){
		return this.type+":"+this.val;
	}
}