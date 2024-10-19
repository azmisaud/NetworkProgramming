import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiFileServer {
    private static final int PORT=8080;

    public static void main(String[] args) {
        ExecutorService pool= Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket=new ServerSocket(PORT)) {
            System.out.println("System is listening on port : " +PORT);

            while(true) {
                Socket socket=serverSocket.accept();
                pool.execute(new MultiClientHandler(socket));
            }
        } catch (IOException e){
            System.err.println("Server exception : " +e.getMessage());
        }
    }

    private static class MultiClientHandler implements Runnable{
        private Socket clientSocket;

        public MultiClientHandler(Socket socket){
            this.clientSocket=socket;
        }

        @Override
        public void run() {
            try(BufferedReader in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                OutputStream out=clientSocket.getOutputStream();
                PrintWriter printWriter=new PrintWriter(out,true)) {

                printWriter.println("Welcome to the file server.");
                printWriter.println("Enter the filename you want (.html,.txt,.jpg) only or type 'message' to get a custom message : ");

                String clientInput = in.readLine();

                if (clientInput != null){
                    clientInput=clientInput.trim();

                    if(clientInput.equalsIgnoreCase("message")){
                        printWriter.println("This is a custom message from the server.");
                    } else {
                        sendFile(out,clientInput,printWriter);
                    }
                }
            } catch (IOException e){
                System.err.println("Client error : " +e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e){
                    System.err.println("Error closing client socket : " +e.getMessage());
                }
            }
        }
    }

    private static void sendFile(OutputStream out,String fileName, PrintWriter printWriter) throws IOException{
        File file=new File(fileName);

        if (file.exists() && file.isFile()) {
            printWriter.println("Starting file transfer.... File Size : " + file.length() + " bytes.");

            try (FileInputStream fileInputStream=new FileInputStream(file)){
                byte[] buffer=new byte[4096];
                int bytesRead;

                while((bytesRead = fileInputStream.read(buffer))!=-1){
                    out.write(buffer,0,bytesRead);
                }

                out.flush();
                printWriter.println("File transfer complete.");
            } catch (IOException e){
                printWriter.println("Error sending the file...");
                System.err.println("Error transferring the file : " +e.getMessage());
            }
        } else {
            printWriter.println("File not found : " +fileName);
        }
    }
}