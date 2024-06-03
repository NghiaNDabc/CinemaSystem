package com.example.hethongdatvexemphim.socket;

import com.example.hethongdatvexemphim.dao.MovieDAO;
import com.example.hethongdatvexemphim.dao.UserDAO;
import com.example.hethongdatvexemphim.models.Movie;
import com.example.hethongdatvexemphim.models.User;


import java.io.*;
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

    private static List<File> getImageFiles(String folderPath) {
        List<File> imageFiles = new ArrayList<>();
        File folder = new File(folderPath);

        // Lọc ra các file ảnh từ thư mục
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

    private static boolean isImageFile(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return extension.equalsIgnoreCase("jpg") ||
                extension.equalsIgnoreCase("jpeg") ||
                extension.equalsIgnoreCase("png") ||
                extension.equalsIgnoreCase("gif");
    }
    private static void sendImage(File imageFile, ObjectOutputStream outputStream) throws IOException {
        byte[] imageData = new byte[(int) imageFile.length()];
        FileInputStream fis = new FileInputStream(imageFile);
        fis.read(imageData);

        // Gửi tên file và dữ liệu của ảnh
        outputStream.writeUTF(imageFile.getName());
        outputStream.writeInt(imageData.length);
        outputStream.write(imageData);
        outputStream.flush();

        fis.close();
    }
    public void DangNhap(GoiTin<User> goiTin){
        String userName =goiTin.getListT().get(0).getUserName();
        String matKhau =goiTin.getListT().get(0).getPassWord();
        User user = UserDAO.getInstance().selectByUserPass(userName,matKhau);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.writeObject(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void HienThi(){
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
                oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void run() {
    try{
        ois = new ObjectInputStream(clientSocket.getInputStream());
        GoiTin goiTin = (GoiTin) ois.readObject();
        if(goiTin.getYeuCau()==YeuCau.XemDanhSachPhim){
            HienThi();
        }
        if(goiTin.getYeuCau()==YeuCau.DangNhap){
            System.out.println("DDang nhap");
            DangNhap(goiTin);
        }
    } catch (IOException e) {
        throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    }
    }
}
