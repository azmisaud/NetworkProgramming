import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class DateTimeClient {
    public static void main(String[] args) {
        Socket socket=null;
        DataInputStream dataInputStream=null;

        try {
            socket=new Socket("localhost",5643);
            System.out.println("Connected to the server.");

            dataInputStream =new DataInputStream(socket.getInputStream());
            String message=dataInputStream.readUTF();
            System.out.println("Server says : " +message);
        } catch (IOException e){
            System.out.println("An error occurred : " +e.getMessage());
        } finally {
            try {
                if (socket!=null)
                    socket.close();
                if(dataInputStream!=null)
                    dataInputStream.close();
            } catch (IOException e){
                System.out.println("An error occurred : " +e.getMessage());
            }
        }
    }
}
