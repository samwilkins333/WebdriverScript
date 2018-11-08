package CourseRegistration;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class CourseRegistration extends Application {
    private Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
        double x = primaryScreenBounds.getMaxX();
        double y = primaryScreenBounds.getMaxY();
        PaneOrganizer organizer = new PaneOrganizer(x, y);

        stage.setScene(new Scene(organizer.root, x, y));
        stage.setTitle("Registration Wizard");
        stage.setMaximized(true);
        stage.setAlwaysOnTop(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}