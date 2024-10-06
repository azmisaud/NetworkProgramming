import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HTMLClient {
    public static void main(String[] args) {
        try{
            Socket socket=new Socket("localhost",7123);
            System.out.println("Connected to the server.");

            DataInputStream dataInputStream=new DataInputStream(socket.getInputStream());

            String fileName=dataInputStream.readUTF();
            long fileSize=dataInputStream.readLong();

            System.out.println("Receiving file : " +fileName+ " , File Size : " +fileSize+ " bytes.");

            FileOutputStream fos=new FileOutputStream("recieved_"+fileName);

            byte[] buffer=new byte[(int) fileSize];
            int read;
            while(fileSize>0 && (read=dataInputStream.read(buffer,0,(int)Math.min(buffer.length,fileSize)))!=-1){
                fos.write(buffer,0,read);
                fileSize-=read;
            }
            System.out.println("File received successfully.");
            socket.close();
            dataInputStream.close();
            fos.close();
        } catch (IOException e){
            System.out.println("An error occurred : " + e.getMessage());
        }
    }
}
