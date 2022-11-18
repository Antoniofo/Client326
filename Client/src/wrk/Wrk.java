package wrk;

import ctrl.ItfCtrlWrk;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;

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
            wrkSocket = new WrkSocket(this);
            wrkUDP = new WrkUDP();
            wrkUDP.setRefWrk(this);
            wrkUDP.launchThread();
        } catch (SocketException e) {

        }
    }

    /**
     * @throws Throwable Throwable
     */
    public void finalize()
            throws Throwable {

    }


    public boolean connectToServer(String ip, int port) {
        boolean ok = wrkSocket.connect(ip, port);
        wrkSocket.start();
        return ok;
    }

    public boolean connectController() {
        return wrkController.connectController();
    }

    @Override
    public void receiveHumidity(double value) {
        refCtrl.handleHumidity(value);
        wrkSocket.sendHumidity(value);
    }

    @Override
    public void receiveRFID(String tag) {
        if (tag.equals("")) {
            wrkSocket.upgradeUser(refCtrl.getCurrentUser());
        }
    }

    @Override
    public void handleOrder(String message) {
        String[] t = message.split(",");
        switch (t[0]) {
            case "LOGINUSER":
                refCtrl.showClient(false);
                break;
            case "LOGINADMIN":
                refCtrl.upgradeUser();
                refCtrl.showClient(true);
                break;
            case "NOLOGIN":
                refCtrl.showLogin();
                break;
            case "Temperature":
                    refCtrl.handleTemperature(Double.valueOf(t[1]));
                break;
        }
    }

    @Override
    public void receiveButton(String button) {
        wrkSocket.sendCommand(button);
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

    @Override
    public void receiveAudio(byte[] data) {
        AudioData ad = new AudioData(data);
        AudioDataStream ads = new AudioDataStream(ad);
        AudioPlayer.player.start(ads);
    }

    public void setRefCtrl(ItfCtrlWrk refCtrl) {
        this.refCtrl = refCtrl;
    }

    public void checkLogin(String username, String password) {
        wrkSocket.sendLoginCheck(username, password);
    }

    public void disconnectController() {
        wrkController.disconnectController();
    }

    public void logOut() {
        wrkSocket.logOut();
    }

    public void connectRobot() {
        wrkSocket.sendRobotInit();
    }

    public void disconnectRobot() {
        wrkSocket.sendRobotDisconnect();
    }

    public void register(String text, String txtfPasswordText) {
        wrkSocket.sendUserRegistration(text, txtfPasswordText, false);
    }
}//end Wrk
