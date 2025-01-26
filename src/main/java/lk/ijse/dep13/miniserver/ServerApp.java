package lk.ijse.dep13.miniserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        System.out.println("Server started on port 80.");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected: " + socket.getRemoteSocketAddress());
        }
    }
}
