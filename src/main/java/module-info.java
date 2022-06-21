module fr.afpajulien.fx_webmail {
    requires javafx.controls;
    requires javafx.fxml;


    opens fr.afpajulien.fx_webmail to javafx.fxml;
    exports fr.afpajulien.fx_webmail;
}