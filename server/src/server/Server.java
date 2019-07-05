package server;

import utility.AllUsers;
import utility.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    public static final int requestPort = 2001;
    public static final String serverIP = "localhost";
    private static ServerSocket serverSocket;


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        AllUsers.init();
        Server.start();
    }

    public static void start() {
        try {
            serverSocket = new ServerSocket(requestPort);
            Thread serverThread = new Thread(new Server(), "Server Thread");
            serverThread.start();
        } catch (IOException e) {
            // ignore it
        }
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            try {
                new Thread(new ServerRunner(serverSocket.accept()), "Server Runner").start();
            } catch (IOException e) {
                // ignore it
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

class ServerRunner implements Runnable {
    private Socket serverSocket;
    private ServerHandler serverHandler;


    public ServerRunner(Socket serverSocket) throws IOException, ClassNotFoundException {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        Message clientRequest = null;
        try {
            serverHandler = new ServerHandler(serverSocket);
            while (true) {
                clientRequest = (Message) serverHandler.getInputStream().readObject();
                serverHandler.handle(clientRequest);
            }
        } catch (IOException | ClassNotFoundException e) {
        } finally {
            userDisconnect();
        }
    }


    private void userDisconnect() {
        try {
            serverHandler.getOutputStream().close();
            serverHandler.getInputStream().close();
        } catch (IOException e) {
            // ignore it
        }
    }
}