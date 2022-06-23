package fr.afpajulien.fx_webmail;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

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


    public void addNewAddress() {
        String name = txtNom.getText().toUpperCase();
        String firstName = txtPrenom.getText();
        String mail = txtMail.getText();

        String newMail = String.format("%s %s;%s", firstName, name, mail);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("mails.csv", true))) {
            writer.append(newMail);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO add new mail to mails.csv and refresh csv each time you click on the combobox



    }
}
