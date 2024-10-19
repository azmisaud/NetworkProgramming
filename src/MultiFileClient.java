import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MultiFileClient {
    private static final String SERVER_ADDRESS="localhost";
    private static final int SERVER_PORT=8080;

    public static void main(String[] args) {
        try (Socket socket=new Socket(SERVER_ADDRESS,SERVER_PORT);
             BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
             Scanner scanner=new Scanner(System.in)) {

            System.out.println("Connected to the server.");
            System.out.println(in.readLine());
            System.out.println(in.readLine());

            System.out.println("Enter file name or 'message' :");
            String request=scanner.nextLine();
            out.println(request);

            if(!request.equalsIgnoreCase("message")){
                receiveFile(socket,in,request);
            } else {
                String serverMessage=in.readLine();
                System.out.println("Server response : " +serverMessage);
            }
        } catch (IOException e){
            System.err.println("Client error : " +e.getMessage());
        }
    }

    private static void receiveFile(Socket socket,BufferedReader in,String fileName) throws IOException {
        String serverResponse=in.readLine();

        if(serverResponse.startsWith("Starting file transfer")){
            System.out.println(serverResponse);

            saveFile(socket.getInputStream(),fileName);

            String completionMessage=in.readLine();
            System.out.println(completionMessage);
        } else {
            System.out.println(serverResponse);
        }
    }

    private static void saveFile(InputStream inputStream,String fileName) throws IOException {

        try(FileOutputStream fileOutputStream=new FileOutputStream("multifile"+fileName)){
            byte[] buffer=new byte[4096];
            int bytesRead;

            while((bytesRead=inputStream.read(buffer))!=-1){
                fileOutputStream.write(buffer,0,bytesRead);
            }

            System.out.println("File saved.");
        }
    }
}
