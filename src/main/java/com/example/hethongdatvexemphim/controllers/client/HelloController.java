package com.example.hethongdatvexemphim.controllers.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    private void handleSeatClick(javafx.event.ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String seatId = clickedButton.getText();
        System.out.println("Seat clicked: " + seatId);

        // Thực hiện logic khi người dùng chọn ghế, ví dụ:
        // - Đổi màu ghế
        // - Gửi thông tin ghế đã chọn đến server
    }
}