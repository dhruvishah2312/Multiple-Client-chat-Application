/*
 *
 *  Name:-Dhruvi Shah
 *  10015504023
 * Project - 1
 * -----------------------------------------------------
 * The program is a client server application that allows the user to download a file from the server.
 * 
 * Steps to run the Server on command line:-
 * 1. Open Command Prompt.
 * 2. Open the directory where the project is stored.
 * 3. Compile the code using "javac WebServer.java"
 * 4. Execute the following command "java WebServer 6789"
 * 5. The program execution will start and the server will wait for a client to connect to.
 * 
 * Reference:
 * - Programming Assignment - 1 (Document provided on Blackboard)
 * - http://www.codejava.net/java-se/networking/use-httpurlconnection-to-download-file-from-an-http-url
 * 
 */
import java.io.* ;
import java.net.* ;
import java.util.* ;
public final class WebServer {
	
private static ServerSocket serverSocket;
public static Socket clientSocket;
public static void main(String args[]) throws Exception {
		int portNo = Integer.parseInt(args[0]);
		
		//Set the port number
		serverSocket = new ServerSocket(portNo);
		serverSocket.setSoTimeout(200000);
		
		while(true) {
			
			
			try {
				//Establish a connection
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				clientSocket = serverSocket.accept();
				
				//Produce HTTP service request and listen for a TCP connection
				HttpRequest request = new HttpRequest(clientSocket);
				Thread thread = new Thread (request);
				thread.start();
			} catch (Exception e) { //to catch the timeout exception
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
final class HttpRequest implements Runnable
{

	final static String CRLF = "\r\n";
	Socket socket;
	
	public HttpRequest(Socket socket) throws Exception {
		 this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			requestProcess();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void requestProcess() throws Exception {
		
		//Get a reference to the socket's input and output streams
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		//Get the request line of the HTTP message
		String request = br.readLine();
		
		System.out.println("Request: " + request);
		
		
		System.out.println("Client address: " + socket.getInetAddress());
		System.out.println("Client port: " + socket.getPort());
		System.out.println("Peer address of server: " + socket.getInetAddress() + " " + socket.getLocalPort());
		System.out.println("Peer address of client: " + socket.getInetAddress() + " " + socket.getPort());
		
		//Get and display the header lines
		String header = null;
		while ((header = br.readLine()).length() != 0) {
			System.out.println("HeaderLine: "+ header);
		}
		
		//Extract the filename from the request line.
		StringTokenizer tokens = new StringTokenizer(request);
		System.out.println("Token" + tokens);
		tokens.nextToken();
		String fileName = "." + tokens.nextToken();
		System.out.println("\nTokens:" + fileName);
		
		FileInputStream fis = null;
		boolean fileExists = true;
		
		//Open the requested file if it exists
		try {
				fis = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			fileExists = false;
			
		}
		
		//Construct the response message
		String statusLine = null;
		String contentTypeLine = null;
		String entityBody = null;
		
		if(fileExists) {
			statusLine = "HTTP/1.1 200 OK";
			contentTypeLine = "\nContent-Type: " + contentType(fileName) + CRLF;
		} else {
			statusLine = "HTTP/1.1 404 Not Found";
			contentTypeLine = " ";
			entityBody = "HTML>" + "<HEAD><TITLE>Not Found</TITLE></HEAD>" + "<BODY>Not Found</BODY></HTML";
		}
		
		out.writeBytes(statusLine);
		out.writeBytes(contentTypeLine);
		out.writeBytes(CRLF);
		
		if (fileExists) {
			sendBytes(fis, out);
			fis.close();
		} else {
			out.writeBytes(entityBody);
		}
		
		out.close();
		br.close();
		socket.close();
	}
	
	private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception {
		
		//Construct a buffer
		byte[] buffer = new byte[1024];
		int bytes = 0;

		//Copy the requested file into the output stream
		while((bytes = fis.read(buffer)) != -1 ) {
			os.write(buffer, 0, bytes);
		}
	}
	
	private static String contentType(String fileName) {
		if(fileName.endsWith(".htm") || fileName.endsWith(".html")) {
			return "text/html";
		}
		if(fileName.endsWith(".jpeg")) {
			return "image/jpeg";
		}
		if(fileName.endsWith(".gif")) {
			return "image/gif";
		}
		return "application/octet-stream";
	}
}