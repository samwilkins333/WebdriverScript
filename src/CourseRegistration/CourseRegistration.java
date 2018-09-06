package CourseRegistration;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CourseRegistration extends Application {

    @Override
    public void start(Stage primaryStage) {
        PaneOrganizer organizer = new PaneOrganizer();

        Stage stage = new Stage();
        Scene scene = new Scene(organizer.root, 800, 800);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Registration Wizard");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
