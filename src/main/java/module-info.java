module com.example.cpu_fx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;

    opens com.example.cpu_fx to javafx.fxml;
    exports com.example.cpu_fx;
}