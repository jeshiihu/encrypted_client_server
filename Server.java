/**
 * Java Program: Server side of client server communication
 *
 * Code is based on the following online source
 * http://introcs.cs.princeton.edu/java/84network/EchoServer.java.html
 */
import helper.FileIo;

import java.net.*;
import java.io.*;
import javax.crypto.*;
import java.nio.file.*;

public class Server
{
	static private int port;

	public static void main(String[] args)
	{
		if(args.length != 1)
		{
			System.err.println("Error: invalid arguements {port}");
			return;
		}

		try
		{
			port = Integer.parseInt(args[0]);
		}
		catch(NumberFormatException e)
		{
			System.err.println("Port needs to be a valid integer value");
			return;
		}

		try
		{
			ServerSocket sock = startConnection();
			if(sock == null)
			{
				System.err.println("Error: Server failed to connect to port " + Integer.toString(port));
				return;
			}

			System.out.println("Server successfully connected!");
			beginListening(sock);
		}
		catch(Exception e)
		{
			System.err.println("Error: unable to make socket connection");
		}
		// sock.close();
	}

	private static ServerSocket startConnection() throws Exception
	{
		ServerSocket sock = null;
		sock = new ServerSocket(port);

		return sock;
	}

	private static void beginListening(ServerSocket sock) throws Exception
	{
		while(true)
		{
			// a "blocking" call which waits until a connection is requested
			int clientPort;
			Socket clientSock = sock.accept();
			clientPort = clientSock.getPort();
			System.err.println("Accepted connection from " + Integer.toString(clientPort));
			CommThread comm = new CommThread(clientSock);
			comm.start();
		}
	}
}