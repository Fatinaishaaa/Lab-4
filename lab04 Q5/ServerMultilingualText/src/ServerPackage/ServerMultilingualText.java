package ServerPackage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMultilingualText {

    private final ServerSocket serverSocket;

    public ServerMultilingualText(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try {

            while (!serverSocket.isClosed()) {
            	
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected!");
                Handler clientHandler = new Handler(socket);
                Thread thread = new Thread(clientHandler);

                thread.start();
            }
        } catch (IOException e) {
            closeServerSocket();
        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2441);
        ServerMultilingualText server = new ServerMultilingualText(serverSocket);
        server.startServer();
    }

}