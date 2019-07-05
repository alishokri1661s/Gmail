package server;
import utility.AllUsers;
import utility.Message;
import utility.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerHandler {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    ServerHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public ObjectInputStream getInputStream() {return inputStream; }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    private static List<User> users = new ArrayList<>();

    void handle(Message message) throws IOException, ClassNotFoundException {
        switch (message.getRequestType()) {
            case login:

                break;
            case signup:
                User user = (User) inputStream.readObject();
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                String response = checkUsername(user.getUsername());
                dataOutputStream.writeUTF(response);
                if(response.equals("Username error"))
                    break;
                AllUsers.addUser(user);
                break;
        }
    }

    private String checkUsername(String username) {
        for (User user :AllUsers.getAll_Users()) {
            if(user.getUsername().equalsIgnoreCase(username))
                return "Username error";
        }
        return "Ok";
    }
}