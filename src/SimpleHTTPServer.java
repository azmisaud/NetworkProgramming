import java.io.*;
import java.net.*;

public class SimpleHTTPServer {

    public static void main(String[] args) {
        int port = 8080;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("HTTP Server started on port " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    handleClientRequest(clientSocket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClientRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream())) {

            // Read the HTTP request line
            String requestLine = in.readLine();
            System.out.println("Request: " + requestLine);

            // Check if the request is a GET request to the "/hello" endpoint
            if (requestLine != null && requestLine.startsWith("GET /hello ")) {
                // Send HTTP response header
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/plain");
                out.println("Content-Length: 13");  // Length of "Hello, World!"
                out.println();

                // Send the response body
                out.println("Hello, World!");
            } else {
                // Send a 404 Not Found response for other endpoints
                out.println("HTTP/1.1 404 Not Found");
                out.println("Content-Type: text/plain");
                out.println("Content-Length: 9");  // Length of "Not Found"
                out.println();
                out.println("Not Found");
            }

            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
