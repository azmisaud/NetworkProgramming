import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiImageServer {
    private static final int PORT=9000;

    private static class MultiImageHandler implements Runnable {
        private Socket clientSocket;

        public MultiImageHandler(Socket socket){
            this.clientSocket=socket;
        }

        @Override
        public void run() {
            try(BufferedReader in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                OutputStream out=clientSocket.getOutputStream();
                PrintWriter printWriter=new PrintWriter(out,true)) {

                printWriter.println("Welcome to the HTML Server.");
                printWriter.println("Do you want the image? Say Yes or No");

                String response=in.readLine();

                if (response!=null){
                    if(response.equalsIgnoreCase("yes")){
                        File file=new File("ghlib.jpg");

                        printWriter.println("Starting Image Transfer....");
                        printWriter.println("Image size : " +file.length() + " bytes.");

                        try(FileInputStream fileInputStream=new FileInputStream(file)){
                            byte[] buffer=new byte[4096];
                            int bytesRead;

                            while((bytesRead=fileInputStream.read(buffer))!=-1)
                                out.write(buffer,0,bytesRead);

                            out.flush();
                            printWriter.println("Image transfer completed...");
                        }
                    } else if (response.equalsIgnoreCase("no")) {
                        printWriter.println("Your response is No, Thank you..");
                    } else {
                        printWriter.println("Wrong response.");
                    }
                } else {
                    printWriter.println("Null response not accepted..");
                }
            } catch (IOException e){
                System.err.println("An error occurred : " +e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try(ExecutorService pool= Executors.newFixedThreadPool(10);
            ServerSocket serverSocket=new ServerSocket(PORT)){

            System.out.println("System is listening on port " +PORT);

            while(true){
                Socket socket=serverSocket.accept();
                pool.execute(new MultiImageHandler(socket));
            }
        } catch (IOException e){
            System.err.println("An error occurred : " +e.getMessage());
        }
    }
}
