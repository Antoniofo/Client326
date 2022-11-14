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
}