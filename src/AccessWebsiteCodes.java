import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AccessWebsiteCodes {
    public static void main(String[] args) {
        String websiteURL="https://amu.ac.in";

        try {
            URL url=new URL(websiteURL);

            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content=new StringBuilder();

            while((inputLine=in.readLine())!=null){
                content.append(inputLine).append("\n");
            }

            in.close();

            System.out.println(content.toString());
        } catch (IOException e){
            System.out.println("An error occurred : " +e.getMessage());
        }
    }
}
