import java.io.*;
import java.net.Socket;

public class PracticeChatClient {
    public static void main(String[] args) {
        try {
            Socket socket=new Socket("127.0.0.1",9001);
            System.out.println("Connected to the server.");

            InputStream inputStream=socket.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

            OutputStream outputStream=socket.getOutputStream();
            PrintWriter printWriter=new PrintWriter(outputStream,true);

            BufferedReader bufferedReader1=new BufferedReader(new InputStreamReader(System.in));

            String clientMessage, serverMessage;

            while(true) {
                System.out.print("Client : ");
                clientMessage=bufferedReader1.readLine();
                printWriter.println(clientMessage);

                if (clientMessage.equalsIgnoreCase("bye")){
                    System.out.println("Client has ended the chat.");
                    break;
                }

                serverMessage=bufferedReader.readLine();
                if(serverMessage==null){
                    System.out.println("Server has disconnected.");
                    break;
                }
                System.out.println("Server : " +serverMessage);

                if(serverMessage.equalsIgnoreCase("bye")){
                    System.out.println("Server has ended the chat.");
                    break;
                }
            }

            bufferedReader1.close();
            printWriter.close();
            outputStream.close();
            bufferedReader.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("An error occurred : " +e.getMessage());
        }
    }
}
