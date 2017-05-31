package me.satyen.test;

import java.io.IOException;

public class Exceptions {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("1");
		System.out.println("2");
		System.out.println("3");
		System.out.println("4");
		new MyThread().start();
//		Thread.sleep(1000);
		System.out.println("5");
//		if(true)throw new Error();
		System.out.println("6");
		System.out.println("7");
		System.out.println("8");
		System.out.println("9");
	}
	
	static class MyThread extends Thread{
		public void run(){
			System.out.println("inside run");
//			throw new Error();
		}
	}
}
