import java.io.*;
import java.net.Socket;

public class WFileClient {
    public static void main(String[] args) {
        try (Socket socket=new Socket("localhost",5000)) {
            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
            BufferedReader consoleReader=new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Enter the file name to request : ");
            String fileName=consoleReader.readLine();
            out.println(fileName);

            InputStream inputStream=socket.getInputStream();
            FileOutputStream fileOutputStream=new FileOutputStream("received2_"+fileName);

            byte[] buffer=new byte[4096];
            int bytesRead;
            while((bytesRead=inputStream.read(buffer))!=-1)
                fileOutputStream.write(buffer,0,bytesRead);

            fileOutputStream.close();
            System.out.println("File received successfully.");
        } catch (IOException e){
            System.out.println("An error occurred : " +e.getMessage());
        }
    }
}
