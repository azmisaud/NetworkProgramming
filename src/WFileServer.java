import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WFileServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket=new ServerSocket(5000)) {
            System.out.println("Server is listening on port 5000.");

            while(true) {
                Socket socket=serverSocket.accept();
                System.out.println("Client Connected.");

                BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String fileName = in.readLine();
                System.out.println("Client Requested : " +fileName);

                File file=new File(fileName);

                if(file.exists() && !file.isDirectory()){
                    BufferedOutputStream out=new BufferedOutputStream(socket.getOutputStream());
                    FileInputStream fileIn=new FileInputStream(file);

                    byte[] buffer=new byte[4096];
                    int bytesRead;

                    while((bytesRead=fileIn.read(buffer))!=-1)
                        out.write(buffer,0,bytesRead);

                    fileIn.close();
                    out.flush();
                    System.out.println("File sent successfully.");
                } else {
                    PrintWriter writer=new PrintWriter(socket.getOutputStream(),true);
                    writer.println("File not found.");
                    System.out.println("File not found");
                }
                socket.close();
            }
        } catch (IOException e){
            System.out.println("An error occurred : " +e.getMessage());
        }
    }
}
