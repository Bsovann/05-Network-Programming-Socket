import java.net.*;
import java.text.Normalizer.Form;
import java.io.*;

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
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostName());

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

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            // Get input and output streams for the socket
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            // Read data from the client and write it
            // (bytesRead = inputStream.read(buffer))
            while (true) {
                // Create a buffer to read data from the client
                byte[] buffer = new byte[1024];
                int bytesRead;
                byte[] prompt = calInter();

                outputStream.write(prompt, 0, prompt.length);

                bytesRead = inputStream.read(buffer);

                if (bytesRead == -1)
                    continue;

                if (buffer.toString().equals("0"))
                    break;

                double result = Calculator.calculate(buffer.toString());
                byte[] sendRes = sendResult(result);

                outputStream.write(sendRes, 0, sendRes.length);
            }

            // Close the streams and socket when done
            inputStream.close();
            outputStream.close();
            clientSocket.close();
            System.out.println("Client disconnected: " + clientSocket.getInetAddress().getHostName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] calInter() {
        String str = "Please Enter the Math Expression to evaluate or 0 to exit: ";
        return str.getBytes();
    }

    private byte[] sendResult(double result) {
        String str = String.format("Your result is: %f\n", result);
        return str.getBytes();
    }

}