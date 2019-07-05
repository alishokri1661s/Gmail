package utility;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AllUsers {

    private static final String USERS_FILE_URL = "src/store/users.ser";
    private static List<User> All_Users;

    public static void init() throws IOException, ClassNotFoundException {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(USERS_FILE_URL));
            All_Users = (List<User>) objectInputStream.readObject();
            objectInputStream.close();
        }catch(Exception e){
            All_Users = new ArrayList<>(); }

    }

    public static List<User> getAll_Users() {
        return All_Users;
    }

    public static void addUser(User user) throws IOException {
        try {
            All_Users.add(user);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(USERS_FILE_URL));
            objectOutputStream.writeObject(All_Users);
            objectOutputStream.close();
        }catch (Exception e){e.printStackTrace();}
    }
}
