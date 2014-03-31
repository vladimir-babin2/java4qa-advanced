package com.db.edu.chat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientConnectionHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(ClientConnectionHandler.class);
	
	private boolean stopFlag;
	private Socket clientSocket;
	private final Collection<Socket> clientsSockets;

	public ClientConnectionHandler(Socket clientSocket, Collection<Socket> clientsSockets) throws IOException {
		this.clientSocket = clientSocket;
		this.clientsSockets = clientsSockets;
	}

	
	public void run() {
		while(!stopFlag) {
			try {
				BufferedReader socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));;
				String message = socketReader.readLine();
				socketReader.readLine(); // skip newLine(); 
				logger.debug("Message from client" 
						+ clientSocket.getInetAddress() + ":" 
						+ clientSocket.getPort() + "> " 
						+ message);

				synchronized (clientsSockets) {
					for(Socket clientSocket : clientsSockets) {
						if(stopFlag) break;
						if(clientSocket.isClosed()) break;
						if(clientSocket.equals(this.clientSocket)) continue;
						
						BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
						socketWriter.write(message);
						socketWriter.newLine();
						socketWriter.flush();
					}
				}
				
			} catch (IOException e) {
				logger.error("Network error", e);
				break;
			}
		}
	}
}
