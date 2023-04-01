import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {
        try {
            // Connect to the server on port 1234
            Socket socket = new Socket("localhost", 1234);
            System.out.println("Connected to server");

            // Get input and output streams for the socket
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            while (true) {
                // Read the response from server
                byte[] buffer = new byte[1024];
                int bytesRead = inputStream.read(buffer);
                if (bytesRead == -1)
                    break;

                String response = new String(buffer, 0, bytesRead);
                System.out.print(response);

                // Send a message to the server
                String message = new Scanner(System.in).nextLine();
                outputStream.write(message.getBytes());
            }

            // Close the streams and socket when done
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
