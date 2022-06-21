package fr.afpajulien.fx_webmail;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button btnMsgNew;

    @FXML
    private Button btnMsgOpen;

    @FXML
    private Button btnMsgSend;

    @FXML
    private ComboBox<String> cbxDest;

    @FXML
    private Label lblMsgError;

    @FXML
    private Label lblMsgSend;

    @FXML
    private MenuBar mainMenu;

    @FXML
    private TextField txtSubject;

    private List<String> records = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addMailAdresses();
    }

    private void addMailAdresses() {
        String path = "C:/Users/AFPA/IdeaProjects/FX_WebMail/src/main/resources/fr/afpajulien/fx_webmail/mails.csv";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                records.add(values[0] + " : " + values[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        cbxDest.getItems().addAll(records);

    }

    public void quit(){
        System.exit(0);
    }
}