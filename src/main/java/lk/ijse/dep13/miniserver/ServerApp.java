package lk.ijse.dep13.miniserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(90);
        System.out.println("Server started on port 90.");

        while (true) {
            Socket localSocket = serverSocket.accept();
            System.out.println("Client connected: " + localSocket.getRemoteSocketAddress());

            new Thread(() -> {
                try {
                    InputStream is = localSocket.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);

                    String commandLine = br.readLine();
                    if (commandLine == null) return;
                    String[] commands = commandLine.split(" ");
                    String cmd = commands[0];
                    String resourcePath = commands[1];
                    System.out.println(cmd + " " + resourcePath);

                    String line;
                    String host = null;
                    while ((line = br.readLine()) != null && !line.isBlank()) {
                        String header = line.split(":")[0].strip();
                        String value = line.substring(line.indexOf(":") + 1).strip();
                        if(header.equalsIgnoreCase("Host")){
                            host = value;
                        }
                    }


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
