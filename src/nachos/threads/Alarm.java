package nachos.threads;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;

import nachos.machine.*;

/**
 * Uses the hardware timer to provide preemption, and to allow threads to sleep
 * until a certain time.
 */
public class Alarm {
	private LinkedList<ThreadList> sleepthreads = new LinkedList<ThreadList>();
    /**
     * Allocate a new Alarm. Set the machine's timer interrupt handler to this
     * alarm's callback.
     *
     * <p><b>Note</b>: Nachos will not function correctly with more than one
     * alarm.
     */
    public Alarm() {
	Machine.timer().setInterruptHandler(new Runnable() {
		public void run() { timerInterrupt(); }
	    });
    }

    /**
     * The timer interrupt handler. This is called by the machine's timer
     * periodically (approximately every 500 clock ticks). Causes the current
     * thread to yield, forcing a context switch if there is another thread
     * that should be run.
     */
    public void timerInterrupt() {																	// needs go be changed
    Machine.interrupt().disable();
    int numThreads = sleepthreads.size(); 
    
    if (!(sleepthreads.isEmpty())){
    	
        boolean toRemove[] = new boolean[numThreads];
        Arrays.fill(toRemove, false);
        
    	for(int x = 0; x < numThreads; x++){
    	    System.out.println(Machine.timer().getTime());
    		if(sleepthreads.get(x).getWaketime() <= Machine.timer().getTime()){
    			sleepthreads.get(x).getSleepthread().ready();
    			toRemove[x] = true;
    		}
    	}
    	for(int x = numThreads-1; x >= 0; x--){
    		if(toRemove[x]){
    			sleepthreads.remove(x);
    		}
    	}
    }
   
    Machine.interrupt().enable();
	//KThread.currentThread().yield();
    }

    /**
     * Put the current thread to sleep for at least <i>x</i> ticks,
     * waking it up in the timer interrupt handler. The thread must be
     * woken up (placed in the scheduler ready set) during the first timer
     * interrupt where
     *
     * <p><blockquote>
     * (current time) >= (WaitUntil called time)+(x)
     * </blockquote>
     *
     * @param	x	the minimum number of clock ticks to wait.
     *
     * @see	nachos.machine.Timer#getTime()
     */
    public void waitUntil(long x) {  																	 // Needs to be completed
	// for now, cheat just to get something working (busy waiting is bad)
    long waketime = x + Machine.timer().getTime();
    Machine.interrupt().disable();
    sleepthreads.add(new ThreadList(waketime, KThread.currentThread()));
    System.out.println(sleepthreads.size());
    KThread.sleep();
    Machine.interrupt().enable();
    /*
    long wakeTime = Machine.timer().getTime() + x;
    while (wakeTime > Machine.timer().getTime()){
    	KThread.sleep();
    }
    */
    /*	
	long wakeTime = Machine.timer().getTime() + x;
	while (wakeTime > Machine.timer().getTime())
	    KThread.yield();
	    */
    }
    
}
