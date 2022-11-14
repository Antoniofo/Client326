package ihm;

import ctrl.Ctrl;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class IhmClientAdmin implements Initializable
{
    @javafx.fxml.FXML
    private ImageView screenRobot;
    @javafx.fxml.FXML
    private TextField txtHumidity;
    @javafx.fxml.FXML
    private TextField txtTemperature;
    @javafx.fxml.FXML
    private TextField txtfRobotStatus;
    @javafx.fxml.FXML
    private TextField txtfControllerStatus;
    @javafx.fxml.FXML
    private CheckBox ckbMicro;
    @javafx.fxml.FXML
    private CheckBox ckbSound;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}