package wrk;

/**
 * @author eggera
 * @version 1.0
 * @created 11-nov.-2022 11:04:09
 */
public interface ItfSocketWrk {

    public void receiveTemperature(double temperature);

    public void receiveValidation(boolean ok);

}
