package com.db.edu.chat.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Socket;

import org.junit.Ignore;
import org.junit.Test;

public class ChatServerAdminTest {
	private Server testServer;
	
	@Test @Ignore
	public void shouldListenPortWhenStarted() throws ServerError {
		testServer = new Server();
		testServer.start();
		try {
			new Socket(Server.HOST, Server.PORT);
		} catch (IOException e) {
			fail(e.getMessage());
		} finally {
			testServer.stop();			
		}
	}

	@Test(expected=IOException.class) @Ignore
	public void shouldReleasePortWhenStopped() throws ServerError, IOException {
		testServer = new Server();
		testServer.start();
		testServer.stop();

		Socket testSocket = new Socket(Server.HOST, Server.PORT);
		testSocket.close();
	}
}
