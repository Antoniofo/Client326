package beans;

/**
 * @author eggera
 * @version 1.0
 * @created 11-nov.-2022 13:52:27
 */
public class XboxButton {

    private boolean isPressed;

    private String name;

    public boolean isIsPressed() {
        return isPressed;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public XbxButton() {

    }

    public void finalize() throws Throwable {

    }
}//end XbxButton
