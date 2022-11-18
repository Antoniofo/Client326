package ihm;

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
    public IhmError ihmError;
    public IhmLogin ihmLogin;
    public IhmRegister ihmRegister;
    public ItfCtrlIhm refCtrl;

    public Ihm() {
        isAdmin = false;
        ihmLogin = new IhmLogin();
        ihmClientAdmin = new IhmClientAdmin();
        ihmError = new IhmError();
        ihmClientUser = new IhmClientUser();
        ihmRegister = new IhmRegister();

        ihmClientAdmin.setLink(this);
        ihmLogin.setLink(this);
        ihmError.setLink(this);
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
    public void showError(String message) {
        ihmError.start(message);
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
        } else {
            ihmClientUser.start();
        }
    }

    public void logOut() {
        user = null;
        refCtrl.logOut();
    }

    public boolean connnectRobot() {
        return refCtrl.connectRobot();
    }

    public void disconnectRobot() {
        refCtrl.disconnectRobot();
    }

    public void register(String text, String txtfPasswordText) {
        refCtrl.register(text, txtfPasswordText);
    }
}//end Ihm