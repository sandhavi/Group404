module org.example.group404 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.group404 to javafx.fxml;
    exports org.example.group404;
}