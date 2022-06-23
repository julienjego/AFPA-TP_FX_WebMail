package fr.afpajulien.fx_webmail;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AddAddress {

    private Controller controller = new Controller();

    @FXML
    private Button btnAdd;

    @FXML
    private TextField txtMail;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenom;

    //private List<String> newMail = new ArrayList<>();

    public void addNewAddress() throws IOException, URISyntaxException {
        String name = txtNom.getText().toUpperCase();
        String firstName = txtPrenom.getText();
        String mail = txtMail.getText();

        //TODO add new to mails.csv and refresh csv each time you click on the combobox

        //String[] newMail = {firstName + " " + name.toUpperCase() + " : ", mail};

        //String newMail = String.format("%s %s : %s", firstName, name, mail);

        //controller.getCbxDest().getItems().add(newMail);




    }
}
