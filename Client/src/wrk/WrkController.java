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

    private static XboxController xc;
    private ItfWrkController refWrk;
    private double leftMagnetude;
    private double rightMagnetude;

    /**
     * Constructor of WrkController
     * @param refWrk reference of the Wrk
     */
    public WrkController(ItfWrkController refWrk) {
        this.refWrk = refWrk;
    }

    /**
     * Disconnects the Xbox One controller
     */
    public void disconnectController() {
        xc.release();
    }

    /**
     * Connects the Xbox One controller
     * @return true if the connection is successful.
     */
    public boolean connectController() {
        xc = new XboxController();
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
                if (leftMagnetude > 0.1) {
                    if ((direction < 135) && (direction >= 45) && leftMagnetude > 0.7) {
                        refWrk.receiveButton("LJRIGHT");
                    } else if ((direction < 315) && (direction >= 225) && leftMagnetude > 0.7) {
                        refWrk.receiveButton("LJLEFT");
                    } else {
                        refWrk.receiveButton("STOP");
                    }
                }


            }

            @Override
            public void rightThumbDirection(double direction) {
                boolean pressed = false;
                if (((direction < 45) || (direction >= 315)) && rightMagnetude > 0.7) {
                    refWrk.receiveButton("UPHEAD");
                } else if (((direction < 225) && (direction >= 135)) && rightMagnetude > 0.7) {
                    refWrk.receiveButton("DOWNHEAD");
                } else {
                    refWrk.receiveButton("STOPHEAD");
                }
            }

            @Override
            public void rightTrigger(double value) {
                boolean pressed = (value > 0.0) ? true : false;
                if (pressed) {
                    refWrk.receiveButton("RTRIG");
                } else {
                    refWrk.receiveButton("STOP");
                }

            }

            @Override
            public void leftTrigger(double value) {
                boolean pressed = (value > 0.0) ? true : false;
                if (pressed) {
                    refWrk.receiveButton("LTRIG");
                } else {
                    refWrk.receiveButton("STOP");
                }
            }

            @Override
            public void buttonY(boolean pressed) {
                if (pressed) {
                    refWrk.receiveButton("DOCK");
                }

            }

            @Override
            public void buttonB(boolean pressed) {
                if (pressed) {
                    refWrk.receiveButton("UNDOCK");
                }
            }

            @Override
            public void buttonX(boolean pressed) {
                if (pressed) {
                    refWrk.receiveButton("STAND");
                }
            }

            @Override
            public void rightShoulder(boolean pressed) {
                if (pressed) {
                    refWrk.receiveButton("LED");
                }

            }

            @Override
            public void dpad(int direction, boolean pressed) {
                String button = "DPNN";
                if (pressed) {
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
                    refWrk.receiveButton(button);
                }
                waitForSec(500);
            }
        });
        return true;
    }

    /**
     * Makes the Thread sleeps for a certain period.
     * @param milli time in ms
     */
    private void waitForSec(int milli) {
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
            System.out.println("Err");
        }
    }
}//end WrkController