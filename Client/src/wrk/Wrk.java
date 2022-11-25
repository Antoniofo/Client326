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

    /**
     * Constructor of the Wrk
     */
    public Wrk() {
        try {

            wrkUDP = new WrkUDP(this);
            wrkUDP.start();

            wrkPhidget = new WrkPhidget(this);
            wrkController = new WrkController(this);

        } catch (SocketException e) {

        }
    }


    /**
     * Connects to the server using the sub-WrkSocket.
     *
     * @param ip   The server's IP address
     * @param port The server's port
     * @return 0 if the connection is successful. 2 if the connection fails. 1 if the connection has already been established.
     */
    public int connectToServer(String ip, int port) {
        return wrkSocket.connect(ip, port);

    }

    /**
     * Connects the controller using the sub-WrkController.
     *
     * @return true if the condition is successful, otherwise false.
     */
    public boolean connectController() {
        return wrkController.connectController();
    }

    /**
     * Transfer the humidity value to the Ctrl and sending it to the server using Sub-WrkSocket.
     *
     * @param value Humidity value
     */
    @Override
    public void receiveHumidity(double value) {
        refCtrl.handleHumidity(value);
        wrkSocket.sendHumidity(value);
    }

    /**
     * Send upgrade request to the server if tag is valid and if a user is connected.
     *
     * @param tag RFID tag
     */
    @Override
    public void receiveRFID(String tag) {
        if (tag.equals("5f00d10d9a")) {
            String u = refCtrl.getCurrentUser();
            if (u != null) {
                wrkSocket.upgradeUser(u);
            }
        }
    }

    /**
     * Handles the command sent by the server and treats it.
     *
     * @param message message sent by the server.
     */
    @Override
    public void handleOrder(String message) {
        String[] t = message.split(",");
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
                break;
            case "ALERT":
                refCtrl.alert();
                break;
        }
    }

    /**
     * Sends the server status to the Ctrl
     *
     * @param b true if the server is connected, false otherwise.
     */
    @Override
    public void statusServer(boolean b) {
        refCtrl.statusServer(b);
    }

    /**
     * Sends the pressed button to the sub-WrkSocket
     *
     * @param button the button pressed.
     */
    @Override
    public void receiveButton(String button) {
        wrkSocket.sendCommand(button);
    }

    /**
     * Transforms the buffered Image to a writtable image and sends it to the Ctrl.
     *
     * @param image The buffered image received.
     */
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

    /**
     * Setter of the Ctrl reference.
     *
     * @param refCtrl reference of the Ctrl
     */
    public void setRefCtrl(ItfCtrlWrk refCtrl) {
        this.refCtrl = refCtrl;
    }

    /**
     * Sends checkLogin request to server using sub-WrkSocket.
     *
     * @param username username of the user
     * @param password password of the user.
     */
    public void checkLogin(String username, String password) {
        wrkSocket.sendLoginCheck(username, password);
    }

    /**
     * Disconnects the Xbox One controller using Sub-WrkController.
     */
    public void disconnectController() {
        wrkController.disconnectController();
    }

    /**
     * Logs out the user using sub-WrkSocket.
     */
    public void logOut() {
        wrkSocket.logOut();
    }

    /**
     * Connects to the robot using sub-WrkSocket.
     */
    public void connectRobot() {
        wrkSocket.sendRobotInit();
    }

    /**
     * Disconnects from the robot using sub-WrkSocket.
     */
    public void disconnectRobot() {
        wrkSocket.sendRobotDisconnect();
    }

    /**
     * Sends user register request using sub-WrkSocket.
     *
     * @param text             Username of the user
     * @param txtfPasswordText Password of the user.
     */
    public void register(String text, String txtfPasswordText) {
        wrkSocket.sendUserRegistration(text, txtfPasswordText, false);
    }

    /**
     * Destroy the sub-WrkUDP and sub-WrkSocket threads and calls GC.
     */
    public void killThread() {
        try {
            wrkSocket.setRuning(false);
            wrkSocket.join();
            wrkUDP.setRunningImg(false);
            wrkUDP.disconnect();
            wrkUDP.join();

            System.gc();
        } catch (InterruptedException e) {
            System.exit(2);
        }

    }

    /**
     * Start the thread of the sub-WrkSocket
     */
    public void startServer() {
        wrkSocket = new WrkSocket(this);
        wrkSocket.start();
    }
}//end Wrk
