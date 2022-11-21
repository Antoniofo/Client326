package ctrl;

import ihm.ItfIhmCtrl;
import javafx.scene.image.WritableImage;
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
     * @throws Throwable
     */
    public void finalize()
            throws Throwable {

    }

    public void start() {
        refIhm.startIhm();
        boolean k = refWrk.connectToServer("127.0.0.1", 7777);
        if (!k) {
            refIhm.showError("Server Not Connected");
        }
    }

    @Override
    public void logIn(String username, String password) {
        refWrk.checkLogin(username, password);
    }

    @Override
    public void logOut() {
        refWrk.logOut();
    }

    @Override
    public void register(String text, String txtfPasswordText) {
        refWrk.register(text, txtfPasswordText);
    }

    @Override
    public void tryAgain() {

    }

    @Override
    public boolean connectController() {
        return refWrk.connectController();
    }

    @Override
    public void disconnectController() {
        refWrk.disconnectController();
    }

    @Override
    public void connectRobot() {
        refWrk.connectRobot();
    }

    @Override
    public void disconnectRobot() {
        refWrk.disconnectRobot();
    }

    @Override
    public void killThread() {
        refWrk.killThread();
    }

    @Override
    public void handleTemperature(double temperature) {
        refIhm.showTemperature(temperature);
    }

    @Override
    public void handleHumidity(double humdity) {
        refIhm.showHumidity(humdity);
    }

    @Override
    public void handleFrame(WritableImage wr) {
        refIhm.showImage(wr);
    }

    @Override
    public String getCurrentUser() {
        return refIhm.getUser();
    }

    @Override
    public void upgradeUser() {
        System.out.println("Upgrade");
        refIhm.setAdmin(true);
        refIhm.adminMode();
    }

    @Override
    public void showClient(boolean b) {
        refIhm.showClient(b);
    }

    @Override
    public void showLogin() {
        refIhm.showLogin();
    }

    @Override
    public void setCurrentUser(String s) {
        refIhm.setUser(s);
    }

    public void setRefIhm(ItfIhmCtrl refIhm) {
        this.refIhm = refIhm;
    }

    public void setRefWrk(Wrk refWrk) {
        this.refWrk = refWrk;
    }
}//end Ctrl
