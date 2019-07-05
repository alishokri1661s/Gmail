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
                /*FileOutputStream newUser = new FileOutputStream("src/store/users/" +
                        user.getUsername()+".ser");
                ObjectOutputStream newObjectUser = new ObjectOutputStream(newUser);
                newObjectUser.writeObject(user);*/
                AllUsers.addUser(user);
                break;
        }
    }
}