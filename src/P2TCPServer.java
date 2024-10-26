import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class P2TCPServer {
    private static final int PORT=1001;

    public static void main(String[] args) {
        try(ServerSocket serverSocket=new ServerSocket(PORT)){
            System.out.println("Server is listening on port " +PORT);

            try(Socket socket=serverSocket.accept();
                InputStream inputStream=socket.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                OutputStream outputStream=socket.getOutputStream();
                PrintWriter printWriter=new PrintWriter(outputStream,true)){
                System.out.println("Client Connected.");

                printWriter.println("Do you want the image file? Say yes or no.");
                String response=bufferedReader.readLine();

                if(response!=null){
                    if(response.equalsIgnoreCase("yes")){
                        printWriter.println("Starting image transfer...");

                        byte[] buffer=new byte[2048];
                        int bytesRead;

                        File file=new File("ghlib.jpg");
                        printWriter.println(file.getName());
                        try(FileInputStream fileInputStream=new FileInputStream(file)){
                            while((bytesRead=fileInputStream.read(buffer))!=-1)
                                outputStream.write(buffer,0,bytesRead);
                        }
                        printWriter.println("Image transfer completed.");
                    } else if (response.equalsIgnoreCase("No")){
                        printWriter.println("You have not requested for the image.");
                    } else {
                        printWriter.println("Invalid response, try again after some time.");
                    }
                } else {
                    printWriter.println("Null response not valid.");
                }
            }
        } catch (IOException e){
            System.err.println("An error occurred : " +e.getMessage());
        }
    }
}
