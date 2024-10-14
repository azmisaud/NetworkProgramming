import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class PracticeChatServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket=new ServerSocket(9001);
            System.out.println("Server is listening on port 9001");

            Socket socket=serverSocket.accept();
            System.out.println("Client Connected.");

            InputStream inputStream=socket.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

            OutputStream outputStream=socket.getOutputStream();
            PrintWriter printWriter=new PrintWriter(outputStream,true);

            BufferedReader bufferedReader1=new BufferedReader(new InputStreamReader(System.in));

            String clientMessage, serverMessage;

            while(true) {
                clientMessage=bufferedReader.readLine();
                if(clientMessage==null){
                    System.out.println("Client has disconnected.");
                    break;
                }
                System.out.println("Client : " +clientMessage);

                if (clientMessage.equalsIgnoreCase("bye")){
                    System.out.println("Client has ended the chat.");
                    break;
                }

                System.out.print("Server : ");
                serverMessage=bufferedReader1.readLine();
                printWriter.println(serverMessage);

                if (serverMessage.equalsIgnoreCase("bye")){
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
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("An error occurred : " +e.getMessage());
        }
    }
}
