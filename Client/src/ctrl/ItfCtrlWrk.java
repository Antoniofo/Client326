package ctrl;


import javafx.scene.image.WritableImage;
import org.openimaj.image.MBFImage;

/**
 * @author eggera
 * @version 1.0
 * @created 11-nov.-2022 11:03:59
 */
public interface ItfCtrlWrk {
    public void handleTemperature(double temperature);
    
    public void handleHumidity(double humdity);
    
    public void handleFrame(WritableImage wr);
       
}