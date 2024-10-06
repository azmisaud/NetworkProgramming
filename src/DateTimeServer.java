import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeServer {
    public static void main(String[] args) {
        ServerSocket serverSocket=null;
        Socket socket=null;
        DataOutputStream dataOutputStream=null;

        try{
            serverSocket=new ServerSocket(5643);
            System.out.println("Server is waiting for connection.");

            socket=serverSocket.accept();
            System.out.println("Client connected.");

            LocalDateTime now=LocalDateTime.now();
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm::ss");
            String currentTime=now.format(formatter);

            dataOutputStream=new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("Current date and time : " +currentTime);
            System.out.println("Date and time sent.");
        } catch (IOException e){
            System.out.println("An error occurred : " +e.getMessage());
        }
        finally {
            try {
                if(serverSocket!=null)
                    serverSocket.close();
                if(socket!=null)
                    socket.close();
                if(dataOutputStream!=null)
                    dataOutputStream.close();
            } catch (IOException e){
                System.out.println("An error occurred : " +e.getMessage());
            }
        }
    }
}
