import java.io.*;
import java.net.Socket;

public class MultiEchoClient {
    private static final String SERVER_ADDRESS="localhost";
    private static final int SERVER_PORT=12345;

    public static void main(String[] args) {
        try(Socket socket=new Socket(SERVER_ADDRESS,SERVER_PORT);
            OutputStream out=socket.getOutputStream();
            PrintWriter printWriter=new PrintWriter(out,true);
            InputStream inputStream=socket.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            BufferedReader consoleReader=new BufferedReader(new InputStreamReader(System.in))){

            System.out.println("Connected to the server.");

            String userInput;

            while((userInput=consoleReader.readLine())!=null){
                printWriter.println(userInput);
                String serverResponse=bufferedReader.readLine();
                System.out.println("Server : " +serverResponse);
            }
        } catch (IOException e){
            System.err.println("An error occurred : " +e.getMessage());
        }
    }
}
