import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class P1TCPServer {
    private static final int PORT=9876;

    public static void main(String[] args) {
        try(ServerSocket serverSocket=new ServerSocket(PORT);
            Scanner scanner=new Scanner(System.in)){

            System.out.println("Server is listening on port " +PORT);

            try(Socket socket=serverSocket.accept();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true)){

                System.out.println("Client Connected.");
                printWriter.println("You can send messages, send 'bye' to end the session.");

                String message;

                while(true) {
                    message=bufferedReader.readLine();
                    if(message!=null){
                        if(message.equalsIgnoreCase("bye"))
                            break;

                        System.out.println("Client : "+message);
                        System.out.print("You : ");
                        String response=scanner.nextLine();
                        printWriter.println("Server : " +response);
                    }
                }
            }
        } catch (IOException e){
            System.err.println("An error occurred : " +e.getMessage());
        }
    }
}
