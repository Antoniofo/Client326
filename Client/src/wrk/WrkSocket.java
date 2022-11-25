package wrk;

import beans.JfxPopup;

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
    private volatile boolean runing;
    private volatile Socket socket;
    public ItfSocketWrk refWrk;

    /**
     *Constructor of WrkSocket
     * @param refWrk reference to the Wrk
     */
    public WrkSocket(ItfSocketWrk refWrk) {
        this.refWrk = refWrk;
        socket = new Socket();
    }

    /**
     * Change the status of the Thread.
     * @param runing Value of the new Thread status.
     */
    public void setRuning(boolean runing) {
        this.runing = runing;
    }

    /**
     * Read the message sent by the server.
     * @return the message sent or null if the connection is lost.
     */
    private String readMessages() {
        String message = null;
        try {
            message = in.readLine();
        } catch (SocketTimeoutException ex) {
        } catch (IOException ex) {
        }
        return message;
    }

    /**
     * Listen to server messages.
     */
    @Override
    public void run() {
        runing = true;
        while (runing) {
            if (socket != null && in != null) {
                if (socket.isConnected() && !(socket.isClosed())) {
                    refWrk.statusServer(true);
                    String message = readMessages();
                    if (message != null) {
                        refWrk.handleOrder(message);
                    }else{
                        try {
                            socket.close();
                        } catch (IOException e) {
                        }
                    }
                }else{
                    refWrk.statusServer(false);
                }

            }

            try {
                sleep(10);
            } catch (InterruptedException e) {

            }
        }
    }

    /**
     * Sends a message to the server.
     * @param message the message that has to be sent.
     */
    private void writeMessage(String message) {
        if (out != null && message != null) {
            try {
                out.write(message + "\n");
                out.flush();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Connects to the server.
     * @param IP The server's IP address
     * @param port The server's port
     * @return 0 if the connection is successful. 2 if the connection fails. 1 if the connection has already been established.
     */
    public int connect(String IP, int port) {
        if (!socket.isConnected()) {
            try {
                SocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(IP), port);

                socket.connect(socketAddress, 0);
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writeMessage("Connected");
                return 0;
            } catch (Exception e) {
                try {
                    socket.close();
                    socket = new Socket();
                } catch (IOException ex) {
                }
                return 2;
            }
        }
        try {
            socket.close();
            socket = new Socket();
        } catch (IOException e) {
        }
        return 1;
    }

    /**
     * Sends user registration request.
     * @param username username of the user
     * @param password password of the user
     * @param isAdmin privilege of the user
     */
    public void sendUserRegistration(String username, String password, boolean isAdmin) {
        writeMessage(String.format("register,%s,%s,%s", username, password, isAdmin));
    }

    /**
     * Sends login check request
     * @param username username of the user
     * @param password password of the user
     */
    public void sendLoginCheck(String username, String password) {
        writeMessage(String.format("checklogin,%s,%s", username, password));
    }

    /**
     * Sends the humidity to the server
     * @param hum Humidity value
     */
    public void sendHumidity(double hum) {
        writeMessage(String.format("humidity,%s", hum));
    }

    /**
     * Close the socket and disconnects to the server.
     */
    public void disconnect() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {

            }
        }
    }

    /**
     * upgrade current user to administrator.
     * @param currentUser the current user to upgrade
     */
    public void upgradeUser(String currentUser) {
        writeMessage(String.format("upgrade,%s", currentUser));
    }

    /**
     * Sends Xbox One controller keys to the server
     * @param button button to send
     */
    public void sendCommand(String button) {
        writeMessage("CTRL," + button);
    }

    /**
     * Send logout message to the server.
     */
    public void logOut() {
        writeMessage("logout");
    }

    /**
     * Send robot connection request.
     */
    public void sendRobotInit() {
        writeMessage("ROBOTINIT");
    }

    /**
     * Send robot disconnection request
     */
    public void sendRobotDisconnect() {
        writeMessage("ROBOTSHUT");
    }
}//end WrkSocket
