import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(8080);
        System.out.println("Server is listening on port 8080");

        Socket clientSocket=serverSocket.accept();
        System.out.println("Client Connected");

        InputStream inputStream=clientSocket.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));

        OutputStream outputStream=clientSocket.getOutputStream();
        PrintWriter writer=new PrintWriter(outputStream,true);

        String message= reader.readLine();
        System.out.println("Received from client : " +message);

        writer.println("Message Received : " +message);

        clientSocket.close();
        serverSocket.close();
    }
}
