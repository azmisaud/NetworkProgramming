import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8080);

        OutputStream outputStream=socket.getOutputStream();
        PrintWriter writer=new PrintWriter(outputStream,true);

        InputStream inputStream=socket.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));

        writer.println("Hello, Server!");

        String response= reader.readLine();
        System.out.println("Server Response : " +response);

        socket.close();
    }
}
