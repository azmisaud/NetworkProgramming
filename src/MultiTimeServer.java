import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

class TimeClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;

    public TimeClientHandler(Socket socket){
        this.clientSocket=socket;
    }

    public void run() {
        try {
            out=new PrintWriter(clientSocket.getOutputStream(),true);

            LocalDateTime now=LocalDateTime.now();
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String currentTime=now.format(formatter);

            MultiTimeServer.broadcastTime(currentTime);
        } catch (IOException e){
            System.out.println("An error occurred : " +e.getMessage());
        } finally {
            try {
                MultiTimeServer.removeClient(this);
                out.close();
                clientSocket.close();
            } catch (IOException e){
                System.out.println("An error occurred : " +e.getMessage());
            }
        }
    }

    public void sendTime(String Time){
        out.println(Time);
    }
}
public class MultiTimeServer {
    private static Set<TimeClientHandler> timeClientHandlerSet=new HashSet<>();

    public static void broadcastTime(String Time){
        for (TimeClientHandler client : timeClientHandlerSet){
            client.sendTime(Time);
        }
    }
    public static void removeClient(TimeClientHandler timeClientHandler){
        timeClientHandlerSet.remove(timeClientHandler);
    }
    public static void main(String[] args) {
        ServerSocket serverSocket=null;
        try {
            serverSocket=new ServerSocket(1000);
            System.out.println("Server started on port 1000");

            while(true) {
                Socket clientSocket=serverSocket.accept();
                System.out.println("New client connected.");
                TimeClientHandler timeClientHandler=new TimeClientHandler(clientSocket);
                timeClientHandlerSet.add(timeClientHandler);
                new Thread(timeClientHandler).start();
            }
        } catch (IOException e){
            System.out.println("An error occurred : " +e.getMessage());
        } finally {
            if(serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e){
                    System.out.println("An error occurred : " +e.getMessage());
                }
            }
        }
    }
}
