package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import main.Main;
import main.PageLoader;
import utility.Message;
import utility.RequestType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LoginController {

    @FXML
    Pane PaneConnect;
    @FXML
    TextField IpAddress , username;
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

    public void login(ActionEvent actionEvent) {
        Message message =  new Message(RequestType.login);
    }

    public void signup(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("../view/signup.fxml");
    }
}
