import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PhotoClient {
    public static void main(String[] args) {
        Socket socket=null;
        DataInputStream dataInputStream=null;
        FileOutputStream fileOutputStream=null;

        try{
            socket=new Socket("localhost",102);
            System.out.println("Connected to the Server.");

            dataInputStream=new DataInputStream(socket.getInputStream());

            String fileName=dataInputStream.readUTF();
            long fileSize=dataInputStream.readLong();

            System.out.println("Receiving file : " +fileName+ " , File Size : "+fileSize);

            fileOutputStream=new FileOutputStream("received_"+fileName);

            byte[] buffer=new byte[4096];
            int read;

            while(fileSize>0 && (read=dataInputStream.read(buffer,0,(int)Math.min(buffer.length,fileSize)))!=-1){
                fileOutputStream.write(buffer,0,read);
                fileSize-=read;
            }
            System.out.println("File Received Successfully");
        } catch (IOException e){
            System.out.println("An error occurred : " +e.getMessage());
        }
        finally {
            try{
                if(socket!=null) socket.close();
                if (dataInputStream!=null) dataInputStream.close();
                if (fileOutputStream!=null) fileOutputStream.close();
            } catch (IOException e){
                System.out.println("An error occurred : " +e.getMessage());
            }
        }
    }
}
