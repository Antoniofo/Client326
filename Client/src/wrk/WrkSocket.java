package wrk;

import java.io.*;
import java.net.*;

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

    public WrkSocket(ItfSocketWrk refWrk) {
        this.refWrk = refWrk;
    }

    /**
     *
     * @exception Throwable
     */
    public void finalize()
            throws Throwable {

    }

    private String readMessages() {
        String message = null;
        try {
            message = in.readLine();
        } catch (SocketTimeoutException ex) {
        } catch (IOException ex) {
        }
        return message;
    }

    @Override
    public void run() {
        runing = true;
        while(runing){
            if(socket != null && in != null){
                if(socket.isConnected()){
                    String message = readMessages();
                    if(message != null){
                        refWrk.handleOrder(message);
                    }
                }
            }
            try {
                sleep(10);
            } catch (InterruptedException e) {

            }
        }
    }

    private void writeMessage(String message) {
        if (out != null && message != null) {
            try {
                out.write(message +"\n");
                out.flush();
            } catch (IOException ex) {
            }
        }
    }



    public boolean connect(String IP, int port) {
        try{
            SocketAddress socketAddress  = new InetSocketAddress(InetAddress.getByName(IP), port);

            socket = new Socket();
            socket.connect(socketAddress, 0);
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writeMessage("Connected");
            return true;
        } catch (Exception e) {

        }
        return false;
    }
    
    public void sendUserRegistration(String username, String password, boolean isAdmin){
        writeMessage(String.format("register,%s,%s,%s",username,password,isAdmin));
    }
    
    public void sendLoginCheck(String username, String password){
        writeMessage(String.format("checklogin,%s,%s",username,password));
    }
    
    public void sendHumidity(double hum){
        writeMessage(String.format("humidity,%s",hum));
    }
            

    public void disconnect() {
        if(socket != null){
            try{
                socket.close();
                socket = null;
            } catch (IOException e) {

            }
        }
    }

    public void upgradeUser(String currentUser) {
        writeMessage(String.format("upgrade,%s",currentUser));
    }

    public void sendCommand(String button) {
        writeMessage(button);
    }

    public void logOut() {
        writeMessage("logout");
    }

    public void sendRobotInit() {
        writeMessage("ROBOTINIT");
    }

    public void sendRobotDisconnect() {
        writeMessage("ROBOTSHUT");
    }
}//end WrkSocket
