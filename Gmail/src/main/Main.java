package main;

import javafx.application.Application;
import javafx.stage.Stage;
import utility.User;

import java.io.*;
import java.net.Socket;

public class Main extends Application {

    public static int WIDTH = 400;
    public static int HEIGHT = 600;

    public static Socket socket;
    public static ObjectOutputStream output;
    public static ObjectInputStream input;

    public static final String serverIP = "localhost";
    public static final int requestPort = 2001;
    public static Stage stage;

    public static User user = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        PageLoader.initStage(primaryStage);
        new PageLoader().load("../view/login.fxml");
    }


    public static void main(String[] args) {
        launch(args);
    }
}