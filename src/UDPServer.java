import java.io.IOException;
import java.net.*;

public class UDPServer {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket=new DatagramSocket(8080);
        byte[] buffer=new byte[1024];

        DatagramPacket packet=new DatagramPacket(buffer, buffer.length);

        System.out.println("Server is listening on port 8080");

        socket.receive(packet);
        String recieved=new String(packet.getData(),0, packet.getLength());
        System.out.println("Received : " +recieved);

        String response="Message received";
        byte[] responseData=response.getBytes();

        InetAddress clientAddress=packet.getAddress();
        int clientPort=packet.getPort();

        DatagramPacket responsePacket=new DatagramPacket(responseData, responseData.length,clientAddress,clientPort);
        socket.send(responsePacket);

        socket.close();
    }
}
