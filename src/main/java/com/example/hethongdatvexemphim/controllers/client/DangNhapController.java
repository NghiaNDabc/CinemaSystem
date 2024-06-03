package com.example.hethongdatvexemphim.controllers.client;

import com.example.hethongdatvexemphim.models.AlertUtil;
import com.example.hethongdatvexemphim.models.User;
import com.example.hethongdatvexemphim.socket.GoiTin;
import com.example.hethongdatvexemphim.socket.YeuCau;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DangNhapController {
    @FXML
    Button btnDangNhap;
    @FXML
    Button btnDangKy;
    @FXML
    TextField txtTenDangNhap;
    @FXML
    TextField txtMatKhau;
    @FXML
    Label labelThongBao;

    boolean checkText(){
        if(txtTenDangNhap.getText().isEmpty() || txtMatKhau.getText().isEmpty()){
            return false;
        }
        return true;
    }
    public User DangNhap(String username, String password){

        if(checkText() == false){
            AlertUtil.showAlertError("Null", "Không được để trống");
            return null ;
        }
        User user =null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try{
            Socket client =  new Socket("localhost",8888);
            GoiTin<User> tin =  new GoiTin<>();
            User check = new User();
            check.setUserName(username);
            check.setPassWord(password);
            tin.setYeuCau(YeuCau.DangNhap);
            ArrayList<User> list = new ArrayList<>();
            list.add(check);
            tin.setListT(list);
            oos = new ObjectOutputStream(client.getOutputStream());
            oos.writeObject(tin);
            ois = new ObjectInputStream(client.getInputStream());
            user = (User) ois.readObject();

        }catch (Exception e){

        }
        if (user != null){
            return user;
        }
        AlertUtil.showAlertError("Đăng nhập thất bại", "Sai tên đăng nhập hoặc mật khẩu");
        return user;
    }
    @FXML
    public void btnDangKyClick(ActionEvent event){
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/com/example/hethongdatvexemphim/clientView/DangKy.fxml"));
        try {
            Parent dangKyRoot = loader.load();
            Scene dangKyScene = new Scene(dangKyRoot);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(dangKyScene);
            window.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
   public void btnDangNhapClick(ActionEvent event) throws IOException {
       // Xác thực người dùng (có thể thêm mã xác thực ở đây)
       // Nếu xác thực thành công, chuyển sang màn hình chính (Home.fxml)
       // Tải file FXML của màn hình Home

       String username = txtTenDangNhap.getText();
       String password = txtMatKhau.getText();
       User user = DangNhap(username, password);
       if(user == null){
       }

       else {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hethongdatvexemphim/clientView/Home.fxml"));
           Parent homeRoot = loader.load();
            HomeController homeController = loader.getController();
            homeController.setUser(user);
           // Tạo Scene mới
           Scene homeScene = new Scene(homeRoot);

           // Lấy Stage hiện tại từ sự kiện
           Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

           // Đặt Scene mới cho Stage
           currentStage.setScene(homeScene);
           currentStage.show();
       }
    }
}
