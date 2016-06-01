package com.db.edu.chat.server;

import java.io.*;
import java.net.Socket;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusinessLogic   {

    private static final Logger logger = LoggerFactory.getLogger(ClientConnectionHandler.class);

    public static void exchangeLogic(Socket inputSocket, Collection<Socket> clientsSockets) throws IOException {
        String message = readMessage(inputSocket);
        /*MessageHandler messageHandler = new MessageHandler(inputSocket,clientsSockets);
        message = messageHandler.readMessage();*/
        if(message == null) return;

        logger.info("Message from client "
                + inputSocket.getInetAddress() + ":"
                + inputSocket.getPort() + "> "
                + message);

        for (Socket outSocket : clientsSockets) {
            try {
                if (outSocket.isClosed()) continue;
                if (!outSocket.isBound()) continue;
                if (!outSocket.isConnected()) continue;
                if (outSocket == inputSocket) continue;
                logger.info("Writing message " + message + " to socket " + outSocket);

                writeMessage(message, outSocket);
            } catch (IOException e) {
                logger.error("Error writing message " + message + " to socket " + outSocket + ". Closing socket", e);
                try {
                    outSocket.close();
                } catch (IOException innerE) {
                    logger.error("Error closing socket ", innerE);
                }

                logger.error("Removing connection " + outSocket);
                clientsSockets.remove(outSocket);
            }
        }

    }
    private static void writeMessage(String message, Socket outSocket) throws IOException {
        BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(outSocket.getOutputStream()));
        socketWriter.write(message);
        socketWriter.newLine();
        socketWriter.flush();
    }

    private static String readMessage(Socket inputSocket) throws IOException {
        BufferedReader socketReader = new BufferedReader(new InputStreamReader(inputSocket.getInputStream()));
        return socketReader.readLine();
    }
}
