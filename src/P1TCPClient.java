import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class P1TCPClient {
    private static final String SERVER_ADDRESS="localhost";
    private static final int SERVER_PORT=9876;

    public static void main(String[] args) {
        try(Socket socket=new Socket(SERVER_ADDRESS,SERVER_PORT);
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
            Scanner scanner=new Scanner(System.in)){

            System.out.println("Connected to the server.");
            System.out.println(bufferedReader.readLine());

            String message;

            while(true){
                System.out.print("You : ");
                message=scanner.nextLine();

                printWriter.println(message);
                String response=bufferedReader.readLine();
                if(response==null)
                    break;
                System.out.println(response);
            }
        } catch (IOException e){
            System.err.println("An error occurred : " +e.getMessage());
        }
    }
}
