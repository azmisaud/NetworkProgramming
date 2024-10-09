import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
    public static void main(String[] args) {
        try {
            String url="https://www.amu.ac.in";
            URL obj=new URL(url);

            HttpURLConnection connection=(HttpURLConnection) obj.openConnection();

            connection.setRequestMethod("GET");

            int responseCode= connection.getResponseCode();

            System.out.println("Response Code : " +responseCode);

            if (responseCode==HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response=new StringBuffer();

                while((inputLine=in.readLine())!=null)
                    response.append(inputLine);

                in.close();

                System.out.println("Webpage Content : \n" + response.toString());
            } else
                System.out.println("Request failed.");
        } catch (Exception e) {
            System.out.println("An error occurred : " +e.getMessage());
        }
    }
}
