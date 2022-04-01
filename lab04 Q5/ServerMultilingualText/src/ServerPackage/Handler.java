package ServerPackage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class Handler implements Runnable {

    public static ArrayList<Handler> clientHandlers = new ArrayList<>();

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;


    public Handler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader
            		(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter= new BufferedWriter
            		(new OutputStreamWriter(socket.getOutputStream()));

            this.clientUsername = bufferedReader.readLine();
            
            clientHandlers.add(this);
            broadcastMessage
            ("SERVER: " + clientUsername + " has entered the chat!");
        } catch (IOException e) {

            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void run() {
        String messageFromClient;

        while (socket.isConnected()) {
            try {

                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {

                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void broadcastMessage(String messageToSend) {
        for (Handler clientHandler : clientHandlers) {
            try {

                if (!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
               
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUsername + " has left the chat!");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader,
    		BufferedWriter bufferedWriter) {

        removeClientHandler();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
