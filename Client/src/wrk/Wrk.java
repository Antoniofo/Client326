package wrk;

import beans.XboxButton;
import ctrl.ItfCtrlWrk;
import org.openimaj.image.MBFImage;

/**
 * @author eggera
 * @version 1.0
 * @created 11-nov.-2022 11:04:09
 */
public class Wrk implements ItfWrkPhidget, ItfSocketWrk, ItfWrkController, ItfWrkUDP {

    public WrkPhidget wrkPhidget;
    public WrkUDP wrkUDP;
    public ItfCtrlWrk refCtrl;
    public WrkController wrkController;
    public WrkSocket wrkSocket;

    public Wrk() {

    }

    /**
     *
     * @exception Throwable Throwable
     */
    public void finalize()
            throws Throwable {

    }
    
    public boolean initializeWrk(){
        return true;
    }

    public void connectToServer() {

    }

    public void connectController() {

    }

    public void initializePhidget() {

    }

    @Override
    public void receiveHumidity(double value) {
    }

    @Override
    public void receiveRFID() {
    }

    @Override
    public void receiveTemperature(double temperature) {
    }

    @Override
    public void receiveValidation(boolean ok) {
    }

    @Override
    public void receiveButton(XboxButton button) {

    }

    @Override
    public void receiveFrame(MBFImage frame) {

    }
}//end Wrk
