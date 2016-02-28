package nachos.threads;

import java.util.LinkedList;

import nachos.machine.*;

/**
 * A <i>communicator</i> allows threads to synchronously exchange 32-bit
 * messages. Multiple threads can be waiting to <i>speak</i>,
 * and multiple threads can be waiting to <i>listen</i>. But there should never
 * be a time when both a speaker and a listener are waiting, because the two
 * threads can be paired off at this point.
 */
public class Communicator {																			// Implement whole class
    /**
     * Allocate a new communicator.
     */
    public Communicator() {
    	coms = new Lock();
    	listener = new LinkedList();
    	speaker = new LinkedList();
    	hold = new LinkedList();
    }

    /**
     * Wait for a thread to listen through this communicator, and then transfer
     * <i>word</i> to the listener.
     *
     * <p>
     * Does not return until this thread is paired up with a listening thread.
     * Exactly one listener should receive <i>word</i>.
     *
     * @param	word	the integer to transfer.
     */
    public void speak(int word) {
    	System.out.println(this.toString());
    	System.out.println(KThread.currentThread().toString());
    	
    	Machine.interrupt().disable();
    	coms.acquire();
    	hold.add( (Integer)word);
    	if (!listener.isEmpty()){
    		coms.release();
    		listener.poll().ready();
    		Machine.interrupt().enable();
    	}
    	else{
    		speaker.addLast(KThread.currentThread());
    		coms.release();
    		speaker.getLast().sleep();
    		Machine.interrupt().enable();
    	}
    }

    /**
     * Wait for a thread to speak through this communicator, and then return
     * the <i>word</i> that thread passed to <tt>speak()</tt>.
     *
     * @return	the integer transferred.
     */    
    public int listen() {
    	coms.acquire();
    	Machine.interrupt().disable();
    	if(!speaker.isEmpty()){
    		coms.release();
    		speaker.poll().ready();
    		Machine.interrupt().enable();
    	}
    	else{
    		listener.addLast(KThread.currentThread());
    		coms.release();
    		listener.getLast().sleep();
    		Machine.interrupt().enable();
    	}
	return hold.poll();
    }
    
    private Lock coms;
	private LinkedList<KThread> listener;
	private LinkedList<KThread> speaker;
	private LinkedList<Integer> hold;
}
