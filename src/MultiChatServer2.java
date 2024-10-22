import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiChatServer2 {
    private static Map<String,MultiClientHandler> clients=new ConcurrentHashMap<>();
    private static ExecutorService executorService= Executors.newCachedThreadPool();
    private static final int PORT=1234;

    private static class MultiClientHandler implements Runnable {
        private Socket socket;
        private String username;

        public MultiClientHandler(Socket socket){
            this.socket=socket;
        }

        @Override
        public void run() {
            try (BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true)) {

                printWriter.println("Enter your username : ");
                username=reader.readLine();

                while(clients.containsKey(username)){
                    printWriter.println("Username already take, try another : ");
                    username=reader.readLine();
                }

                clients.put(username,this);
                printWriter.println("Welcome " +username + "! You can now send messages.");

                String message;

                while((message=reader.readLine())!=null) {
                    if(message.startsWith("@")){
                        String[] parts=message.split(" ",2);
                        String targetUser = parts[0].substring(1);
                        String messageToSend=parts.length > 1 ? parts[1] : "";

                        if(clients.containsKey(targetUser)){
                            clients.get(targetUser).sendMessage(username+ ": " +messageToSend);
                        } else {
                            printWriter.println("User : " +targetUser+ " not found.");
                        }
                    } else {
                        broadcastMessage(username+": "+message);
                    }
                }
            } catch (IOException e){
                System.err.println("An error occurred : " +e.getMessage());
            } finally {
                clients.remove(username);
                try {
                    if(socket!=null)
                        socket.close();
                } catch (IOException e){
                    System.err.println("An error occurred : " +e.getMessage());
                }
            }
        }

        private void sendMessage(String message){
            try(PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true)){
                printWriter.println(message);
            } catch (IOException e){
                System.err.println("An error occurred : " +e.getMessage());
            }
        }

        private void broadcastMessage(String message){
            clients.forEach((clientName,clientHandler) -> {
                if(!clientName.equals(username))
                    clientHandler.sendMessage(message);
            });
        }
    }

    public static void main(String[] args) {
        try(ServerSocket serverSocket=new ServerSocket(PORT)){
            System.out.println("Server started on port " +PORT);
            while (true){
                Socket socket=serverSocket.accept();
                System.out.println("New client connected.");
                executorService.execute(new MultiClientHandler(socket));
            }
        } catch (IOException e){
            System.err.println("An error occurred : " +e.getMessage());
        }
    }
}
