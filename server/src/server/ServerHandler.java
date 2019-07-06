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

    /**
     * Handle the request of client and give back a response
     * @param message request of client
     * @throws IOException
     * @throws ClassNotFoundException
     */
    void handle(Message message) throws IOException, ClassNotFoundException {
        switch (message.getRequestType()) {
            case login:
                User user2 = (User) inputStream.readObject();
                DataOutputStream dataOutputStream2 = new DataOutputStream(socket.getOutputStream());
                String response2 = checkLogin(user2.getUsername(),user2.getPassword());
                dataOutputStream2.writeUTF(response2);
                break;

            case forget_password:
                User u= (User) inputStream.readObject();
                DataOutputStream dOpS = new DataOutputStream(socket.getOutputStream());
                String re = checkForget(u.getUsername(),u.getBestFriend());
                dOpS.writeUTF(re);
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

    private String checkForget(String username, String bestFriend) {
        for (User user:AllUsers.getAll_Users()) {
            if(user.getBestFriend()==null)
                return "You can not recovery you password";
            if(user.getUsername().equals(username)){
                if(user.getBestFriend().equals(bestFriend))
                    return "Ok";
                else
                    return "Answer is wrong";
            }
        }
        return "Username does not exist";
    }

    private String checkLogin(String username,String password) {
        for (User user:AllUsers.getAll_Users()) {
            if(user.getUsername().equalsIgnoreCase(username)){
                if(user.getPassword().equals(password))
                    return "Ok";
                else
                    return "Password is wrong";
            }
        }
        return "Username does not exist";
    }

    /**
     * To check that username has been taken or not
     * @param username
     * @return
     */
    private String checkUsername(String username) {
        for (User user :AllUsers.getAll_Users()) {
            if(user.getUsername().equalsIgnoreCase(username))
                return "Username error";
        }
        return "Ok";
    }
}