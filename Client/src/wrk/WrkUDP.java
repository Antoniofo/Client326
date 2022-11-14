package wrk;

import org.openimaj.image.MBFImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


/**
 * @author eggera
 * @version 1.0
 * @created 11-nov.-2022 11:04:09
 */
public class WrkUDP extends Thread {

	private boolean runing;
	private byte[] buf = new byte[42069];
	private DatagramSocket socketUDP;
	public ItfWrkUDP refWrk;

	public void setRefWrk(ItfWrkUDP refWrk) {
		this.refWrk = refWrk;
	}

	public WrkUDP() throws SocketException {
		super("UDP Listener");
		socketUDP = new DatagramSocket(42069);

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
		runing = true;
		while (runing){
			try {
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				socketUDP.receive(dp);
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(dp.getData()));

				refWrk.receiveFrame(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}//end WrkUDP