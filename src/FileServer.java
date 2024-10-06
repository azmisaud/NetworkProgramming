import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket=new ServerSocket(5000);
            System.out.println("Server is waiting for connection.");

            Socket socket=serverSocket.accept();
            System.out.println("Client connected.");

            File file=new File("example.txt");
            FileInputStream fis=new FileInputStream(file);

            DataOutputStream dos=new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(file.getName());
            dos.writeLong(file.length());

            byte[] buffer=new byte[4096];
            int read;
            while((read=fis.read(buffer))!=-1)
                dos.write(buffer,0,read);

            fis.close();
            dos.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("An error occurred : " +e.getMessage());
        }
    }
}
