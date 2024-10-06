import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket=new Socket("127.0.0.1",8080);
            System.out.println("Connected to the server.");

            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);

            BufferedReader clientInput=new BufferedReader(new InputStreamReader(System.in));

            String clientMessage, serverMessage;

            while(true) {
                System.out.print("Client : ");
                clientMessage=clientInput.readLine();
                out.println(clientMessage);

                if (clientMessage.equalsIgnoreCase("bye")){
                    System.out.println("Client has ended the chat.");
                    break;
                }

                serverMessage=in.readLine();
                if (serverMessage==null){
                    System.out.println("Server has disconnected");
                    break;
                }
                System.out.println("Server : " +serverMessage);

                if (serverMessage.equalsIgnoreCase("bye")){
                    System.out.println("Server has ended the chat.");
                    break;
                }
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("An error occurred : " + e.getMessage());
        }
    }
}
