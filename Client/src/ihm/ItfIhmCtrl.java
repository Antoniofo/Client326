package ihm;


import javafx.scene.image.WritableImage;

/**
 * @author eggera
 * @version 1.0
 * @created 11-nov.-2022 11:04:05
 */
public interface ItfIhmCtrl {

	public void quit();

	public void startIhm();

	public void showImage(WritableImage wr);

	void showClient(String username, String password);

	void showLogin();

	String getUser();

	void setAdmin(boolean b);

    boolean connnectConttroller();

	void disconnectController();

	void showError(String message);

    void showHumidity(double humidity);
}