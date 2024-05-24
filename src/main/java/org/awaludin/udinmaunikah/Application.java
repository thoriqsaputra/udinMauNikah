package org.awaludin.udinmaunikah;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class Application extends javafx.application.Application {

    private static Stack<Scene> sceneStack = new Stack<>();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Awali dengan udin");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }

    public static void pushScene(Scene scene) {
        sceneStack.push(scene);
    }

    public static Scene popScene() {
        return sceneStack.isEmpty() ? null : sceneStack.pop();
    }

}