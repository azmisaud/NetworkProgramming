import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PracticeTCPServer {
    public static void main(String[] args) {
        try{
            ServerSocket serverSocket=new ServerSocket(9000);
            System.out.println("Server is waiting for the connection.");

            Socket socket=serverSocket.accept();
            System.out.println("Client is connected.");

            String message="You are allowed to send one message.";
            byte[] msg=message.getBytes();

            OutputStream outputStream=socket.getOutputStream();
            outputStream.write(msg);
            outputStream.flush();

            byte[] buffer=new byte[4096];
            InputStream inputStream=socket.getInputStream();
            int bytesRead=inputStream.read(buffer);

            if(bytesRead!=-1){
                String clientMessage=new String(buffer,0,buffer.length);
                System.out.println("Received from client : " +clientMessage);
            }

            serverSocket.close();
            socket.close();
            outputStream.close();
            inputStream.close();
        } catch (IOException e){
            System.out.println("An error occurred : " +e.getMessage());
        }
    }
}
