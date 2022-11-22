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

        // Construct input stream for info sent to server
        InputStream input = client.getInputStream();
        OutputStream output = client.getOutputStream();

        // Grab information from the stream and send it back
        int inputInt;
        while((inputInt = input.read()) != -1) {
          output.write(inputInt);
          System.out.print((char) inputInt);
          if((char)inputInt == '\n') {
            output.flush();
          }
        } 

        // Close the client socket and reader since we're done
        client.close();
      }
    // *Very* minimal error handling.
    } catch (IOException ioe) {
      System.out.println("We caught an unexpected exception");
      System.err.println(ioe);
    }
  }
}
