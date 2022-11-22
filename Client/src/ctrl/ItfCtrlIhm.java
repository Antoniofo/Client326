package ctrl;


/**
 * @author eggera
 * @version 1.0
 * @created 11-nov.-2022 11:03:59
 */
public interface ItfCtrlIhm {

	public void logIn(String username, String password);

	public void logOut();

	public void register(String text, String txtfPasswordText);

	public void tryAgain();

    boolean connectController();

	void disconnectController();

	void connectRobot();

	void disconnectRobot();

	void killThread();

    boolean connectServer(String s, int i);
}