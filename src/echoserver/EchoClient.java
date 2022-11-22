package echoserver;
import java.net.*;
import java.io.*;

public class EchoClient {
  public static final int portNumber = 6013;

  public static void main(String[] args) throws IOException {
    String server;
    // Use "127.0.0.1", i.e., localhost, if no server is specified.
    if (args.length == 0) {
      server = "127.0.0.1";
    } else {
      server = args[0];
    }

    try {
      // Connect to the server
      Socket socket = new Socket(server, portNumber);

      // Get the input stream so we can read from that socket
      InputStream keyboardInput = System.in;
      InputStream input = socket.getInputStream();
      OutputStream output = socket.getOutputStream();

      // Send information to the server      
      while(keyboardInput.available() != 0) {
        int keyboardInt = keyboardInput.read();
        output.write(keyboardInt);
        char c = (char) input.read();
        System.out.print(c);
        if((char)c == '\n') {
          output.flush();
        }
      }
      // Tell the server when we're done reading
      socket.shutdownOutput();
      // Wait for the server to shutdown it's responses
      while(input.available() != 0) {
        char c = (char) input.read();
        System.out.print(c);
      }
      // Then close the socket
      socket.close();


    // Provide some minimal error handling.
    } catch (ConnectException ce) {
      System.out.println("We were unable to connect to " + server);
      System.out.println("You should make sure the server is running.");
    } catch (IOException ioe) {
      System.out.println("We caught an unexpected exception");
      System.err.println(ioe);
    }
  }
}