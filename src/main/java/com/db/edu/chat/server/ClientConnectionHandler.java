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
	
	private final Socket clientSocket;
	private final Collection<Socket> clientsSockets;

	public ClientConnectionHandler(Socket clientSocket, Collection<Socket> clientsSockets) throws IOException {
		this.clientSocket = clientSocket;
		this.clientsSockets = clientsSockets;
	}

	
	public void run() {
		while(true) {
			try {
				BufferedReader socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));;
				String message = socketReader.readLine();
				if(message == null) break;

				logger.info("Message from client "
						+ clientSocket.getInetAddress() + ":"
						+ clientSocket.getPort() + "> "
						+ message);

				for(Socket clientSocket : clientsSockets) {
					try {
						if(clientSocket.isClosed()) continue;
						if(!clientSocket.isBound()) continue;
						if(!clientSocket.isConnected()) continue;
						if(clientSocket == this.clientSocket) continue;

                        logger.info("Writing message "
                            + message
                            + " to socket "
                            + clientSocket);

                        BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
						socketWriter.write(message);
						socketWriter.newLine();
						socketWriter.flush();
					} catch (IOException e) {
						logger.debug("Error writing message " + message + " to socket " + clientSocket + ". Closing socket", e);
						try {
							clientSocket.close();
						} catch (IOException innerE) {
							logger.debug("Error closing socket ", innerE);
						}
						clientsSockets.remove(clientSocket);
					}
				}
			} catch (IOException e) {
				logger.error("Network reading message from socket " + clientSocket, e);
			}

		}
	}
}
