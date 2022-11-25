package wrk;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;


/**
 * @author eggera
 * @version 1.0
 * @created 11-nov.-2022 11:04:09
 */
public class WrkUDP extends Thread {

    private volatile boolean runningImg;

    private byte[] bufImg = new byte[65565];
    private DatagramSocket uDPVideo;
    public ItfWrkUDP refWrk;

    /**
     * Constructor of WrkUdp.
     * @param refWrk reference to the Wrk
     * @throws SocketException
     */
    public WrkUDP(ItfWrkUDP refWrk) throws SocketException {
        this.refWrk = refWrk;
        uDPVideo = new DatagramSocket(42069);
    }


    /**
     * Will receive a UDP packet and it will send it to the Wrk to show the image.
     */
    @Override
    public void run() {
        runningImg = true;
        while (runningImg) {
            try {
                DatagramPacket dp = new DatagramPacket(bufImg, bufImg.length);
                uDPVideo.receive(dp);
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(dp.getData()));

                refWrk.receiveFrame(image);
            } catch (IOException e) {
            }
        }
    }

    /**
     * Permit to pause the thread.
     * @param runningImg Value of running for the thread.
     */
    public void setRunningImg(boolean runningImg) {
        this.runningImg = runningImg;
    }

    /**
     * Disconnect the datagram socket.
     */
    public void disconnect() {
        uDPVideo.close();
    }
}//end WrkUDP