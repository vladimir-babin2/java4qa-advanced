package com.db.edu.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {
	private static final Logger logger = LoggerFactory.getLogger(Server.class);
	
	public static final String HOST = "127.0.0.1";
	public static final int PORT = 4445;
	
	private final Collection<Socket> clientsSockets = new java.util.concurrent.CopyOnWriteArrayList<>();
	private volatile ServerSocket serverSocket;
	private volatile boolean stopFlag;

	private Thread connectionEventLoop = new Thread() {
		@Override
		public void run() {
			while(!isInterrupted()) {
				try {
					Socket clientSocket = getClientSocket();
					createClientConnectionHandler(clientSocket);
				} catch (SocketException e) {
					logger.debug("Intentionally closed socket: time to stop");
					break;
				} catch (IOException e) {
					logger.error("Network error", e);
					break;
				}
			}
		}
	};

	private Socket getClientSocket() throws IOException {
		Socket clientSocket = serverSocket.accept();
		logger.info("Client connected: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

		clientsSockets.add(clientSocket);
		return clientSocket;
	}

	private void createClientConnectionHandler(Socket clientSocket) throws IOException {
		Thread clientConnectionHandler = new Thread(new ClientConnectionHandler(clientSocket, clientsSockets));
		clientConnectionHandler.setDaemon(true);
		clientConnectionHandler.start();
	}

	public void start() throws ServerError {
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			throw new ServerError(e);
		}
		connectionEventLoop.start();
	}
	
	public void stop() throws ServerError {
		connectionEventLoop.interrupt();
		
		try { Thread.sleep(1000); } catch (InterruptedException e1) { } 
		
		try {
			serverSocket.close();
		} catch (IOException e) {
			throw new ServerError(e);
		}
	}
	
	public static void main(String... args) throws ServerError {
		new Server().start();
	}
}
