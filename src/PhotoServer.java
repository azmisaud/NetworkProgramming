import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PhotoServer {
    public static void main(String[] args) {
        try{
            ServerSocket serverSocket=new ServerSocket(102);
            System.out.println("Server is waiting for the connection.");

            Socket socket=serverSocket.accept();
            System.out.println("Client Connected.");

            File file=new File("ghlib.jpg");
            FileInputStream fileInputStream=new FileInputStream(file);

            DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(file.getName());
            dataOutputStream.writeLong(file.length());

            byte[] buffer=new byte[4096];

            int read;

            while((read=fileInputStream.read(buffer))!=-1)
                dataOutputStream.write(buffer,0,read);

            fileInputStream.close();
            dataOutputStream.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("An error occurred : " +e.getMessage());
        }
    }
}
