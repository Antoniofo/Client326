package wrk;


import ch.aplu.xboxcontroller.XboxController;
import ch.aplu.xboxcontroller.XboxControllerAdapter;

import javax.swing.*;

/**
 * @author eggera
 * @version 1.0
 * @created 11-nov.-2022 11:04:09
 */
public class WrkController {

    private XboxController xc = new XboxController();
    private ItfWrkController refWrk;
    private double leftMagnetude;
    private double rightMagnetude;

    public WrkController() {

    }

    /**
     * @throws Throwable
     */
    public void finalize()
            throws Throwable {

    }

    public void disconnectController(){
        xc.release();
    }

    public boolean connectController() {
        if (!xc.isConnected()) {
            xc.release();
            return false;
        }
        xc.addXboxControllerListener(new XboxControllerAdapter() {
            @Override
            public void leftThumbMagnitude(double magnitude) {
                leftMagnetude = magnitude;
            }

            @Override
            public void rightThumbMagnitude(double magnitude) {
                rightMagnetude = magnitude;
            }

            @Override
            public void leftThumbDirection(double direction) {
                boolean pressed = false;

                if ((direction < 45) || (direction >= 315)) {
                    pressed = (leftMagnetude > 0.7) ? true : false;
                    refWrk.receiveButton(String.format("LJUP,%s", pressed));
                } else if ((direction < 135) && (direction >= 45)) {
                    pressed = (leftMagnetude > 0.7) ? true : false;
                    refWrk.receiveButton(String.format("LJRIGHT,%s", pressed));
                } else if ((direction < 225) && (direction >= 135)) {
                    pressed = (leftMagnetude > 0.7) ? true : false;
                    refWrk.receiveButton(String.format("LJDOWN,%s", pressed));
                } else if ((direction < 315) && (direction >= 225)) {
                    pressed = (leftMagnetude > 0.7) ? true : false;
                    refWrk.receiveButton(String.format("LJLEFT,%s", pressed));
                }
            }

            @Override
            public void rightThumbDirection(double direction) {
                boolean pressed = false;
                if ((direction < 45) || (direction >= 315)) {
                    pressed = (rightMagnetude > 0.7) ? true : false;
                    refWrk.receiveButton(String.format("RJUP,%s", pressed));
                } else if ((direction < 225) && (direction >= 135)) {
                    pressed = (rightMagnetude > 0.7) ? true : false;
                    refWrk.receiveButton(String.format("RJDOWN,%s", pressed));
                }
            }

            @Override
            public void buttonY(boolean pressed) {
                refWrk.receiveButton(String.format("DOCK,%s", pressed));
            }

            @Override
            public void buttonB(boolean pressed) {
                refWrk.receiveButton(String.format("UNDOCK,%s", pressed));
            }

            @Override
            public void buttonX(boolean pressed) {
                refWrk.receiveButton(String.format("STAND",pressed));
            }

            @Override
            public void rightShoulder(boolean pressed) {
                refWrk.receiveButton(String.format("LED", pressed));
            }

            @Override
            public void dpad(int direction, boolean pressed) {
                String button = "DPNN";
                switch (direction) {
                    case 0:
                        button = "DPUP";
                        break;
                    case 2:
                        button = "DPRIGHT";
                        break;
                    case 4:
                        button = "DPDOWN";
                        break;
                    case 6:
                        button = "DPLEFT";
                        break;

                    }
                refWrk.receiveButton(String.format(button+"%s", pressed));
            }
        });
        return true;
    }
}//end WrkController