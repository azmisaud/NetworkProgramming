import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MultiImageClient {
    private static final String SERVER_ADDRESS="localhost";
    private static final int SERVER_PORT=9000;

    public static void main(String[] args) {
        try(Socket socket=new Socket(SERVER_ADDRESS,SERVER_PORT);
            InputStream inputStream=socket.getInputStream();
            BufferedReader in=new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
            Scanner scanner=new Scanner(System.in)){

            System.out.println(in.readLine());
            System.out.println(in.readLine());

            String response=scanner.nextLine();
            out.println(response);

            if(response!=null){
                if(response.equalsIgnoreCase("yes")){
                    System.out.println(in.readLine());
                    System.out.println(in.readLine());

                    try(FileOutputStream fileOutputStream=new FileOutputStream("multiImageServer.jpg")){
                        byte[] buffer=new byte[4096];
                        int bytesRead;

                        while((bytesRead=inputStream.read(buffer))!=-1)
                            fileOutputStream.write(buffer,0,bytesRead);

                        System.out.println(in.readLine());
                    }
                } else if (response.equalsIgnoreCase("no")){
                    System.out.println(in.readLine());
                } else {
                    System.out.println(in.readLine());
                }
            } else {
                System.out.println(in.readLine());
            }
        } catch (IOException e){
            System.err.println("An error occurred : " +e.getMessage());
        }
    }
}
