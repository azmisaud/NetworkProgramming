import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class PracticeTCPClient {
    public static void main(String[] args) {
        try {
            Socket socket=new Socket("localhost",9000);
            System.out.println("Connected to the server.");

            InputStream inputStream=socket.getInputStream();
            byte[] buffer=new byte[4096];
            int bytesRead=inputStream.read(buffer);

            if (bytesRead!=-1){
                String serverMessage=new String(buffer,0,bytesRead);
                System.out.println("Received from Server : " +serverMessage);
            }

            OutputStream outputStream=socket.getOutputStream();
            Scanner scanner=new Scanner(System.in);
            System.out.println("Enter your message to the server : ");
            String clientMessage= scanner.nextLine();
            outputStream.write(clientMessage.getBytes());
            outputStream.flush();

            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException e){
            System.out.println("An error occurred : " +e.getMessage());
        }
    }
}
