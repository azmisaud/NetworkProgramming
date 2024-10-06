import java.net.InetAddress;
import java.net.UnknownHostException;

public class DomainToIP {
    public static void main(String[] args) {
        String domain="www.amu.ac.in";

        try {
            InetAddress inetAddress=InetAddress.getByName(domain);

            String ipAddress=inetAddress.getHostAddress();

            System.out.println("IP Address of " +domain+ " is " +ipAddress);
        } catch (UnknownHostException e){
            System.out.println("An error occurred : " +e.getMessage());
        }
    }
}
