package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Main;
import utility.Message;
import utility.RequestType;
import utility.User;

import java.io.IOException;

public class SignupController {
    @FXML
    TextField username;
    @FXML
    PasswordField password;

    public void signup(ActionEvent actionEvent) throws IOException, InterruptedException {
        User user = new User(username.getText() , password.getText());
        Message message = new Message(RequestType.signup);
        Main.output.writeObject(message);
        Main.output.writeObject(user);
    }
}
