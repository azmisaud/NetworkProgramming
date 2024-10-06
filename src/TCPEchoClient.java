import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPEchoClient {
    public static void main(String[] args) {
        try {
            Socket socket=new Socket("localhost",6000);
            System.out.println("Connected to TCP Echo Server.");

            BufferedReader userInput=new BufferedReader(new InputStreamReader(System.in));
            PrintWriter output=new PrintWriter(socket.getOutputStream(),true);
            BufferedReader input=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message;

            while (true) {
                System.out.println("Enter a message (or 'exit' to quit) : ");
                message=userInput.readLine();

                if(message.equalsIgnoreCase("exit"))
                    break;

                output.println(message);
                System.out.println("Echo from server : " +input.readLine());
            }

            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("An error occurred : " + e.getMessage());
        }
    }
}
