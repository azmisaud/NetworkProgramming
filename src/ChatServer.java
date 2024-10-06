import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket=new ServerSocket(8080);
            System.out.println("Server is listening on port 8080");

            Socket socket=serverSocket.accept();
            System.out.println("Client Connected");

            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);

            BufferedReader serverInput=new BufferedReader(new InputStreamReader(System.in));

            String clientMessage, serverMessage;

            while(true) {
                clientMessage=in.readLine();
                if(clientMessage==null){
                    System.out.println("Client has disconnected");
                    break;
                }
                System.out.println("Client : " +clientMessage);

                if (clientMessage.equalsIgnoreCase("bye")) {
                    System.out.println("Client has ended the chat");
                    break;
                }

                System.out.print("Server : ");
                serverMessage=serverInput.readLine();
                out.println(serverMessage);

                if (serverMessage.equalsIgnoreCase("bye")) {
                    System.out.println("Server has ended the chat.");
                    break;
                }
            }

            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("An error occurred : " +e.getMessage());
        }
    }
}
