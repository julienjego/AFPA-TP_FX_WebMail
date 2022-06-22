package fr.afpajulien.fx_webmail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
		try {
			addMailAddresses();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		btnMsgSend.setDisable(true);
		btnMsgSendFooter.setDisable(true);
		itmSend.setDisable(true);

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

		cbxDest.valueProperty().addListener((e -> checkIfEmpty()));

	}

	private void addMailAddresses() throws URISyntaxException {
		String path = String.valueOf(Paths.get(App.class.getResource("/mails.csv").toURI()));
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

	public void createNewMsg() {
		txtMsg.clear();
		txtSubject.clear();
	}

	public void openMsg() throws URISyntaxException {
		FileChooser fileChooser = new FileChooser();

		File defaultDir = new File(App.class.getResource("/msg").toURI());
		// TO DO changer path File
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
		} else {
			btnMsgSendFooter.setDisable(false);
			btnMsgSend.setDisable(false);
		}

	}

	public void sendMsg() {
		System.out.println(txtMsg.getText());
		System.out.println(cbxDest.getSelectionModel().getSelectedItem().getClass().getSimpleName());

		File mail = new File("C:/Users/AFPA/IdeaProjects/FX_WebMail/src/main/resources/fr/afpajulien/fx_webmail/msg/"
				+ txtSubject + ".txt");

	}

	/*
	 * public void openAbout() throws IOException { Stage window = new Stage();
	 * FXMLLoader fxmlAbout = new FXMLLoader(App.class.getResource("about.fxml"));
	 * Scene about = new Scene(fxmlAbout.load());
	 * window.setTitle("A propos de Web mail"); //window.getIcons().add(new
	 * Image(String.valueOf(App.class.getResource("icon.png"))));
	 * window.setScene(about); window.show(); }
	 */

}