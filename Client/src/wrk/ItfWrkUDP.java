package wrk;


import org.openimaj.image.MBFImage;

/**
 * @author eggera
 * @version 1.0
 * @created 11-nov.-2022 11:04:09
 */
public interface ItfWrkUDP {
    public void receiveFrame(MBFImage frame);
}