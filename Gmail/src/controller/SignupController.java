package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.Main;
import main.PageLoader;
import utility.Message;
import utility.RequestType;
import utility.User;

import java.io.DataInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

public class SignupController {
    @FXML
    TextField username, firstname, lastname;
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

    public void signup(ActionEvent actionEvent) throws IOException, InterruptedException {
        otherSigninPane.setVisible(true);
        if(checkValid()) {
            User user = new User(firstname.getText(), lastname.getText(),
                    username.getText(), password.getText(), birthday.getEditor().getText());
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
            else
                otherSigninPane.setVisible(true);
        }
    }

    private boolean checkValid() throws IOException {
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
        new PageLoader().load("./login.fxml");
    }

    public void femaleClicked(MouseEvent mouseEvent) {
        Platform.runLater(()->selectMale.setSelected(false));
    }

    public void maleClicked(MouseEvent mouseEvent) {
        Platform.runLater(()->selectFemale.setSelected(false));
    }



}
