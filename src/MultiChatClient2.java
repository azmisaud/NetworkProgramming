import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MultiChatClient2 {
    private static final String SERVER_ADDRESS="localhost";
    private static final int SERVER_PORT=1234;

    public static void main(String[] args) {
        try(Socket socket=new Socket(SERVER_ADDRESS,SERVER_PORT);
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
            Scanner scanner=new Scanner(System.in)) {

            System.out.println("Connected to the server.");

            new Thread( () -> {
                try {
                    String message;
                    while((message=bufferedReader.readLine())!=null)
                        System.out.println(message);
                } catch (IOException e){
                    System.err.println("An error occurred : " +e.getMessage());
                }
            }).start();

            while(true){
                String input=scanner.nextLine();
                printWriter.println(input);
            }
        } catch (IOException e){
            System.err.println("An error occurred : " +e.getMessage());
        }
    }
}
