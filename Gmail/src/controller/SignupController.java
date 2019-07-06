package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import main.Main;
import main.PageLoader;
import utility.Message;
import utility.RequestType;
import utility.User;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Random;

public class SignupController {
    @FXML
    TextField username, firstname, lastname, phonenumber, bestfriend;
    @FXML
    PasswordField password;
    @FXML
    DatePicker birthday;
    @FXML
    Label error;
    @FXML
    Pane otherSigninPane;
    @FXML
    RadioButton selectMale, selectFemale;
    @FXML
    ImageView image;


    /**
     * Prepare sign up request
     * @param actionEvent
     * @throws IOException
     */
    public void signup(ActionEvent actionEvent) throws IOException {
        if(checkValid()) {
            User user = new User(firstname.getText(), lastname.getText(),
                    username.getText(), password.getText(), birthday.getEditor().getText());
            if (!bestfriend.getText().equals("") && bestfriend.getText()!=null)
                user.setBestFriend(bestfriend.getText());
            Message message = new Message(RequestType.signup);
            Main.output.writeObject(message);
            Main.output.writeObject(user);
            DataInputStream dataInputStream = new DataInputStream(Main.socket.getInputStream());
            String response = dataInputStream.readUTF();
            if (response.equals("Username error")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username has been taken");
                alert.showAndWait();
            }
            else {
                otherSigninPane.setVisible(true);
                Main.user=user;
                dataInputStream.close();
            }
        }
    }




    private boolean checkValid() {
        if(firstname.getText().equals("") || lastname.getText().equals("") || username.getText().equals("")
            || password.getText().equals("") || birthday.getEditor().getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Complete all fields");
            alert.showAndWait();
            return false;
        }
        if (!password.getText().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$")
            || (!username.getText().matches("[a-zA-Z1-9.]+"))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Username or password is invalid");
            alert.showAndWait();
            return false;
        }

        //find age of user
        String[] dat = birthday.getEditor().getText().split("/");
        LocalDate birthDay = LocalDate.of(Integer.valueOf(dat[2]), Integer.valueOf(dat[0]), Integer.valueOf(dat[1]));
        LocalDate now = LocalDate.now();
        int age = Period.between(birthDay, now).getYears();
        //check age of uer
        if (age < 13) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Your age is below 13");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("../view/login.fxml");
    }

    public void femaleClicked(MouseEvent mouseEvent) {
        Platform.runLater(()->selectMale.setSelected(false));
    }

    public void maleClicked(MouseEvent mouseEvent) {
        Platform.runLater(()->selectFemale.setSelected(false));
    }

    /**
     * To choose an image from our files
     * @param mouseEvent
     * @throws IOException
     */
    public void chooseImage(MouseEvent mouseEvent) throws IOException {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(Main.stage);
            if (selectedFile != null) {
                image.setImage(new Image(selectedFile.toURI().toString()));
                image.setClip(new Circle(75,75,75));
            }
    }

    public void doneSignup(ActionEvent actionEvent) {
        if (selectFemale.isSelected())
            Main.user.setSex(false);
        Main.user.setPhoneNumber(phonenumber.getText());

    }


    /**
     * Suggests a username
     * @param actionEvent
     */
    public void usernameSuggestion(ActionEvent actionEvent) {
        String string = "";
        Random rand = new Random();
        try {
            int lengthname = firstname.getText().length();
            String name = firstname.getText().substring(0 , rand.nextInt(lengthname) + 1);
            int lengthlastname = lastname.getText().length();
            String lname = lastname.getText().substring(0 , rand.nextInt(lengthlastname)+1);
            string = name.toUpperCase();
            if (rand.nextBoolean())
                string += ".";
            string += lname.toLowerCase();
        } catch (Exception e) {
        }
        while (string.length() < 8) {
            string += rand.nextInt(10);
        }
        string += "1";
        username.setText(string);
    }
}
