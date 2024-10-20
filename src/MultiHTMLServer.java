import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiHTMLServer {
    private static final int PORT=8080;

    public static void main(String[] args) {
        try(ExecutorService pool=Executors.newFixedThreadPool(10);
            ServerSocket serverSocket=new ServerSocket(PORT)){

            System.out.println("System is listening on port : " +PORT);

            while(true) {
                Socket socket=serverSocket.accept();
                pool.execute(new MultiHTMLHandler(socket));
            }
        } catch (IOException e){
            System.err.println("An error occurred : " +e.getMessage());
        }
    }

    private static class MultiHTMLHandler implements Runnable {
        private Socket clientSocket;

        public MultiHTMLHandler(Socket socket){
            this.clientSocket=socket;
        }

        @Override
        public void run() {
            try(BufferedReader in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                OutputStream out=clientSocket.getOutputStream();
                PrintWriter printWriter=new PrintWriter(out,true)){

                printWriter.println("Welcome to the HTML Server");
                printWriter.println("Do you want the HTML file of LabTracker HomePage? [Yes/No] : ");

                String clientInput = in.readLine();

                if (clientInput != null) {
                    clientInput=clientInput.trim();

                    if(clientInput.equalsIgnoreCase("yes")){
                        File file=new File("homepage.html");
                        printWriter.println("Starting HTML transfer....");
                        printWriter.println("HTML file size : " +file.length() + " bytes.");

                        try(FileInputStream fileInputStream=new FileInputStream(file)){
                            byte[] buffer=new byte[4096];
                            int bytesRead;

                            while((bytesRead = fileInputStream.read(buffer))!=-1)
                                out.write(buffer,0,bytesRead);

                            out.flush();
                            printWriter.println("File Transfer Complete....");
                        }
                    } else if (clientInput.equalsIgnoreCase("No")){
                        printWriter.println("You have not asked for the HTML file.");
                    } else {
                        printWriter.println("Wrong Response.");
                    }
                } else {
                    printWriter.println("Null response not accepted...");
                }
            } catch (IOException e){
                System.err.println("An error occurred : " +e.getMessage());

            }
        }
    }
}
