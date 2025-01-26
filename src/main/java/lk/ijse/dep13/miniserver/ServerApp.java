package lk.ijse.dep13.miniserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

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

                    OutputStream os = localSocket.getOutputStream();
                    if(!cmd.equalsIgnoreCase("GET")){

                    }else if(host == null){

                    }


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    private static void sendErrorResponse(OutputStream os, int statusCode, String statusMessage, String body) throws IOException {
        String httpResponse = """
                                HTTP/1.1 405 Method Not Allowed
                                Server: mini-server
                                Date: %s
                                Content-Type: text/html
                                Connection: close
                                """.formatted(LocalDateTime.now());
        os.write(httpResponse.getBytes());
        os.write("\r\n".getBytes());
        os.write(body.getBytes());
        os.flush();
    }
}
