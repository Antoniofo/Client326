package ihm;

import ctrl.ItfCtrlIhm;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author raposoesilvac
 * @version 1.0
 * @created 11-nov.-2022 11:04:05
 */
public class Ihm implements ItfIhmCtrl {

	public IhmClientAdmin ihmClientAdmin;
	public IhmClientUser ihmClientUser;
	public IhmError ihmError;
	public IhmLogin ihmLogin;
	public IhmRegister ihmRegister;
	public ItfCtrlIhm refCtrl;	

	public Ihm(){
		ihmLogin = new IhmLogin();
	}

    public void setRefCtrl(ItfCtrlIhm refCtrl) {
        this.refCtrl = refCtrl;
    }

    public void finalize() throws Throwable {

	}
	public void quit(){

	}

	public void startIhm(){
		ihmLogin.start();
	}
}//end Ihm