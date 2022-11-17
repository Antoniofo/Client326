package wrk;


import org.openimaj.image.MBFImage;

import java.awt.image.BufferedImage;

/**
 * @author eggera
 * @version 1.0
 * @created 11-nov.-2022 11:04:09
 */
public interface ItfWrkUDP {
    public void receiveFrame(BufferedImage frame);

    void receiveAudio(byte[] data);
}