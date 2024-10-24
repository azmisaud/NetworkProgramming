import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MultiEchoClient2 {
    private static final String SERVER_ADDRESS="localhost";
    private static final int SERVER_PORT=1000;

    public static void main(String[] args) {
        try(Socket socket=new Socket(SERVER_ADDRESS,SERVER_PORT);
            PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner=new Scanner(System.in)) {

            System.out.println("Connected to the server.");

            String userInput;

            while((userInput=scanner.nextLine())!=null){
                printWriter.println(userInput);
                String serverResponse=bufferedReader.readLine();
                System.out.println(serverResponse);
            }

        } catch (IOException e){
            System.err.println("An error occurred : " +e.getMessage());
        }
    }
}
