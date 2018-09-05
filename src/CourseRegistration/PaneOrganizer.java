package CourseRegistration;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.openqa.selenium.support.Colors;

public class PaneOrganizer {

    public Pane root = new Pane();

    PaneOrganizer() {
        Label title = new Label("Registration Wizard");
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 35));
        title.setLayoutY(85);
        title.setLayoutX(200);

        TextField toAdd = new TextField();
        toAdd.setPromptText("Add...");
        toAdd.setLayoutX(153);
        toAdd.setLayoutY(180);
        toAdd.setPrefWidth(200);
        toAdd.setFocusTraversable(false);

        TextField toDrop = new TextField();
        toDrop.setPromptText("Drop...");
        toDrop.setLayoutX(446);
        toDrop.setLayoutY(180);
        toDrop.setPrefWidth(200);
        toDrop.setFocusTraversable(false);

        Rectangle borderRect = new Rectangle(20, 20, 760, 760);
        borderRect.setStroke(Color.BLACK);
        borderRect.setStrokeWidth(3);
        borderRect.setFill(Color.TRANSPARENT);

        root.getChildren().addAll(title, toAdd, toDrop, borderRect);
    }

}
