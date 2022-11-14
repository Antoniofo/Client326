package ihm;

import ctrl.ItfCtrlIhm;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.image.WritableImage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author raposoesilvac
 * @version 1.0
 * @created 11-nov.-2022 11:04:05
 */
public class Ihm implements ItfIhmCtrl {

	private boolean isAdmin;
	public IhmClientAdmin ihmClientAdmin;
	public IhmClientUser ihmClientUser;
	public IhmError ihmError;
	public IhmLogin ihmLogin;
	public IhmRegister ihmRegister;
	public ItfCtrlIhm refCtrl;	

	public Ihm(){
		isAdmin = false;
		ihmLogin = new IhmLogin();
		ihmClientAdmin = new IhmClientAdmin();
		ihmError = new IhmError();
		ihmClientUser = new IhmClientUser();
		ihmRegister = new IhmRegister();
	}

    public void setRefCtrl(ItfCtrlIhm refCtrl) {
        this.refCtrl = refCtrl;
    }

    public void finalize() throws Throwable {

	}
	public void quit(){
		Platform.exit();
	}

	public void startIhm(){
		ihmLogin.start();
	}

	@Override
	public void showImage(WritableImage wr) {
		if(wr != null){
			if(isAdmin){
				ihmClientAdmin.showImage(wr);
			}else{
				ihmClientUser.showImage(wr);
			}
		}
	}

	public void showRegister(){
		ihmRegister.start();
	}

	public void showError(){
		ihmError.start();
	}
}//end Ihm