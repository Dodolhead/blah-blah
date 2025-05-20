module com.tubesoop {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.tubesoop to javafx.fxml;
    exports com.tubesoop;
}
