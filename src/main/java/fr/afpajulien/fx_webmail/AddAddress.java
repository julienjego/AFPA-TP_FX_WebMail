package fr.afpajulien.fx_webmail;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * The type Add address.
 */
public class AddAddress {

    @FXML
    private Button btnAdd;

    @FXML
    private TextField txtMail;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenom;

    /**
     * Add new address.
     */
    public void addNewAddress() {
        String name = txtNom.getText().toUpperCase();
        String firstName = txtPrenom.getText();
        String mail = txtMail.getText().toLowerCase();

        String newMail = String.format("%n%s %s;%s", firstName, name, mail);

        if (isValiEmail(mail)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/mails.csv", true))) {
                writer.append(newMail);
                alertNewAddressOK();
                txtPrenom.clear();
                txtNom.clear();
                txtMail.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            alertWrongAddress();
            txtPrenom.clear();
            txtNom.clear();
            txtMail.clear();
        }

    }

    /**
     * Window alerts address is not added.
     */

    private void alertWrongAddress() {
        Alert.AlertType type = Alert.AlertType.ERROR;
        Alert alert = new Alert(type, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setHeaderText("L'adresse mail n'est pas valide !");

        ButtonType result = alert.showAndWait().orElseThrow();

        if (result == ButtonType.OK) {
            alert.close();
        }
    }

    /**
     * Window confirms address is added.
     */

    private void alertNewAddressOK() {
        Alert.AlertType type = Alert.AlertType.INFORMATION;
        Alert alert = new Alert(type, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setHeaderText("L'adresse mail a bien été enregistré !");

        ButtonType result = alert.showAndWait().orElseThrow();

        if (result == ButtonType.OK) {
            alert.close();
        }
    }

    /**
     * Pattern to verify mail address.
     * @param email
     * @return
     */
    private boolean isValiEmail(String email) {
        Pattern mailPattern = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
        );
        return mailPattern.matcher(email).matches();
    }
}
