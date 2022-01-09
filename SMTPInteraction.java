import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Open an SMTP connection to mailserver and send one mail.
 *
 */
public class SMTPInteraction {
    /* Socket to the server */
    private Socket connection;

    /* Streams for reading from and writing to socket */
    private BufferedReader fromServer;
    private DataOutputStream toServer;

    private static final String CRLF = "\r\n";

    /* Are we connected? Used in close() to determine what to do. */
    private boolean isConnected = false;

    /* Create an SMTPInteraction object. Create the socket and the
       associated streams. Initialise SMTP connection. */
    public SMTPInteraction(EmailMessage mailmessage) throws IOException {
        // Open a TCP client socket with hostname and portnumber specified in
        // mailmessage.DestHost and  mailmessage.DestHostPort, respectively.
        //mailmessage.DestHost = "localhost";
	   // mailmessage.DestHostPort = 10025;
        connection = new Socket(mailmessage.DestHost = "localhost", mailmessage.DestHostPort = 1025);

        // attach the BufferedReader fromServer to read from the socket and
        // the DataOutputStream toServer to write to the socket
      fromServer = new BufferedReader(new InputStreamReader(System.in));
	  toServer = new DataOutputStream(System.out);
	/* Read one line from server and check that the reply code is 220.
	   If not, throw an IOException. */
     String reply = fromServer.readLine();
	 System.out.println(reply);     
     if (!reply.startsWith("220")){
        	throw new IOException("220 Connection Failed.");
 

	/* SMTP handshake. We need the name of the local machine.
	   Send the appropriate SMTP handshake command. */
	  //String localhost = InetAddress.getLocalHost().getHostName();
	      sendCommand(InetAddress.getLocalHost().getHostname();
        System.out.println("Local Host: " + localhost);
        sendCommand("HELO" + localhost + CRLF ,250);
       
       
        isConnected = true;
    }
    }

    /* Send message. Write the correct SMTP-commands in the
       correct order. No checking for errors, just throw them to the
       caller. */
    public void send (EmailMessage mailmessage) throws IOException {
    sendCommand("MAIL FROM "  + CRLF , 250);
    sendCommand("RCT TO "+ CRLF ,250);
    sendCommand("DATA" + CRLF , 250);
    }

        /* Send all the necessary commands to send a message. Call
	   sendCommand() to do the dirty work. Do _not_ catch the
	   exception thrown from sendCommand(). */


    /* Close SMTP connection. First, terminate on SMTP level, then
       close the socket. */
    public void close() {
isConnected = false;
try {
    sendCommand( "QUIT" + CRLF,221 );
    connection.close();
  }
   catch (IOException e) {
	  System.out.println("Unable to close connection: " + e);
	  isConnected = true;
	}
        }

    /* Send an SMTP command to the server. Check that the reply code is
       what is is supposed to be according to RFC 821. */
   private void sendCommand(String sendCommand, int rc) throws IOException {

	/* Write command to server and read reply from server. */
    toServer.writeBytes(sendCommand);
		System.out.println(sendCommand);//used for testing
		String serverReply = fromServer.readLine();
		System.out.println(serverReply);//used for testing
	 String	reply = (serverReply);
//parseReply
	/* Check that the server's reply code is the same as the parameter
	   rc. If not, throw an IOException. */
        if(serverReply != reply){
			    throw new IOException("Reply code mismatch.");

    }
   }
}
