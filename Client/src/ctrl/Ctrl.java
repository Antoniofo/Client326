package ctrl;

import ihm.ItfIhmCtrl;
import wrk.ItfWrkPhidget;
import wrk.Wrk;

/**
 * @author eggera
 * @version 1.0
 * @created 11-nov.-2022 11:03:59
 */
public class Ctrl implements ItfCtrlWrk, ItfCtrlIhm {

    public ItfIhmCtrl refIhm;
    public Wrk refWrk;

    public Ctrl() {

    }

    /**
     *
     * @exception Throwable
     */
    public void finalize()
            throws Throwable {

    }
    
    public void start(){
        
    }

    @Override
    public void logIn() {

    }

    @Override
    public void logOut() {

    }

    @Override
    public void register() {

    }

    @Override
    public void tryAgain() {

    }

    @Override
    public void handleTemperature(double temperature) {
    }

    @Override
    public void handleHumidity(double humdity) {
    }

    @Override
    public void handleFrame(MBFImage frame) {
    }
}//end Ctrl
