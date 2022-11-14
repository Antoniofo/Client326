package ihm;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.embed.swing.JFXPanel;
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
    @javafx.fxml.FXML
    private TextField txtfLogin;
    @javafx.fxml.FXML
    private TextField txtf;

    private final String fxml = "/ihm/Login.fxml";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void start(){
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
                    Stage stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));

                    fxmlLoader.setControllerFactory(controllerFactory);

                    Parent root = (Parent) fxmlLoader.load();
                    Scene scene = new Scene(root);

                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.setTitle("Login");
                    stage.show();
                } catch (IOException ex) {
                    System.out.println("Can't start the IHM because : " + ex);
                    Platform.exit();
                }
            });
        });
    }

    public void login(ActionEvent actionEvent) {
        IhmClientUser rr = new IhmClientUser();
        rr.start();
    }

    public void register(ActionEvent actionEvent) {
        IhmRegister reg = new IhmRegister();
        reg.start();
    }
}

