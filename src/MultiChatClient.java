import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MultiChatClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader consoleInput;

    public MultiChatClient(String host,int port){
        try {
            socket=new Socket(host,port);
            System.out.println("Connected to chat server.");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
            consoleInput = new BufferedReader(new InputStreamReader(System.in));

            new Thread(new IncomingMessageHandler()).start();

            String userInput;

            while((userInput=consoleInput.readLine())!=null)
                out.println(userInput);
        } catch (IOException e){
            System.out.println("An error occurred : " +e.getMessage());
        } finally {
            try {
                if (socket!=null) socket.close();
                if(in!=null) in.close();
                if(out!=null) out.close();
                if(consoleInput!=null) consoleInput.close();
            } catch (IOException e){
                System.out.println("An error occurred : " +e.getMessage());
            }
        }
    }

    class IncomingMessageHandler implements Runnable {
        public void run() {
            String message;

            try {
                while((message=in.readLine())!=null)
                    System.out.println(message);
            } catch (IOException e) {
                System.out.println("An error occurred : " +e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new MultiChatClient("localhost",1000);
    }
}
