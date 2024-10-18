import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MultiTimeClient {
    private Socket socket;
    private BufferedReader in;

    public MultiTimeClient(String host,int port){
        try {
            socket=new Socket(host,port);
            System.out.println("Connected to time server.");

            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(new IncomingTimeHandler()).start();
        } catch (IOException e){
            System.out.println("An error occurred : " +e.getMessage());
        } finally {
            try {
                if (socket!=null) socket.close();
                if (in!=null) in.close();
            } catch (IOException e){
                System.out.println("An error occurred : " +e.getMessage());
            }
        }
    }

    class IncomingTimeHandler implements Runnable {
        public void run() {
            String time;
            try {
                time=in.readLine();
                System.out.println(time);
            } catch (IOException e){
                System.out.println("An error occurred " +e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new MultiChatClient("localhost",1000);
    }
}
