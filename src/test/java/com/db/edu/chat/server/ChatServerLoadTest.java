package com.db.edu.chat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatServerLoadTest {
	public static void main(String... args) throws IOException {
		while(true) {
			ChatServerLoadTest.sleep(1);
			final Socket socket = new Socket(Server.HOST, Server.PORT);
			
			new Thread() {
				@Override
				public void run() {
					try {
						BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
						String message = Thread.currentThread().getName();
						
						while(true) {
							ChatServerLoadTest.sleep(1);
							socketWrite(socketWriter, message);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}.start();
			
			new Thread() {
				@Override
				public void run() {
					try {
						BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						while(true) {
							socketReader.readLine();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
	
	private static void socketWrite(BufferedWriter socketWriter, String text) throws IOException {
		socketWriter.write(text);
		socketWriter.newLine();
		socketWriter.flush();
	}
	
	private static void sleep(int seconds) {
		try { Thread.sleep(1000*seconds); } catch (InterruptedException e) { }
	}
}
