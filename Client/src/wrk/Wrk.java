package wrk;

import ctrl.ItfCtrlWrk;

/**
 * @author eggera
 * @version 1.0
 * @created 11-nov.-2022 11:04:09
 */
public class Wrk implements ItfWrkPhidget, ItfSocketWrk, ItfWrkController, ItfWrkUdp {

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
    public void receiveButton(Object button) {
    }

    @Override
    public void receiveFrame(Object frame) {
    }

    @Override
    public void receiveTemperature(double temperature) {
    }

    @Override
    public void receiveValidation(boolean ok) {
    }

}//end Wrk
