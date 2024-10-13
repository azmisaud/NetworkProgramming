import java.io.*;
import java.net.Socket;

public class PracticeFileClient {
    public static void main(String[] args) {
        try {
            Socket socket=new Socket("localhost",9000);
            System.out.println("Connected to the server.");

            InputStream inputStream=socket.getInputStream();
            DataInputStream dataInputStream=new DataInputStream(inputStream);

            String fileName=dataInputStream.readUTF();
            long fileSize=dataInputStream.readLong();

            System.out.println("Receiving file : " +fileName);
            System.out.println("File Size : " +fileSize + " bytes");

            FileOutputStream fileOutputStream=new FileOutputStream("practice_received_"+fileName);

            byte[] buffer=new byte[1024];
            int read;
            while(fileSize>0 && (read=dataInputStream.read(buffer,0, (int) Math.min(buffer.length, fileSize)))!=-1){
                fileOutputStream.write(buffer,0,read);
                fileSize-=read;
            }
            System.out.println("File Received Successfully.");

            OutputStream outputStream=socket.getOutputStream();
            PrintWriter printWriter=new PrintWriter(outputStream);

            printWriter.println("Thank you, Server for the file.");

            printWriter.close();
            outputStream.close();
            fileOutputStream.close();
            dataInputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("An error occurred : " +e.getMessage());
        }
    }
}
