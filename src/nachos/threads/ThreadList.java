package nachos.threads;

import nachos.machine.Machine;

public class ThreadList {
	
	private long waketime;
	private KThread sleepthread;
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

}
