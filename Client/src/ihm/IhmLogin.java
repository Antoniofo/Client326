package ihm;

import beans.JfxPopup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Button;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.SwingUtilities;


public class IhmLogin implements Initializable {

    @FXML
    public Button btnLogin;
    @FXML
    public Button btnRegister;
    private Stage stage;
    @javafx.fxml.FXML
    private TextField txtfLogin;
    @javafx.fxml.FXML
    private TextField txtf;
    @FXML
    private TextField srvIP;
    private Ihm link;

    public void setLink(Ihm link) {
        this.link = link;
    }

    private final String fxml = "/ihm/Login.fxml";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void start() {
        IhmLogin myself = this;

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
                    stage.setTitle("Login");
                    btnRegister.setDisable(true);
                    btnLogin.setDisable(true);
                    stage.show();
                    stage.setOnCloseRequest((e) -> {
                        link.killThread();
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

    public void login(ActionEvent actionEvent) {
        String username = txtfLogin.getText();
        String pwd = txtf.getText();
        if (username != null && pwd != null) {
            if (!(username.equals("") || pwd.equals(""))) {
                link.logIn(username, pwd);
            }
        }

    }

    public void register(ActionEvent actionEvent) {
        link.showRegister();
    }

    public void quit() {
        Platform.runLater(()->{
            stage.close();
        });

    }

    public void connectToServer(ActionEvent actionEvent) {
        int err = link.connectToServer(srvIP.getText(), 7777);
        if(err == 0){
            btnRegister.setDisable(false);
            btnLogin.setDisable(false);
        }else if (err == 1){
            JfxPopup.displayError("Error", "Connection", "The Server you are trying to connect is already connected");
        }else{
            JfxPopup.displayError("Error", "Connection", "Couldn't contact server");
        }

    }
}

