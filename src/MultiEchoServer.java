import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiEchoServer {
    private static final int PORT=12345;
    private static final int THREAD_POOL_SIZE=15;

    private static class EchoClientHandler implements Runnable {
        private Socket socket;

        public EchoClientHandler(Socket socket){
            this.socket=socket;
        }

        @Override
        public void run() {
            try(InputStream inputStream=socket.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                OutputStream outputStream=socket.getOutputStream();
                PrintWriter printWriter=new PrintWriter(outputStream,true)) {

                String message;
                while((message= bufferedReader.readLine())!=null){
                    System.out.println("Received : " +message);
                    printWriter.println("Echo : " +message);
                }
            } catch (IOException e){
                System.err.println("An error occurred : " +e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e){
                    System.out.println("Error closing socket : " +e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        try(ExecutorService executorService= Executors.newFixedThreadPool(THREAD_POOL_SIZE);
            ServerSocket serverSocket=new ServerSocket(PORT)){

            System.out.println("Server is listening on port : " +PORT);

            while (true){
                Socket socket=serverSocket.accept();
                System.out.println("New client connected.");

                executorService.submit(new EchoClientHandler(socket));
            }
        } catch (IOException e){
            System.err.println("An error occurred : " +e.getMessage());
        }
    }
}
