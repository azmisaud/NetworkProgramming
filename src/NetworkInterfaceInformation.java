import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;

public class NetworkInterfaceInformation {
    public static void main(String[] args) {
        try {
            Enumeration<NetworkInterface> interfaceEnumeration = NetworkInterface.getNetworkInterfaces();

            while(interfaceEnumeration.hasMoreElements()) {
                NetworkInterface networkInterface=interfaceEnumeration.nextElement();
                System.out.println("Interface Name : " +networkInterface.getName());
                System.out.println("Display Name : " +networkInterface.getDisplayName());

                Enumeration<InetAddress> inetAddresses=networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()){
                    InetAddress inetAddress=inetAddresses.nextElement();
                    System.out.println("IP Address : " +inetAddress.getHostAddress());
                }

                System.out.println("MAC Address : " + Arrays.toString(networkInterface.getHardwareAddress()));
                System.out.println("Is Up : " +networkInterface.isUp());
                System.out.println("Is Loopback : " +networkInterface.isLoopback());
            }
        } catch (SocketException e){
            System.err.println("An error occurred : " +e.getMessage());
        }
    }
}
