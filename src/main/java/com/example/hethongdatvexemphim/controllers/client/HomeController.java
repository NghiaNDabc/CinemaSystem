package com.example.hethongdatvexemphim.controllers.client;

import com.example.hethongdatvexemphim.models.Movie;
import com.example.hethongdatvexemphim.models.User;
import com.example.hethongdatvexemphim.socket.GoiTin;
import com.example.hethongdatvexemphim.socket.YeuCau;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import  com.google.gson.reflect.TypeToken;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.lang.reflect.Type;
public class HomeController implements Initializable {
    List<Movie> movies;
    @FXML
    private BorderPane root;
    @FXML
    private VBox content;
    @FXML
    Label labelUserFullname;public User user;
    @FXML
    TextField txtTimKiem;
    @FXML
    Button btnTimKiem;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ViewHienThiPhim();
    }
    @FXML
    void btnTimKiemClick(ActionEvent actionEvent) {
        if(txtTimKiem.getText().equals("")){ViewHienThiPhim(); return;}
        ArrayList<Movie> timKiem = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getMovieName().toLowerCase().contains(txtTimKiem.getText().toLowerCase()))
                timKiem.add(movie);
        }
        content.getChildren().clear();
        for (Movie movie : timKiem) {
            content.getChildren().add(MovieBox(movie));
        }
    }
    public void setUser(User user) {
        this.user = user;
        // Now you can use the user information
        labelUserFullname.setText("Xin chào, " + user.getFullName());
    }
    void ViewHienThiPhim(){
        movies = getListMovie();
        content.getChildren().clear();
        for (Movie movie : movies) {
            content.getChildren().add(MovieBox(movie));
        }
    }
    HBox MovieBox(Movie movie){
        HBox movieBox = new HBox();
        movieBox.setSpacing(10);
        movieBox.setStyle("-fx-border-color: black; -fx-padding: 10px;");
        System.out.println(movie.getImage());
        ImageView movieImageView = new ImageView();
        movieImageView.setFitHeight(180);
        movieImageView.setFitWidth(100);
        String imagePath = "/com/example/hethongdatvexemphim/images/" + movie.getImage(); // Use absolute path
        try {
            Image movieImage = new Image(getClass().getResourceAsStream(imagePath)); // Load image from file system
            movieImageView.setImage(movieImage);
        } catch (Exception e) {
            System.out.println("Error loading image: " + imagePath);
            e.printStackTrace();
        }
        VBox movieInfo = new VBox();
        movieInfo.setSpacing(5);

        Label titleLabel = new Label(movie.getMovieName());
        titleLabel.setStyle("-fx-font-weight: bold");
        Label descriptionLabel = new Label(movie.getDescripsion());
        descriptionLabel.setWrapText(true);
        Label durationLabel = new Label("Duration: " + movie.getDuration() + " minutes");
        Label ratingLabel = new Label("Country: " + movie.getCountry());

        Button bookButton = new Button("Đặt vé");
        bookButton.setOnAction(event -> {
            // Handle booking action
            System.out.println("Đặt vé cho phim: " + movie.getMovieName());
        });
        movieInfo.getChildren().addAll(titleLabel, descriptionLabel, durationLabel, ratingLabel, bookButton);
        movieBox.getChildren().addAll(movieImageView, movieInfo);
        return  movieBox;
    }
    public static ArrayList<Movie> getListMovie() {
        ArrayList<Movie> list = null;
        try (Socket socket = new Socket("localhost", 8888);
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Tạo yêu cầu xem danh sách phim
            GoiTin<Movie> goiTin = new GoiTin<>(null, YeuCau.XemDanhSachPhim);
            Gson gson = new Gson();
            String json = gson.toJson(goiTin);
            printWriter.println(json);

            // Đọc phản hồi từ server
            String responseJson = in.readLine();
            Type listType = new TypeToken<ArrayList<Movie>>(){}.getType();
            list = gson.fromJson(responseJson, listType);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    /*
    public static ArrayList<Movie> getListMovie(){
        ArrayList<Movie> list=null;
        Socket socket = null;
        ObjectOutputStream oup = null;
        ObjectInputStream inp = null;
        try  {
            socket = new Socket("localhost",8888);
            //oup = new ObjectOutputStream(socket.getOutputStream());

            //ArrayList<Movie> saches =  new ArrayList<>();
            GoiTin <Movie> goiTin = new GoiTin(null, YeuCau.XemDanhSachPhim);
            //oup.writeObject(goiTin);

            Gson gson = new Gson();
            String json = gson.toJson(goiTin);
            System.out.println(json);
            PrintWriter printWriter =  new PrintWriter(socket.getOutputStream(),true);
            printWriter.println(json);
            inp = new ObjectInputStream(socket.getInputStream());
            list= (ArrayList<Movie> ) inp.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }*/
}
