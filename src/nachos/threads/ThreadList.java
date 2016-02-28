package nachos.threads;

import nachos.machine.Machine;

public class ThreadList {
	
	private long waketime;
	private KThread sleepthread;
	private int status; // 0 = sleeo/blocked  1= waiting for lock
	/**
	 * Used in the Alarm class and contains wake time and 
	 * the Kthread that is to be woken up
	 */
	public ThreadList(long time, KThread threadaSleep){
		setWaketime(time);
		setSleepthread(threadaSleep);
		if(Machine.interrupt().disabled()){
			System.out.println("already done");
		}
	}
	/**
	 * Used in Condition2 class and contains status of 
	 * thread and a list of KThreads
	 * @param stat
	 * @param threadaSleep
	 */
	public ThreadList(int stat, KThread threadaSleep){
		setStatus(stat);
		setSleepthread(threadaSleep);
		if(Machine.interrupt().disabled()){
			System.out.println("already done");
		}
	}
	public long getWaketime() {
		return waketime;
	}

	public void setWaketime(long waketime) {
		this.waketime = waketime;
	}

	public KThread getSleepthread() {
		return sleepthread;
	}

	public void setSleepthread(KThread sleepthread) {
		this.sleepthread = sleepthread;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
