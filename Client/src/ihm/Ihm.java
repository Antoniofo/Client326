package ihm;

import beans.JfxPopup;
import ctrl.ItfCtrlIhm;
import javafx.application.Platform;
import javafx.scene.image.WritableImage;

/**
 * @author raposoesilvac
 * @version 1.0
 * @created 11-nov.-2022 11:04:05
 */
public class Ihm implements ItfIhmCtrl {

    private String user;
    private boolean isAdmin;
    public IhmClientAdmin ihmClientAdmin;
    public IhmClientUser ihmClientUser;

    public IhmLogin ihmLogin;
    public IhmRegister ihmRegister;
    public ItfCtrlIhm refCtrl;

    public Ihm() {
        isAdmin = false;
        ihmLogin = new IhmLogin();
        ihmClientAdmin = new IhmClientAdmin();

        ihmClientUser = new IhmClientUser();
        ihmRegister = new IhmRegister();

        ihmClientAdmin.setLink(this);
        ihmLogin.setLink(this);

        ihmRegister.setLink(this);
        ihmClientUser.setLink(this);

    }

    public void setRefCtrl(ItfCtrlIhm refCtrl) {
        this.refCtrl = refCtrl;
    }

    public void finalize() throws Throwable {

    }

    public void quit() {
        Platform.exit();
    }

    public void startIhm() {
        ihmClientAdmin.init();
        ihmClientUser.init();
        ihmLogin.start();
    }

    @Override
    public void showImage(WritableImage wr) {
        if (wr != null) {
            if (isAdmin) {
                ihmClientAdmin.showImage(wr);
            } else {
                ihmClientUser.showImage(wr);
            }
        }
    }

    @Override
    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public void adminMode() {
        Platform.runLater(() -> {
            ihmClientUser.quit();
            ihmClientAdmin.start();
        });

    }

    @Override
    public void statusServer(boolean b) {
        if (!b && (ihmClientAdmin.isShown() || ihmClientUser.isShown())) {
            Platform.runLater(() -> {
                ihmLogin.start();
                ihmClientAdmin.quit();
                ihmClientUser.quit();
            });
        }
    }

    @Override
    public void alert() {
        Platform.runLater(() -> {
            JfxPopup.displayError("Alert", "Weather Condition", "The weather condition are not convenable");
        });

    }


    @Override
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean connnectConttroller() {
        return refCtrl.connectController();
    }

    @Override
    public void disconnectController() {
        refCtrl.disconnectController();
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public void logIn(String username, String password) {
        refCtrl.logIn(username, password);
    }


    @Override
    public void showLogin() {
        ihmLogin.start();
    }

    public void showRegister() {
        ihmRegister.start();
    }

    @Override
    public void showHumidity(double humidity) {
        if (isAdmin) {
            ihmClientAdmin.updateHumidity(humidity);
        } else {
            ihmClientUser.updateHumidity(humidity);
        }
    }

    @Override
    public void showClient(boolean b) {
        if (b) {
            ihmClientAdmin.start();
            isAdmin = true;
        } else {
            ihmClientUser.start();
            isAdmin = false;
        }
        ihmLogin.quit();
    }

    @Override
    public void showTemperature(double temperature) {
        if (isAdmin) {
            ihmClientAdmin.showTemperature(temperature);
        } else {
            ihmClientUser.showTemperature(temperature);
        }
    }

    public void logOut() {
        user = null;
        refCtrl.logOut();
    }

    public void connnectRobot() {
        refCtrl.connectRobot();
    }

    public void disconnectRobot() {
        refCtrl.disconnectRobot();
    }

    public void register(String text, String txtfPasswordText) {
        refCtrl.register(text, txtfPasswordText);
    }

    public void killThread() {
        refCtrl.killThread();
    }

    public int connectToServer(String s, int i) {
        return refCtrl.connectServer(s, i);
    }

}//end Ihm