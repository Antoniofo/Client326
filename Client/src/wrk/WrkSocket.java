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

    public void setRuning(boolean runing) {
        this.runing = runing;
    }

    private BufferedWriter out;
    private volatile boolean runing;
    private volatile Socket socket;
    public ItfSocketWrk refWrk;

    public WrkSocket(ItfSocketWrk refWrk) {
        this.refWrk = refWrk;
        socket = new Socket();
    }

    /**
     * @throws Throwable
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
        while (runing) {
            if (socket != null && in != null) {
                if (socket.isConnected() && !(socket.isClosed())) {
                    refWrk.statusServer(true);
                    String message = readMessages();
                    if (message != null) {
                        refWrk.handleOrder(message);
                    }
                }refWrk.statusServer(false);

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
                out.write(message + "\n");
                out.flush();
            } catch (IOException ex) {
            }
        }
    }


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
                    JfxPopup.displayError("Error","You have been disconnected from the server.", "Disconnected from the server.");
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

    public void sendUserRegistration(String username, String password, boolean isAdmin) {
        writeMessage(String.format("register,%s,%s,%s", username, password, isAdmin));
    }

    public void sendLoginCheck(String username, String password) {
        writeMessage(String.format("checklogin,%s,%s", username, password));
    }

    public void sendHumidity(double hum) {
        writeMessage(String.format("humidity,%s", hum));
    }


    public void disconnect() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {

            }
        }
    }

    public void upgradeUser(String currentUser) {
        writeMessage(String.format("upgrade,%s", currentUser));
    }

    public void sendCommand(String button) {
        writeMessage("CTRL," + button);
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
