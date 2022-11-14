package ihm;


import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
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

public class IhmClientUser
{
    private final String fxml = "/ihm/ClientUser.fxml";
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

    @javafx.fxml.FXML
    public void initialize() {
    }

    public void logOut(ActionEvent actionEvent) {
    }

    public void disconnectController(ActionEvent actionEvent) {
    }

    public void disconnectRobot(ActionEvent actionEvent) {
    }

    public void connectController(ActionEvent actionEvent) {
    }

    public void connectRobot(ActionEvent actionEvent) {
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void start(){
        IhmClientUser myself = this;

        Callback<Class<?>, Object> controllerFactory = type -> {
            return myself;
        };

        // Commence l'initialisation dans le thread de java
        SwingUtilities.invokeLater(() -> {
            new JFXPanel(); // Permet d'initializer le toolkit et l'environment JavaFX
            // Accède au dispatcher thread tant aimé par JavaFX
            Platform.runLater(() -> {
                try {
                    Stage stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));

                    fxmlLoader.setControllerFactory(controllerFactory);

                    Parent root = (Parent) fxmlLoader.load();
                    Scene scene = new Scene(root);

                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.setTitle("Client User");
                    stage.show();
                } catch (IOException ex) {
                    System.out.println("Can't start the IHM because : " + ex);
                    Platform.exit();
                }
            });
        });
    }

    public void showImage(WritableImage wr) {
        if(wr != null && screenRobot != null){
            screenRobot.setImage(wr);
        }else{
            IhmError err = new IhmError();
            err.start();
        }
    }
}