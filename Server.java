import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// The server computes  monthly payment and total payment,
// and sends them back to the client.

public class Server {

  public static void main(String[] args) {
    try {
      //===================================================================
      // THE SERVER STARTS TO LISTEN ON PORT 7654 FOR INPUT FROM A CLIENT
      //===================================================================
      System.out.println("Waiting for a connection on port 7654.");
      ServerSocket serverSock = new ServerSocket(7654);
      Socket conn = serverSock.accept();
      System.out.println("Connection made, waiting for client.");
      //===================================================================
      // READ THE INPUT FROM THE CLIENT
      //===================================================================
      DataInputStream in = new DataInputStream(conn.getInputStream());

      double annulaInterestRate = in.readDouble();
      int numberYears = in.readInt();
      double loanAmount = in.readDouble();
      //===================================================================
      // SET THE OUTPUT TO SEND TO THE CLIENT WITH THE COMPUTED MONTLY MORGAGE
      //===================================================================
      DataOutputStream out = new DataOutputStream(conn.getOutputStream());

      double monthlyPayment = calculateMonthlyPayment(
        loanAmount,
        numberYears,
        annulaInterestRate
      );
      double totalPayment = monthlyPayment * (12 * numberYears);

      out.writeDouble(monthlyPayment);
      out.writeDouble(totalPayment);
      //===================================================================
      // set a debug log message
      //===================================================================
      System.out.println("\nSERVER LOGS:\n");
      String messageInputValues =
        "\nannulaInterestRate: " +
        annulaInterestRate +
        "\nnumberYears: " +
        numberYears +
        "\nloanAmount: " +
        loanAmount;
      System.out.println("Received values: " + messageInputValues);

      String messageOutputValues =
        "\nMonthly Payment: " +
        monthlyPayment +
        "\nTotal Payment: " +
        totalPayment;
      System.out.println("Sent values: " + messageOutputValues);
      //===================================================================
      // CLOSE ALL CONNECTIONS
      //===================================================================
      out.close();
      conn.close();
      serverSock.close();
      //===================================================================
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  /// DISCLAIMER: This block of code was taken from the website
  /// : https://learn-java-by-example.com/java/monthly-payment-calculator
  public static double calculateMonthlyPayment(
    double loanAmount,
    int termInYears,
    double interestRate
  ) {
    // Convert interest rate into a decimal
    // eg. 6.5% = 0.065

    interestRate /= 100.0;

    // Monthly interest rate
    // is the yearly rate divided by 12

    double monthlyRate = interestRate / 12.0;

    // The length of the term in months
    // is the number of years times 12

    int termInMonths = termInYears * 12;

    // Calculate the monthly payment
    // Typically this formula is provided so
    // we won't go into the details

    // The Math.pow() method is used calculate values raised to a power

    double monthlyPayment =
      (loanAmount * monthlyRate) /
      (1 - Math.pow(1 + monthlyRate, -termInMonths));

    return monthlyPayment;
  }
}
