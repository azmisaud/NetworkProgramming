import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileHTTPServer {
    private static final int PORT=7000;

    public static void main(String[] args) {
        try(ServerSocket serverSocket=new ServerSocket(PORT)){
            System.out.println("File HTTP Server started on port : " +PORT);

            while (true){
                try(Socket socket=serverSocket.accept();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true)){

                    String requestLine=bufferedReader.readLine();
                    System.out.println("Request : " +requestLine);

                    if(requestLine!=null && requestLine.startsWith("GET /hello ")) {
                        File file=new File("homepage.html");

                        StringBuilder contentBuilder=new StringBuilder();
                        try(BufferedReader bufferedReader1=new BufferedReader(new FileReader(file))){
                            String line;
                            while((line=bufferedReader1.readLine())!=null)
                                contentBuilder.append(line).append("\n");
                        }

                        String htmlContent=contentBuilder.toString();

                        printWriter.println("HTTP/1.1 200 OK");
                        printWriter.println("Content-Type: text/html; charset=UTF-8");
                        printWriter.println("Content-Length: " +htmlContent.length());
                        printWriter.println();

                        printWriter.print(htmlContent);
                    } else {
                        String notFound="<html><body><h1>404 Not Found</h1></body></html>";
                        printWriter.println("HTTP/1.1 404 Not Found");
                        printWriter.println("Content-Type: text/html; charset=UTF-8");
                        printWriter.println("Content-Length: " +notFound.length());
                        printWriter.println();
                        printWriter.print(notFound);
                    }
                }
            }
        } catch (IOException e){
            System.err.println("An error occurred : " +e.getMessage());
        }
    }
}
