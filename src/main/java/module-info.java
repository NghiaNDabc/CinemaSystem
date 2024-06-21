module com.example.hethongdatvexemphim {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.java;
    requires com.google.gson;
    opens com.example.hethongdatvexemphim.socket to com.google.gson;
    opens com.example.hethongdatvexemphim.models to com.google.gson;
    opens com.example.hethongdatvexemphim.controllers.client to javafx.fxml;
    opens com.example.hethongdatvexemphim.controllers.server to javafx.fxml;
    opens com.example.hethongdatvexemphim to javafx.fxml;
    exports com.example.hethongdatvexemphim;
    exports com.example.hethongdatvexemphim.models;
    exports com.example.hethongdatvexemphim.controllers.client;
    exports com.example.hethongdatvexemphim.util;
}