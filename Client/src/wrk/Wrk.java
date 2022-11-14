package wrk;

import beans.XboxButton;
import ctrl.ItfCtrlWrk;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import org.openimaj.image.MBFImage;

import java.awt.image.BufferedImage;
import java.net.SocketException;

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
        try {
            wrkController = new WrkController();
            wrkPhidget = new WrkPhidget();
            wrkSocket = new WrkSocket();
            wrkUDP = new WrkUDP();
            wrkUDP.setRefWrk(this);
            wrkUDP.start();
        } catch (SocketException e) {

        }


    }

    /**
     *
     * @exception Throwable Throwable
     */
    public void finalize()
            throws Throwable {

    }
    


    public void connectToServer() {
        wrkSocket.start();
    }

    public void connectController() {
        wrkController.connectController();
    }

    @Override
    public void receiveHumidity(double value) {
    }

    @Override
    public void receiveRFID(String tag) {
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
    public void receiveFrame(BufferedImage image) {
        WritableImage wr;
        wr = new WritableImage(image.getWidth(), image.getHeight());
        PixelWriter pw = wr.getPixelWriter();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                pw.setArgb(x, y, image.getRGB(x, y));
            }
        }
        refCtrl.handleFrame(wr);
    }

    public void setRefCtrl(ItfCtrlWrk refCtrl) {
        this.refCtrl = refCtrl;
    }
}//end Wrk
