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
      OutputStream toServer = socket.getOutputStream();

      int keyboardInt;
      int recievedFromServer;
      // Send information to the server      
      while((keyboardInt = keyboardInput.read()) != -1) {
        toServer.write(keyboardInt);
        recievedFromServer = input.read();
        System.out.write(recievedFromServer);
      }
      // Tell the server when we're done reading
      toServer.flush();
      System.out.flush();
      socket.shutdownOutput();
      // Wait for the server to shutdown it's responses
      while((recievedFromServer = input.read()) != -1) {
        System.out.write(recievedFromServer);
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