package echoserver;
import java.net.*;
import java.io.*;

public class EchoServer {
  public static final int portNumber = 6013;

  public static void main(String[] args) {
    try {
      // Start listening on the specified port
      ServerSocket sock = new ServerSocket(portNumber);

      // Run forever, which is common for server style services
      while (true) {
        // Wait until someone connects, thereby requesting a date
        Socket client = sock.accept();
        System.out.println("Connected:");

        // Construct a reader to grab info sent to the server
        BufferedReader clientReader = new BufferedReader(new InputStreamReader(client.getInputStream()));

        // Construct a writer so we can write to the socket, thereby
        // sending something back to the client.
        PrintWriter writer = new PrintWriter(client.getOutputStream(), true);

        // Grab information from the client and send it back
        String line;      
        while(!isClosed(clientReader)) {
          line = clientReader.readLine();
          writer.println(line);
        }

        // Close the client socket and reader since we're done.
        writer.close();
        clientReader.close();
        client.close();
      }
    // *Very* minimal error handling.
    } catch (IOException ioe) {
      System.out.println("We caught an unexpected exception");
      System.err.println(ioe);
    }
  }

  public static boolean isClosed(BufferedReader reader) {
    try {
      reader.mark(1);
      boolean result = (reader.read() == -1);
      reader.reset();
      return result;  
    }
    catch(IOException ioe) {
      System.out.println("We caught an ioe in the isClosed");
      return true;
    }
  }
}
