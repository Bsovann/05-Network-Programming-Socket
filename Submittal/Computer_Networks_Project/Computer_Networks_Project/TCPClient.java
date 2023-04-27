import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {
        try {
            // Connect to the server on port
            Socket socket = new Socket("localhost", Integer.parseInt(args[0]));

            // Get input and output streams for the socket
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            // Read buffer for solution from server, print writer to send expr
            BufferedReader read = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter write = new PrintWriter(outputStream, true);
            // To read input from client
            Scanner sc = new Scanner(System.in);
            
            System.out.print(read.readLine());
            String name = sc.nextLine();
            write.println(name);
            System.out.println(read.readLine());

            while (true) {
                // Read the response from server
                System.out.println();
                System.out.print(read.readLine());

                // Send a expr to the server
                String expr = sc.nextLine().trim();

                if (expr.toLowerCase().equals("e")) {
                    write.println(expr);
                    break;
                } else
                    write.println(expr);

                System.out.print(read.readLine());
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