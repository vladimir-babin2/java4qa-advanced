package com.db.edu.chat.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.db.edu.chat.server.Server;

public class Client {
	public static void main(String... args) throws IOException {
		Socket socket = new Socket(Server.HOST, Server.PORT);
		BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		final BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

		(new Thread() {
			@Override
			public void run() {
				while(true) {
					try {
						System.out.println(socketReader.readLine());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		while(true) {
			socketWriter.write(consoleReader.readLine());
			socketWriter.newLine();
			socketWriter.flush();
		}
	}
}
