import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PracticeMultiChatServer {
    private ServerSocket serverSocket;
    public  PracticeMultiChatServer(ServerSocket serverSocket){
        this.serverSocket=serverSocket;
    }

    public void startServer() {
        try {
            while(!serverSocket.isClosed()){
                Socket socket=serverSocket.accept();
                System.out.println("A new client has connected.");

                ClientHandler2 clientHandler=new ClientHandler2(socket);

                Thread thread=new Thread(clientHandler);
                thread.start();

            }
        } catch (IOException e){
            System.out.println("An error occurred : " +e.getMessage());
        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket!=null)
                serverSocket.close();
        } catch (IOException e){
            System.out.println("An error occurred : " +e.getMessage());

        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket=new ServerSocket(9002);
            PracticeMultiChatServer practiceMultiChatServer=new PracticeMultiChatServer(serverSocket);
            practiceMultiChatServer.startServer();
        } catch (IOException e){
            System.out.println("An error occurred : " +e.getMessage());
        }
    }
}
