package com.db.edu.chat.server;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

import org.junit.Test;

public class ChatServerAdminTest {
	private Server testServer;
	
	@Test 
	public void shouldListenPortWhenStarted() throws ServerError, IOException {
		testServer = new Server();
		testServer.start();
		try {
			new Socket(Server.HOST, Server.PORT);
		} finally {
			testServer.stop();			
		}
	}

	@Test(expected=ConnectException.class) 
	public void shouldReleasePortWhenStopped() throws ServerError, IOException {
		testServer = new Server();
		testServer.start();
		testServer.stop();

		Socket testSocket = new Socket(Server.HOST, Server.PORT);
		testSocket.close();
	}
}
