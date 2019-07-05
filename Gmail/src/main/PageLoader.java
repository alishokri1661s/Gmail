package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PageLoader {
    private static Stage stage;

    /**
     * init a stage for app
     * @param primaryStage
     */
    public static void initStage(Stage primaryStage){
        stage = primaryStage;
        stage.setTitle("Gmail");
        stage.setResizable(false);
        stage.setWidth(Main.WIDTH);
        stage.setHeight(Main.HEIGHT);
        stage.initStyle(StageStyle.DECORATED);
        stage.getIcons().add(new Image(Paths.get("src/view/icon.png").toUri().toString()));
    }

    /**
     * loads any pages we want
     * @param url
     * @throws IOException
     */
    public void load(String url) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(root, Main.WIDTH, Main.HEIGHT));
        stage.show();
    }
}
