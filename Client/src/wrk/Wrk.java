package wrk;

import ctrl.ItfCtrlWrk;
import javafx.application.Platform;
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
            wrkSocket = new WrkSocket(this);
            wrkSocket.start();
            wrkUDP = new WrkUDP();
            wrkUDP.setRefWrk(this);
            wrkUDP.launchThread();

            wrkPhidget = new WrkPhidget(this);
            wrkController = new WrkController(this);

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
        System.out.println("TAG RECEIVED: " + tag);
        if (tag.equals("5f00d10d9a")) {
            String u = refCtrl.getCurrentUser();
            if (u != null) {
                wrkSocket.upgradeUser(u);
            }
        }
    }

    @Override
    public void handleOrder(String message) {
        String[] t = message.split(",");
        System.out.println(t);
        switch (t[0]) {
            case "LOGINUSER":
                refCtrl.showClient(false);
                refCtrl.setCurrentUser(t[1]);
                break;
            case "LOGINADMIN":
                refCtrl.showClient(true);
                refCtrl.setCurrentUser(t[1]);
                break;
            case "NOLOGIN":
                refCtrl.showLogin();
                break;
            case "Temperature":
                refCtrl.handleTemperature(Double.valueOf(t[1]));
                break;
            case "ADMINMODE":
                refCtrl.upgradeUser();
        }
    }

    @Override
    public void receiveButton(String button) {
        wrkSocket.sendCommand(button);
    }

    @Override
    public void receiveFrame(BufferedImage image) {
        if (image != null) {
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

    public void killThread() {
        try {
            wrkSocket.join();
            wrkUDP.stopThread();
            System.gc();
        } catch (InterruptedException e) {
            System.exit(2);
        }

    }
}//end Wrk
