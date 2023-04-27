import java.net.*;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer.Form;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TCPServer {

    public static void main(String[] args) {
        try {
            // Create a server socket on port 1234
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server started on port 1234");

            while (true) {
                // Wait for client connection
                System.out.println("Waiting for client connection...");
                Socket clientSocket = serverSocket.accept();

                // Create a new thread to handle the client
                Thread thread = new ClientHandler(clientSocket);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;
    // Client information format
    public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            // Get input and output streams for the socket
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();
            // To output to client
            BufferedReader read = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter write = new PrintWriter(outputStream, true);
            // Prompt server connection
            write.println("What is Your Name ? ");
            String name = read.readLine();
            System.out.printf("%s is connected! on %s", name, dtf.format(LocalDateTime.now()));
            write.println(name + ", You are connected, Successfully!");

            // Read data from the) client and write it
            while (true) {

                // Create a buffer to read data from the client
                write.println("Please Enter the Math Expression to evaluate or E to exit: ");
                String expr = read.readLine();

                if (expr.toLowerCase().equals("e"))
                    break;

                double result = 0.0;
                try {
                    System.out.printf("\n%s requested solution for %s", name, expr);
                    result = Calculator.calculate(expr);
                } catch (Exception e) {
                }

                write.println("Your result is: " + result);
            }

            // Close the streams and socket when done
            inputStream.close();
            outputStream.close();
            clientSocket.close();
            // Prompt disconnect
            System.out.printf("\n%s is disconnected on %s: %s", name, dtf.format(LocalDateTime.now()),
                    clientSocket.getInetAddress().getHostName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}