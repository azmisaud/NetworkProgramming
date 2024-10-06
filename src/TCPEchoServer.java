import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPEchoServer {
    public static void main(String[] args) {
        try{
            ServerSocket serverSocket=new ServerSocket(6000);
            System.out.println("Echo Server is Listening");

            Socket socket=serverSocket.accept();
            System.out.println("Client Connected");

            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);

            String message;
            while ((message=bufferedReader.readLine())!=null){
                System.out.println("Received from client : "+message);
                printWriter.println(message);
            }

            bufferedReader.close();
            printWriter.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("An error occurred : " +e.getMessage());
        }
    }
}
