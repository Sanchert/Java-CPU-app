    package com.example.cpu_fx;

    import javafx.application.Application;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Scene;
    import javafx.stage.Stage;

    import java.io.IOException;

    public class CPU_Application extends Application {
        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(CPU_Application.class.getResource("main.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Pseudo Assembler Executor");
            stage.setScene(scene);
            stage.show();
        }

    public static void main(String[] args) {
        launch();
    }
}