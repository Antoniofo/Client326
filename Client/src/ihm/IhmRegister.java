package ihm;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import java.io.IOException;

public class IhmRegister
{
    private Stage stage;
    private final String fxml = "/ihm/Register.fxml";
    @javafx.fxml.FXML
    private TextField txtfUsername;
    @javafx.fxml.FXML
    private TextField txtfPassword;
    @javafx.fxml.FXML
    private TextField txtfConfirmPassword;
    private Ihm link;

    public void setLink(Ihm link) {
        this.link = link;
    }

    @javafx.fxml.FXML
    public void initialize() {
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void start(){
        IhmRegister myself = this;

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
                    stage.setTitle("Register");
                    stage.show();
                } catch (IOException ex) {
                    System.out.println("Can't start the IHM because : " + ex);
                    Platform.exit();
                }
            });
        });
    }

    public void quit(){
        stage.close();
    }
    public void registerAccount(ActionEvent actionEvent) {
        if(txtfConfirmPassword.getText().equals(txtfPassword.getText())){
            link.register(txtfUsername.getText(),txtfPassword.getText());
            quit();
        }

    }
}