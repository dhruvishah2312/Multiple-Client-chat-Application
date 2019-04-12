Name:- DHRUVI SHAH
ID: 1001550403

-----------------------------------------------------
Project - 1

The program is a client server application that allows the user to download a file from the server.

Steps to run the Server on command line:-
1. Open Command Prompt.
2. Open the directory where the project is stored.
3. Compile the code using "javac WebServer.java"
4. Execute the following command "java WebServer 6789" or any port other than the used ones
5. The program execution will start and the server will wait for a client to connect to.

Steps to run the client on command line:-
1. Open command prompt.
2. Open the directiory where the project is stored.
3. Compile the code using "javac WebClient.java"
4. Execute the following command "java WebClient localhost 6789 Test.html".
5. The program will then connect to the server and download the file if it exists and store the file in the downloads folder.


Important: You might have to change the filepath for downloads incase it cant find the path where you want it to be saved.
Java file: WebClient.java Line:61

Reference:
- Programming Assignment - 1 (Document provided on Blackboard)
- http://www.codejava.net/java-se/networking/use-httpurlconnection-to-download-file-from-an-http-url

