import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class FileClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connected to the server.");

            DataInputStream dis = new DataInputStream(socket.getInputStream());

            String fileName = dis.readUTF();
            long fileSize = dis.readLong();

            System.out.println("Receiving file : " +fileName+ " , File Size : " +fileSize+ " bytes");

            FileOutputStream fos=new FileOutputStream("recieved_"+fileName);

            byte[] buffer=new byte[4096];
            int read;
            while(fileSize>0 && (read= dis.read(buffer,0, (int)Math.min(buffer.length,fileSize)))!=-1){
                fos.write(buffer,0,read);
                fileSize-=read;
            }
            System.out.println("File received successfully.");
            socket.close();
            dis.close();
            fos.close();
        } catch (IOException e){
            System.out.println("An error occurred : " +e.getMessage());
        }

    }
}
