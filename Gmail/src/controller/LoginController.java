package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.Main;
import main.PageLoader;
import utility.Message;
import utility.RequestType;
import utility.User;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LoginController {

    @FXML
    Pane PaneConnect,forgetPane;
    @FXML
    TextField IpAddress , username,forgetUsername,forgetBestfriend;
    @FXML
    Label error;
    @FXML
    PasswordField password;


    public void connect(ActionEvent actionEvent) {
        error.setVisible(false);
        new Thread(() -> {
            try {
                error.setVisible(true);
                Platform.runLater(()->error.setText("Waiting to connect"));
                Socket socket = new Socket(IpAddress.getText() , Main.requestPort);
                Main.socket = socket;
                Main.output = new ObjectOutputStream(Main.socket.getOutputStream());
                Main.input = new ObjectInputStream(Main.socket.getInputStream());
                PaneConnect.setVisible(false);
            } catch (Exception e) { error.setVisible(true);
                Platform.runLater(()->error.setText("Unable to connect"));}
        }).start();
    }

    public void login(ActionEvent actionEvent) throws IOException {
        Message message =  new Message(RequestType.login);
        User user = new User(username.getText(),password.getText());
        Main.output.writeObject(message);
        Main.output.writeObject(user);
        DataInputStream dataInputStream = new DataInputStream(Main.socket.getInputStream());
        String response = dataInputStream.readUTF();
        if (response.equals("Ok")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You have been entered");
            alert.showAndWait();
        }
        else if (response.equals("Password is wrong")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(response);
            alert.showAndWait();
        }
        else if (response.equals("Username does not exist")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(response);
            alert.showAndWait();
        }
    }

    public void signup(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("../view/signup.fxml");
    }

    public void doneForget(ActionEvent actionEvent) throws IOException {
        Message message =  new Message(RequestType.forget_password);
        User user = new User(forgetUsername.getText(),null);
        user.setBestFriend(forgetBestfriend.getText());
        Main.output.writeObject(message);
        Main.output.writeObject(user);
        DataInputStream dataInputStream = new DataInputStream(Main.socket.getInputStream());
        String response = dataInputStream.readUTF();
        if (response.equals("Ok")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You have been entered");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(response);
            alert.showAndWait();
        }
    }

    public void backForget(ActionEvent actionEvent) {
        forgetPane.setVisible(false);
    }

    public void forgetPassword(MouseEvent mouseEvent) {
        forgetPane.setVisible(true);
    }
}
