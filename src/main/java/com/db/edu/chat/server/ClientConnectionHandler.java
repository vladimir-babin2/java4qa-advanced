package com.db.edu.chat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientConnectionHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(ClientConnectionHandler.class);
	
	private final Socket inputSocket;
	private final Collection<Socket> clientsSockets;

	public ClientConnectionHandler(Socket clientSocket, Collection<Socket> clientsSockets) throws IOException {
		this.inputSocket = clientSocket;
		this.clientsSockets = clientsSockets;
	}

	
	public void run() {
		while(true) {
			try {
				BusinessLogic.exchangeLogic(inputSocket, clientsSockets);

			} catch (IOException e) {
				logger.error("Network reading message from socket " + inputSocket, e);
				try {
					inputSocket.close();
				} catch (IOException innerE) {
					logger.debug("Error closing socket ", innerE);
				}

				logger.error("Removing socket and stop this handler thread");
				clientsSockets.remove(inputSocket);
				return;
			}

		}
	}




}
