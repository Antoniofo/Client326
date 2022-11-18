package ihm;

import ctrl.Ctrl;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IhmClientAdmin implements Initializable
{
    private final String fxml = "/ihm/ClientAdmin.fxml";
    private Stage stage;
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
    private CheckBox cbxMicrophone;
    @javafx.fxml.FXML
    private CheckBox cbxSound;
    private Ihm link;
    public void setLink(Ihm link) {
        this.link = link;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void logOut(ActionEvent actionEvent) {
        link.logOut();
        link.showLogin();
        stage.close();
    }

    public void disconnectController(ActionEvent actionEvent) {
        link.disconnectController();
        txtfControllerStatus.setText("Controller Disconnected");
    }

    public void disconnectRobot(ActionEvent actionEvent) {
        link.disconnectRobot();
        txtfRobotStatus.setText("Robot Disconnected");
    }

    public void connectController(ActionEvent actionEvent) {
        if(link.connnectConttroller()){
            txtfControllerStatus.setText("Controller Connection Successfull");
        }else{
            txtfControllerStatus.setText("Controller Connection Failed");
        }
    }

    public void connectRobot(ActionEvent actionEvent) {
        link.connnectRobot();
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void start(){
        IhmClientAdmin myself = this;

        Callback<Class<?>, Object> controllerFactory = type -> {
            return myself;
        };

        // Commence l'initialisation dans le thread de java
        SwingUtilities.invokeLater(() -> {
            new JFXPanel(); // Permet d'initializer le toolkit et l'environment JavaFX
            // Accède au dispatcher thread tant aimé par JavaFX
            Platform.runLater(() -> {
                try {
                    stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));

                    fxmlLoader.setControllerFactory(controllerFactory);

                    Parent root = (Parent) fxmlLoader.load();
                    Scene scene = new Scene(root);

                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.setTitle("Client Admin");
                    stage.show();
                    stage.setOnCloseRequest((e)-> {
                        link.logOut();
                        e.consume();
                        System.exit(0);
                    });
                } catch (IOException ex) {
                    System.out.println("Can't start the IHM because : " + ex);
                    Platform.exit();
                }
            });
        });
    }

    public void showImage(WritableImage wr) {
        if(wr != null && screenRobot != null){
            screenRobot.setVisible(true);
            screenRobot.setImage(wr);
        }else{
            link.showError("Can't show Image");
        }
    }

    public void updateHumidity(double humidity) {
        txtHumidity.setText(humidity + "%");
    }

    public void showTemperature(double temperature) {
        txtTemperature.setText(temperature+"°C");
    }
}