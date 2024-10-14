import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class PracticeMultiChatClient {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public PracticeMultiChatClient(Socket socket,String username){
        try {
            this.socket=socket;
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username=username;
        } catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner=new Scanner(System.in);

            while(socket.isConnected()){
                String message=scanner.nextLine();
                bufferedWriter.write(username + " : " + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromGroupChat;
                while(socket.isConnected()){
                    try {
                        messageFromGroupChat=bufferedReader.readLine();
                        System.out.println(messageFromGroupChat);
                    } catch (IOException e){
                        closeEverything(socket,bufferedReader,bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter){
        try {
            if (bufferedReader!=null) bufferedReader.close();
            if (bufferedWriter!=null) bufferedWriter.close();
            if (socket!=null) socket.close();
        } catch (IOException e){
            System.out.println("An error occurred : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter your username for the group chat : ");
        String username=scanner.nextLine();

        try {
            Socket socket=new Socket("localhost",9002);
            PracticeMultiChatClient practiceMultiChatClient=new PracticeMultiChatClient(socket,username);
            practiceMultiChatClient.listenForMessage();
            practiceMultiChatClient.sendMessage();
        } catch (IOException e){
            System.out.println("An error occurred : " + e.getMessage());
        }
    }
}
