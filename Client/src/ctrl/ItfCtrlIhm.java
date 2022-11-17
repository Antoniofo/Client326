package ctrl;


/**
 * @author eggera
 * @version 1.0
 * @created 11-nov.-2022 11:03:59
 */
public interface ItfCtrlIhm {

	public void logIn(String username, String password);

	public void logOut();

	public void register();

	public void tryAgain();

    boolean connectController();

	void disconnectController();

	boolean connectRobot();

	void disconnectRobot();
}