import java.net.InetAddress;
import java.net.UnknownHostException;

public class ObtainingHostInformation {
    public static void main(String[] args) {
        try {
            InetAddress localHost=InetAddress.getLocalHost();
            System.out.println("Local Host Name : " +localHost.getHostName());
            System.out.println("Local IP Address : " +localHost.getHostAddress());

            InetAddress google=InetAddress.getByName("www.google.com");
            System.out.println("Google Host Name : " +google.getHostName());
            System.out.println("Google IP Address : " +google.getHostAddress());

            InetAddress[] addresses=InetAddress.getAllByName("www.google.com");
            for (InetAddress address : addresses)
                System.out.println("Google IP : " +address.getHostAddress());
        } catch (UnknownHostException e){
            System.err.println("An error occurred : " +e.getMessage());
        }
    }
}
