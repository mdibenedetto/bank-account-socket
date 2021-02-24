import java.io.*;
import java.net.*;

/*
    The client sends loan information
    (
        annual interest rate (first digit of your student id),
        number of years (2nd digit of your studentid),
        and loan amount = last four digits of your studentID (x12345678)
    ) to the server.
*/
public class Client {

  public static void main(String[] args) {
    //===================================================================
    // Set input params to send to server
    //===================================================================
    double annulaInterestRate = 1;
    int numberYears = 9;
    double loanAmount = 9141;
    // set as default server domain "localhost" with port 7654
    // the server will have set the same port number
    String serverName = "localhost";
    int port = 7654;

    try {
      //===================================================================
      // START CONNECTION TO SERVER
      //===================================================================
      System.out.println("Connecting to " + serverName + " on port " + port);
      Socket client = new Socket(serverName, port);
      System.out.println(
        "Just connected to " + client.getRemoteSocketAddress()
      );
      //===================================================================
      // SET OUTPUT TO SEND TO THE SERVER TO CALCULATE THE MONTHLY PAYMENT
      //===================================================================
      OutputStream outToServer = client.getOutputStream();
      DataOutputStream out = new DataOutputStream(outToServer);

      out.writeDouble(annulaInterestRate);
      out.writeInt(numberYears);
      out.writeDouble(loanAmount);
      //===================================================================
      // CAPTURE THE ANSWER OF THE SERVER
      //===================================================================
      InputStream inFromServer = client.getInputStream();
      DataInputStream in = new DataInputStream(inFromServer);

      System.out.println("Server responds: ");
      System.out.println("Monthly Payment: " + in.readDouble());
      System.out.println("Total Payment: " + in.readDouble());
      //===================================================================
      client.close();
      //===================================================================
    } catch (java.net.ConnectException ex) {
      System.out.println("\nClient Error: " + ex.getMessage());
      System.out.println(
        "\nIt looks that the server is NOT ready yet." +
        "\nPlease check that server domain '" +
        serverName +
        "' on the PORT number '" +
        port +
        "' is reachable.\n"
      );
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
