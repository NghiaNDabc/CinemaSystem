package com.example.hethongdatvexemphim.socket;

import com.example.hethongdatvexemphim.dao.MovieDAO;
import com.example.hethongdatvexemphim.dao.UserDAO;
import com.example.hethongdatvexemphim.models.Movie;
import com.example.hethongdatvexemphim.models.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.mysql.cj.Session;


import java.io.*;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable{
    Socket clientSocket;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    String folderPath = "/com/example/hethongdatvexemphim/images/";
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
//
//    public void Them(GoiTin goiTin){
//        Movie movie =  goiTin.getDsSach().get(0);
//
//        MovieDAO.getInstance().insert(movie);
//    }
//    public  void Tim(GoiTin goiTin){
//        Movie sv =  goiTin.getDsSach().get(0);
//
//        Movie selectedSV = MovieDAO.getInstance().selectById(sv.getId_Movie());
//        try {
//            ObjectOutputStream oos =  new ObjectOutputStream(clientSocket.getOutputStream());
//            if(selectedSV == null){
//                Movie sach1 =  new Movie();
//                sach1.setId_Movie(0);
//                oos.writeObject(sach1);
//            }
//            else{
//                oos.writeObject(selectedSV);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    private List<File> getImageFiles(String folderPath) {
        List<File> imageFiles = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && isImageFile(file.getName())) {
                    imageFiles.add(file);
                }
            }
        }
        return imageFiles;
    }

    private boolean isImageFile(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return extension.equalsIgnoreCase("jpg") ||
                extension.equalsIgnoreCase("jpeg") ||
                extension.equalsIgnoreCase("png") ||
                extension.equalsIgnoreCase("gif");
    }

    private void sendImage(File imageFile, DataOutputStream dos) throws IOException {
        byte[] imageData = new byte[(int) imageFile.length()];
        FileInputStream fis = new FileInputStream(imageFile);
        fis.read(imageData);
        fis.close();

        dos.writeUTF(imageFile.getName());
        dos.writeInt(imageData.length);
        dos.write(imageData);
        dos.flush();
    }

    private void sendMoviesAndImages(DataOutputStream dos) throws IOException {
        List<Movie> movies = MovieDAO.getInstance().selectAll();
        List<File> imageFiles = getImageFiles(folderPath);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        try {
            Gson gson = new Gson();
            String moviesJson = gson.toJson(movies);
            out.println(moviesJson);

            for (File imageFile : imageFiles) {
                sendImage(imageFile, dos);
            }
            dos.writeUTF("END");  // Signal the end of images
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void DangNhap(GoiTin<User> goiTin){
        String userName =goiTin.getListT().get(0).getUserName().toString();
        String matKhau =goiTin.getListT().get(0).getPassWord().toString();
        System.out.println(userName+ " " + matKhau);
        User user = UserDAO.getInstance().selectByUserPass(userName,matKhau);
        System.out.println(user);
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            Gson gson = new Gson();
            String responseJson = gson.toJson(user);
            out.println(responseJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {

        }
    }
    public void DangKy(GoiTin<User> goiTin) {
        User user = goiTin.getListT().get(0);
        int i = UserDAO.getInstance().insert(user);
        String mess = (i > 0) ? "Đăng ký thành công" : "Tên đăng nhập hoặc mật khẩu đã tồn tại";

        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            Gson gson = new Gson();
            String responseJson = gson.toJson(mess);
            out.println(responseJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void HienThi() {
        ArrayList<Movie> listFromFile = MovieDAO.getInstance().selectAll();
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            Gson gson = new Gson();
            String responseJson = gson.toJson(listFromFile);
            out.println(responseJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*public void HienThi(){
        ArrayList<Movie> listFromFile =  new ArrayList<>();
        listFromFile = MovieDAO.getInstance().selectAll();
        try{
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
//            List<File> imageFiles = getImageFiles(folderPath);
//
//            // Gửi số lượng ảnh đến client
//            oos.writeInt(imageFiles.size());
//            oos.flush();
//            // Gửi từng ảnh đến client
//            for (File imageFile : imageFiles) {
//                sendImage(imageFile, oos);
//            }
            oos.writeObject(listFromFile);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
               if(oos!= null) oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }*/
    @Override
    public void run() {
    try{
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String json = in.readLine();
        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        String yeuCau = jsonObject.get("yeuCau").getAsString();

        if(yeuCau.equals("DangNhap")){
            InetAddress inetAddress = clientSocket.getInetAddress();
            System.out.println("Đã kết nối với " + inetAddress.getHostAddress() + " dang nhap");
            System.out.println(json);
            Type goiTinUserType = new TypeToken<GoiTin<User>>(){}.getType();
            GoiTin<User> goiTinUser = new Gson().fromJson(json, goiTinUserType);
            System.out.println(goiTinUser);
            System.out.println("Dang nhap");

            DangNhap(goiTinUser);
        }

        if (yeuCau.equals("DangKy")) {
            InetAddress inetAddress = clientSocket.getInetAddress();
            System.out.println("Đã kết nối với " + inetAddress.getHostAddress() + " dang ky");
            Type goiTinUserType = new TypeToken<GoiTin<User>>() {}.getType();
            GoiTin<User> goiTinUser = gson.fromJson(json, goiTinUserType);
            DangKy(goiTinUser);
        }


        if(yeuCau.equals("XemDanhSachPhim")){
            InetAddress inetAddress = clientSocket.getInetAddress();
            System.out.println("Đã kết nối với " + inetAddress.getHostAddress() + " Xem danh sach phim");

            HienThi();
//            try (DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream())) {
//                sendMoviesAndImages(dos);
//            }
        }
//        if(goiTin.getYeuCau()==YeuCau.DangKy){
//            System.out.println("Đăng ký");
//            GoiTin<User> goiTinUser = (GoiTin<User>) goiTin;
//            DangKy(goiTinUser);
//        }

        if(in!=null) in.close();

    } catch (IOException e) {
        throw new RuntimeException(e);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    finally {
        try{
            if(clientSocket!=null) clientSocket.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
}
