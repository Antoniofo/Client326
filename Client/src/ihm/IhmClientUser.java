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
    private Stage stage;
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
    private Ihm link;

    public void setLink(Ihm link) {
        this.link = link;
    }

    @javafx.fxml.FXML
    public void initialize() {
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
            txtfControllerStatus.setText("Controller Connection Successful");

        }else{
            txtfControllerStatus.setText("Controller Connection Failed");
        }
    }

    public void connectRobot(ActionEvent actionEvent) {
        link.connnectRobot();
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void start(){
        IhmClientUser myself = this;

        Callback<Class<?>, Object> controllerFactory = type -> {
            return myself;
        };

        SwingUtilities.invokeLater(() -> {
            new JFXPanel();
            Platform.runLater(() -> {
                try {
                    stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));

                    fxmlLoader.setControllerFactory(controllerFactory);

                    Parent root = (Parent) fxmlLoader.load();
                    Scene scene = new Scene(root);

                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.setTitle("Client User");
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
            link.showError("Can't show image");
        }
    }

    public void updateHumidity(double humidity) {
        txtHumidity.setText(humidity + "%");
    }

    public void showTemperature(double temperature) {
        txtTemperature.setText(temperature+"°C");
    }
}