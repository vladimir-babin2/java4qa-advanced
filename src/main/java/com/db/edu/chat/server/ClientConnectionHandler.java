package com.db.edu.chat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientConnectionHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(ClientConnectionHandler.class);
	
	private final Socket inSocket;
	private final Collection<Socket> clientsSockets;
	private BusinessLogic businessLogic;

	public ClientConnectionHandler(
		Socket clientSocket,
		Collection<Socket> clientsSockets,
		BusinessLogic businessLogic) throws IOException {

		this.inSocket = clientSocket;
		this.clientsSockets = clientsSockets;
		this.businessLogic = businessLogic;
	}

	
	public void run() {
		while(true) {
			try {
				businessLogic.handle();
			} catch (IOException e) {
				logger.error("Network reading message from socket " + inSocket, e);
				try {
					inSocket.close();
				} catch (IOException innerE) {
					logger.debug("Error closing socket ", innerE);
				}

				logger.error("Removing socket and stop this handler thread");
				clientsSockets.remove(inSocket);
				return;
			}

		}
	}
}
