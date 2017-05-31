package me.satyen.test;

public class MyLock{
	boolean isLocked = false;
	public synchronized void lock() throws InterruptedException{
		while(isLocked){
			wait();
		}
		isLocked = true;
	}

	public synchronized void unlock(){
		isLocked = false;
		notify();
	}
}

class MyReentrantLock{
	boolean isLocked = false;
	Thread lock = null;
	int lockCnt = 0;

	public synchronized void lock() throws InterruptedException{
		while(isLocked && lock != Thread.currentThread()){
			wait();
		}
		isLocked = true;
		lock = Thread.currentThread();
		lockCnt++;
	}

	public synchronized void unlock(){
		if(lock == Thread.currentThread()){
			lockCnt--;
			if(lockCnt==0){
				isLocked = false;
				lock = null;
				notify();
			}
		}
	}
}