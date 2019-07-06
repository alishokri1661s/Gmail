package utility;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1;
    private static final String IMAGE_URL_ROOT = "src/resources/images/";
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String birthDay;
    private String imageName="unknown.png";
    private String phoneNumber;
    private String bestFriend=null;

    /** True means male*/
    private boolean sex=true;

    public User(String firstName, String lastName, String username, String password, String birthDay) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
    }

    public User (String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getBirthDay() { return birthDay; }

    public void setBirthDay(String birthDay) { this.birthDay = birthDay; }

    public String getImageUrl() {return IMAGE_URL_ROOT+imageName; }

    public void setImageName(String imageName) { this.imageName = imageName; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public boolean isMale() { return sex; }

    public void setSex(boolean sex) { this.sex = sex; }

    public String getBestFriend() { return bestFriend; }

    public void setBestFriend(String bestFriend) { this.bestFriend = bestFriend; }
}
