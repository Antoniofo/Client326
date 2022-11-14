package wrk;

import java.net.DatagramSocket;


/**
 * @author eggera
 * @version 1.0
 * @created 11-nov.-2022 11:04:09
 */
public class WrkUDP extends Thread {

	private boolean runing;
	private DatagramSocket socketUDP;
	public ItfWrkUDP refWrk;

	public WrkUDP(){

	}

	/**
	 * 
	 * @exception Throwable
	 */
	public void finalize()
	  throws Throwable{

	}

	@Override
	public void run(){

	}

	/**
	 * 
	 * @param runing
	 */
	public void setRuning(boolean runing){

	}
}//end WrkUDP