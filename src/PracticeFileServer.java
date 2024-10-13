import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class PracticeFileServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket=new ServerSocket(9000);
            System.out.println("Server is waiting for the connection.");

            Socket socket=serverSocket.accept();
            System.out.println("Client Connected.");

            File file=new File("example.txt");
            FileInputStream fileInputStream=new FileInputStream(file);

            OutputStream outputStream=socket.getOutputStream();
            DataOutputStream dataOutputStream=new DataOutputStream(outputStream);

            dataOutputStream.writeUTF(file.getName());
            dataOutputStream.writeLong(file.length());

            byte[] buffer=new byte[1024];
            int read;
            while((read=fileInputStream.read(buffer))!=-1)
                dataOutputStream.write(buffer,0,read);

            InputStream inputStream=socket.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

            String response=bufferedReader.readLine();
            System.out.println("Client Response : " +response);

            bufferedReader.close();
            inputStream.close();
            fileInputStream.close();
            dataOutputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e){
            System.out.println("An error occurred : " +e.getMessage());
        }
    }
}
