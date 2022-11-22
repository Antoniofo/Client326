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
public class WrkUDP {

    private boolean runningImg;
    private boolean runningAudio;
    private Thread threadAudio;
    private Thread threadImg;
    private byte[] bufImg = new byte[65565];
    private DatagramSocket socketUDPVideo;

    private DatagramSocket socketUDPAudio;

    private byte[] bufAudio = new byte[65565];
    public ItfWrkUDP refWrk;

    public void setRefWrk(ItfWrkUDP refWrk) {
        this.refWrk = refWrk;
    }

    public WrkUDP() throws SocketException {
        socketUDPVideo = new DatagramSocket(42069);
        socketUDPAudio = new DatagramSocket(42070);
    }

    /**
     * @throws Throwable
     */
    public void finalize()
            throws Throwable {

    }

    public void launchThread() {
        threadImg = new Thread("UDP Image") {
            @Override
            public void run() {
                runningImg = true;
                while (runningImg) {
                    try {
                        DatagramPacket dp = new DatagramPacket(bufImg, bufImg.length);
                        socketUDPVideo.receive(dp);
                        BufferedImage image = ImageIO.read(new ByteArrayInputStream(dp.getData()));

                        refWrk.receiveFrame(image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        threadAudio = new Thread("UDP Audio") {
            @Override
            public void run() {
                runningAudio = true;
                while (runningAudio) {
                    try {
                        DatagramPacket dp = new DatagramPacket(bufAudio, bufAudio.length);
                        socketUDPAudio.receive(dp);

                        refWrk.receiveAudio(dp.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        threadImg.start();
        threadAudio.start();
    }

    public void stopThread() throws InterruptedException {
        threadImg.join();
        threadAudio.join();
    }
}//end WrkUDP