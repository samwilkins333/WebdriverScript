package CourseRegistration;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CourseRegistration extends Application {
    public static Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) {
        PaneOrganizer organizer = new PaneOrganizer();

        stage.setScene(new Scene(organizer.root, 400, 800));
        stage.setResizable(false);
        stage.setTitle("Registration Wizard");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
