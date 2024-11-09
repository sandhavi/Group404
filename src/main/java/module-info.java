module org.example.group404 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.group404 to javafx.fxml;
    exports org.example.group404;
    exports org.example.group404.ClassPackage;
    opens org.example.group404.ClassPackage to javafx.fxml;
    exports org.example.group404.Database;
    opens org.example.group404.Database to javafx.fxml;
    exports org.example.group404.ControllerPackage;
    opens org.example.group404.ControllerPackage to javafx.fxml;
}