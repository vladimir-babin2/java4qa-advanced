package com.db.edu.chat.server;


import static com.db.edu.chat.server.TestUtils.sleep;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

import org.junit.Ignore;
import org.junit.Test;


public class ChatServerAdminTest {
	private Server testServer;
	
	@Test(timeout=3000)
	public void shouldListenPortWhenStarted() throws ServerError, IOException {
		testServer = new Server();
		testServer.start();
		sleep(300);
		
		try {
			new Socket(Server.HOST, Server.PORT);
		} finally {
			testServer.stop();			
		}
	}

	@Test(expected=ConnectException.class, timeout=3000)
	public void shouldReleasePortWhenStopped() throws ServerError, IOException {
		testServer = new Server();
		testServer.start();
		sleep(300);
		testServer.stop();

		Socket testSocket = new Socket(Server.HOST, Server.PORT);
		testSocket.close();
	}
}
