package me.satyen.test;

public class Synchronization {
	public static void main (String[] args){
		;
	}
	
	class SimpleLock {
		private boolean isLocked = false;
		public synchronized void lock() throws InterruptedException{
			while(isLocked){
				wait();
			}
			isLocked = true;
		}
		
		public synchronized void unlock(){
			isLocked= false;
			notify();
		}
	}
	
	class RentrantLock{
		private boolean isLocked = false;
		private int lockCnt = 0;
		private Thread lockedByThread = null;
		
		public synchronized void lock() throws InterruptedException{
			while(isLocked && Thread.currentThread() != lockedByThread){
				wait();
			}
			lockedByThread = Thread.currentThread();
			isLocked = true;
			lockCnt++;
		}
		
		public synchronized void unlock(){
			if(Thread.currentThread() == lockedByThread){
				lockCnt--;
				if(lockCnt == 0){
					isLocked= false;
					lockedByThread = null;
					notify();
				}
			}
		}
	}
}
