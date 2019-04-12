/*
 *  Name:-Dhruvi Shah
 *  10015504023
 * Project - 1
 * -----------------------------------------------------
 * The program is a client server application that allows the user to download a file from the server.
 *
 * Steps to run the client on command line:-
 * 1. Open command prompt.
 * 2. Open the directiory where the project is stored.
 * 3. Compile the code using "javac WebClient.java"
 * 4. Execute the following command "java WebClient localhost 6789 Test.html".
 * 5. The program will then connect to the server and download the file if it exists and store the file in the downloads folder.
 *
 * Reference:
 * - Programming Assignment - 1 (Document provided on Blackboard)
 * - http://www.codejava.net/java-se/networking/use-httpurlconnection-to-download-file-from-an-http-url
 * 
 */
import java.io.*;
import java.net.* ;
import java.util.GregorianCalendar;

public class WebClient {

	public static Socket server;
	public static void main(String args[]) throws Exception {
		
		// Store the required server information from the command line.
		String host = args[0];
		int portNo = Integer.parseInt(args[1]);
		String Filename = args[2];
		
		//Establish a connection
		String fileURL = "http://"  + host + ":" + portNo + "/" + Filename;   
		
		//Open a connection
		URL url = new URL(fileURL);
		
		long finish = 0;
		long start = new GregorianCalendar().getTimeInMillis();
		HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
		
		
		
		//Get the response code
		int responseCode = httpconn.getResponseCode();
		
		System.out.println("ResponseCode: " + responseCode);
		
		//If the response code is "OK", download the file
		if(responseCode == HttpURLConnection.HTTP_OK) {
			System.out.println("File Exists");
			
			//Status and content message
			System.out.println("Status: " + httpconn.getResponseMessage());
			System.out.println("Content-Type: " + httpconn.getContentType());
			
			InputStream input = httpconn.getInputStream();
			
			FileOutputStream output = new FileOutputStream("C:/Users/Admin/Downloads/args[2]");
			
			//Download the file
			int bytesRead = -1;
            byte[] buffer = new byte[1024];
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            finish = new GregorianCalendar().getTimeInMillis();
            System.out.println("RTT: " + (finish - start + "ms"));

            System.out.println("File Copied");
            
    		//Close all the streams
            input.close();
            output.close();
		} else {
			System.out.println("File does not exist");
		}
		
		
	}
}
