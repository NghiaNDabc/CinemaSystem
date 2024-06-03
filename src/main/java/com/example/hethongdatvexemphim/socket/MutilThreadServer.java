package com.example.hethongdatvexemphim.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MutilThreadServer implements Runnable{
    ServerSocket serverSocket;
    final int port_server = 8888;
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port_server);
            while (true) {
                Socket socket = serverSocket.accept();
                InetAddress inetAddress = socket.getInetAddress();
                System.out.println("Đã kết nối với " + inetAddress.getHostAddress());
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
