import com.phidget22.RFID;
import ctrl.Ctrl;
import ihm.Ihm;
import wrk.Wrk;

public class Main {
    public static void main(String[] args) {
        Ihm ihm = new Ihm();
        Ctrl ctrl = new Ctrl();
        Wrk wrk = new Wrk();
        
        ctrl.setRefIhm(ihm);
        ctrl.setRefWrk(wrk);
        ihm.setRefCtrl(ctrl);
        ctrl.start();
    }
}