package fr.afpajulien.fx_webmail;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private BorderPane mainPane;
    @FXML
    private Button btnMsgNew;
    @FXML
    private Button btnMsgOpen;
    @FXML
    private Button btnMsgSend;
    @FXML
    private Button btnMsgSendFooter;
    @FXML
    private ComboBox<String> cbxDest;

    @FXML
    private Label lblMsgError;

    @FXML
    private Label lblMsgSend;
    @FXML
    private MenuBar mainMenu;
    @FXML
    private MenuItem itmSend;
    @FXML
    private TextField txtSubject;
    @FXML
    private TextArea txtMsg;

    private final List<String> records = new ArrayList<>();


    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnMsgSend.setDisable(true);
        btnMsgSendFooter.setDisable(true);
        itmSend.setDisable(true);

        btnMsgNew.setGraphic(new ImageView(new Image(App.class.getResource("/new.png").toExternalForm(), 24, 24, true, true)));
        btnMsgNew.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        btnMsgOpen.setGraphic(new ImageView(new Image(App.class.getResource("/open.png").toExternalForm(), 24, 24, true, true)));
        btnMsgOpen.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        btnMsgSend.setGraphic(new ImageView(new Image(App.class.getResource("/send.png").toExternalForm(), 24, 24, true, true)));
        btnMsgSend.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        init();
    }

    public void init() {
        initTxtSubject();
        initTxtMsg();
        initcbxDest();
    }

    private void initTxtSubject() {
        txtSubject.textProperty().addListener(e -> checkIfEmpty()); // Event fire every time text is changed.
    }

    private void initTxtMsg() {
        txtMsg.textProperty().addListener(e -> checkIfEmpty());
    }

    private void initcbxDest() {
        cbxDest.valueProperty().addListener(e -> checkIfEmpty());
    }

    public void addMailAddresses() {
        String path = "src/main/resources/mails.csv";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            records.clear();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                records.add(values[0] + " : " + values[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        cbxDest.getItems().clear();
        cbxDest.getItems().addAll(records);
    }

    public void createNewMsg() {
        txtMsg.clear();
        txtSubject.clear();
    }

    public void openMsg() {
        FileChooser fileChooser = new FileChooser();

        File defaultDir = new File("src/main/resources/msg/");
        fileChooser.setInitialDirectory(defaultDir);
        Stage direc = (Stage) mainPane.getScene().getWindow();
        fileChooser.showOpenDialog(direc);
    }

    public void confirmQuit() {
        Alert.AlertType type = Alert.AlertType.CONFIRMATION;
        Alert alert = new Alert(type, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.getDialogPane().setHeaderText("Voulez-vous vraiment quitter l'application ?");

        if (txtMsg.getText().isEmpty()) {
            System.exit(0);
        } else {
            ButtonType result = alert.showAndWait().orElseThrow();
            if (result == ButtonType.OK) {
                System.exit(0);
            } else if (result == ButtonType.CANCEL) {
                alert.close();
            }
        }
    }

    public void confirmNewMsg() {
        Alert.AlertType type = Alert.AlertType.CONFIRMATION;
        Alert alert = new Alert(type, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.getDialogPane().setHeaderText("Voulez-vous vraiment créer un nouveau message ?");

        if (!txtMsg.getText().isEmpty()) {
            ButtonType result = alert.showAndWait().orElseThrow();
            if (result == ButtonType.OK) {
                createNewMsg();
            } else if (result == ButtonType.CANCEL) {
                alert.close();
            }
        }
    }

    public void checkIfEmpty() {

        if (txtMsg.getText().isEmpty() || txtSubject.getText().isEmpty() || cbxDest.getSelectionModel().isEmpty()) {
            btnMsgSendFooter.setDisable(true);
            btnMsgSend.setDisable(true);
            itmSend.setDisable(true);
        } else {
            btnMsgSendFooter.setDisable(false);
            btnMsgSend.setDisable(false);
            itmSend.setDisable(false);
        }
    }

    public void sendMsg() throws IOException {

        List<String> lines = Arrays.asList("From: " + cbxDest.getSelectionModel().getSelectedItem(), "Object: " + txtSubject.getText(), "Text: " + txtMsg.getText());
        String beginPath = "src/main/resources/msg/";
        Path mail = Paths.get(beginPath + txtSubject.getText() + ".txt");
        Path p = Files.createFile(mail);
        System.out.println("create at " + p);

        Files.write(mail, lines, StandardCharsets.UTF_8);
        confirmMsgSend();
    }

    public void confirmMsgSend() {
        Alert.AlertType type = Alert.AlertType.INFORMATION;
        Alert alert = new Alert(type, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setHeaderText("Le mail a bien été envoyé !");

        ButtonType result = alert.showAndWait().orElseThrow();

        if (result == ButtonType.OK) {
            txtSubject.clear();
            txtMsg.clear();
            alert.close();
        }
    }

    public void openAbout() throws IOException {
        FXMLLoader fxmlAbout = new FXMLLoader(App.class.getResource("about.fxml"));
        Stage window = new Stage();
        Scene about = new Scene(fxmlAbout.load());
        window.setTitle("A propos de Web mail");
        window.getIcons().add(new Image(App.class.getResource("/icon.png").toExternalForm()));
        window.setScene(about);
        window.setResizable(false);
        window.showAndWait();
    }

    public void openAddNewAddress() throws IOException {
        FXMLLoader fxmlAdd = new FXMLLoader(App.class.getResource("addAddress.fxml"));
        Stage window = new Stage();
        Scene newMailScene = new Scene(fxmlAdd.load());
        window.setTitle("Ajouter une nouvelle adresse mail");
        window.getIcons().add(new Image(App.class.getResource("/icon.png").toExternalForm()));
        window.setScene(newMailScene);
        window.setResizable(false);
        window.showAndWait();
    }

}