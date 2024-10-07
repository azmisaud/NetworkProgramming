import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket){
        this.clientSocket=socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;

            while ((message = in.readLine()) != null) {
                System.out.println("Received : " + message);
                MultiChatServer.broadcastMessage(message, this);
            }
        } catch (IOException e) {
            System.out.println("An error occurred : " +e.getMessage());
        } finally {
            try {
                MultiChatServer.removeClient(this);
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e){
                System.out.println("An error occurred : " +e.getMessage());
            }
        }
    }

    public void sendMessage(String message){
        out.println(message);
    }
}

public class MultiChatServer {
    private static Set<ClientHandler> clientHandlers=new HashSet<>();

    public static void broadcastMessage(String message,ClientHandler sender){
        for (ClientHandler client : clientHandlers) {
            if(client!=sender)
                client.sendMessage(message);
        }
    }

    public static void removeClient(ClientHandler clientHandler){
        clientHandlers.remove(clientHandler);
        System.out.println("A client has disconnected.");
    }

    public static void main(String[] args) {
        ServerSocket serverSocket=null;
        try {
            serverSocket=new ServerSocket(1000);
            System.out.println("Server started on port 12345.");

            while (true) {
                Socket clientSocket=serverSocket.accept();
                System.out.println("New client connected.");

                ClientHandler clientHandler=new ClientHandler(clientSocket);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e){
            System.out.println("An error occurred : " +e.getMessage());
        } finally {
            if (serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.out.println("An error occurred : " +e.getMessage());
                }
            }
        }
    }
}
