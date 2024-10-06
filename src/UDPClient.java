import java.io.IOException;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket=new DatagramSocket();

        String message="Hello, UDP Server!";
        byte[] buffer=message.getBytes();

        InetAddress serverAddress=InetAddress.getByName("127.0.0.1");
        DatagramPacket packet=new DatagramPacket(buffer, buffer.length,serverAddress,8080);

        socket.send(packet);

        byte[] responseBuffer=new byte[1024];
        DatagramPacket responsePacket=new DatagramPacket(responseBuffer, responseBuffer.length);

        socket.receive(responsePacket);
        String response=new String(responsePacket.getData(),0,responsePacket.getLength());
        System.out.println("Server response : " +response);

        socket.close();
    }
}
