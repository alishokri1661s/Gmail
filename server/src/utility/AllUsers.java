package utility;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AllUsers {

    private static final String USERS_FILE_URL = "src/resources/users.ser";
    private static List<User> All_Users;

    /**
     * Init all users
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void init() throws IOException, ClassNotFoundException {
        try {
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(new FileInputStream(USERS_FILE_URL));
            All_Users = (List<User>) objectInputStream.readObject();
            for (User user: All_Users) {
                System.out.println(user.getUsername()+" : "+user.getPassword());
            }
            objectInputStream.close();
        }catch(Exception e){
            All_Users = new ArrayList<>(); }

    }

    public static List<User> getAll_Users() {
        return All_Users;
    }

    /**
     * adds user to All_users list
     * @param user the user that we want to add
     * @throws IOException
     */
    public static void addUser(User user) throws IOException {
        try {
            All_Users.add(user);
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(new FileOutputStream(USERS_FILE_URL));
            objectOutputStream.writeObject(All_Users);
            objectOutputStream.close();
        }catch (Exception e){e.printStackTrace();}
    }
}
