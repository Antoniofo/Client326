package wrk;

/**
 * @author eggera
 * @version 1.0
 * @created 11-nov.-2022 11:04:09
 */
public interface ItfWrkPhidget {

    public void receiveHumidity(double value);

    public void receiveRFID(String tag);
}
