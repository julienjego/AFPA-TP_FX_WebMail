package fr.afpajulien.fx_webmail;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Envoi de mails à un destinataire");
        stage.getIcons().add(new Image(App.class.getResource("/icon.png").toExternalForm()));
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(confirmQuit);
    }

    private final EventHandler<WindowEvent> confirmQuit = event -> {
        Alert.AlertType type = Alert.AlertType.CONFIRMATION;
        Alert alert = new Alert(type, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setHeaderText("Voulez-vous vraiment quitter l'application ?");

           Optional<ButtonType> result = alert.showAndWait();

           if (result.get() == ButtonType.OK) {
               System.exit(0);
           } else if (result.get() == ButtonType.CANCEL) {
               event.consume();
           }
    };

    public static void main(String[] args) {
        launch();

    }


}