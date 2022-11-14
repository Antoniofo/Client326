package wrk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

/**
 * @author eggera
 * @version 1.0
 * @created 11-nov.-2022 11:04:09
 */
public class WrkSocket extends Thread {

    private BufferedReader in;
    private BufferedWriter out;
    private boolean runing;
    private Socket socket;
    public ItfSocketWrk refWrk;

    public WrkSocket() {

    }

    /**
     *
     * @exception Throwable
     */
    public void finalize()
            throws Throwable {

    }

    public void connect() {

    }
    
    public void sendUserRegistration(){
        
    }
    
    public void sendLoginCheck(){
        
    }
    
    public void sendHumidity(){
        
    }
            

    public void disconnect() {

    }

}//end WrkSocket
