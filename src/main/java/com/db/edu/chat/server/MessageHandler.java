package com.db.edu.chat.server;

import java.io.*;
import java.net.Socket;
import java.util.Collection;

/**
 * Created by Student on 01.06.2016.
 */
public class MessageHandler {
    private final Socket inputSocket;
    private final Collection<Socket> clientsSockets;

    public MessageHandler(Socket clientSocket, Collection<Socket> clientsSockets) throws IOException {
        this.inputSocket = clientSocket;
        this.clientsSockets = clientsSockets;
    }

    public void writeMessage(String message, MessageHandler messageHandler) throws IOException {
        BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(outSocket.getOutputStream()));
        socketWriter.write(message);
        socketWriter.newLine();
        socketWriter.flush();
    }
    public String readMessage(Socket inputSocket) throws IOException {
        BufferedReader socketReader = new BufferedReader(new InputStreamReader(inputSocket.getInputStream()));
        return socketReader.readLine();
    }
}
