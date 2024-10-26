import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class P2TCPClient {
    private static final String SERVER_ADDRESS="localhost";
    private static final int SERVER_PORT=1001;

    public static void main(String[] args) {
        try(Socket socket=new Socket(SERVER_ADDRESS,SERVER_PORT);
            InputStream inputStream=socket.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            OutputStream outputStream=socket.getOutputStream();
            PrintWriter printWriter=new PrintWriter(outputStream,true);
            Scanner scanner=new Scanner(System.in)){

            System.out.println("Connected to the Server..");
            System.out.println(bufferedReader.readLine());

            String response=scanner.nextLine();
            printWriter.println(response);

            if(response!=null){
                if(response.equalsIgnoreCase("yes")){
                    System.out.println(bufferedReader.readLine());

                    byte[] buffer=new byte[2048];
                    int bytesRead;

                    String filename=bufferedReader.readLine();

                    try(FileOutputStream fileOutputStream=new FileOutputStream("P2TCP"+filename)){
                        while((bytesRead=inputStream.read(buffer))!=-1)
                            fileOutputStream.write(buffer,0,bytesRead);
                    }
                    System.out.println(bufferedReader.readLine());
                } else if (response.equalsIgnoreCase("no")) {
                    System.out.println(bufferedReader.readLine());
                } else {
                    System.out.println(bufferedReader.readLine());
                }
            } else {
                System.out.println(bufferedReader.readLine());
            }
        } catch (IOException e){
            System.err.println("An error occurred : " +e.getMessage());
        }
    }
}
