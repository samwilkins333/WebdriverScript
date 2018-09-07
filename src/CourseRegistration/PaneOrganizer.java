package CourseRegistration;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static CourseRegistration.Constants.chrome;
import static CourseRegistration.Constants.driverPath;

public class PaneOrganizer {
    public Pane root = new Pane();

    private ObservableList<String> listViewItems = FXCollections.observableArrayList();
    private HashMap<String, Operation> crnMapping = new HashMap<>();

    private TextField usernameField;
    private TextField passwordField;

    private TextField toAdd;
    private TextField toDrop;

    private FadeTransition usernameFail;
    private FadeTransition passwordFail;

    PaneOrganizer() {
        System.setProperty(chrome, driverPath);

        ListView<String> toAddListView = new ListView<>();
        toAddListView.setLayoutX(153);
        toAddListView.setLayoutY(300);
        toAddListView.setPrefSize(200, 350);
        toAddListView.setEditable(true);

        toAddListView.setItems(listViewItems);

        Label title = new Label("Registration Wizard");
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 35));
        title.setLayoutY(85);
        title.setLayoutX(200);

        usernameField = new TextField();
        usernameField.setPromptText("Brown username...");
        usernameField.setLayoutX(153);
        usernameField.setLayoutY(180);
        usernameField.setPrefWidth(200);
        usernameField.requestFocus();
        usernameField.setFocusTraversable(true);

        int errorLineOffset = 216;

        Line usernameFailure = new Line(155, errorLineOffset, 351, errorLineOffset);
        usernameFailure.setStroke(Color.RED);
        usernameFailure.setStrokeWidth(2);
        usernameFailure.setStrokeLineCap(StrokeLineCap.ROUND);
        usernameFailure.setOpacity(0);

        Line passwordFailure = new Line(448, errorLineOffset, 644, errorLineOffset);
        passwordFailure.setStroke(Color.RED);
        passwordFailure.setStrokeWidth(2);
        passwordFailure.setStrokeLineCap(StrokeLineCap.ROUND);
        passwordFailure.setOpacity(0);

        usernameFail = new FadeTransition(Duration.millis(500), usernameFailure);
        usernameFail.setFromValue(0);
        usernameFail.setToValue(1);

        passwordFail = new FadeTransition(Duration.millis(500), passwordFailure);
        passwordFail.setFromValue(0);
        passwordFail.setToValue(1);

        passwordField = new TextField();
        passwordField.setPromptText("Password...");
        passwordField.setLayoutX(446);
        passwordField.setLayoutY(180);
        passwordField.setPrefWidth(200);
        passwordField.requestFocus();
        passwordField.setFocusTraversable(true);

        toAdd = new TextField();
        toAdd.setPromptText("Add...");
        toAdd.setLayoutX(153);
        toAdd.setLayoutY(240);
        toAdd.setPrefWidth(200);
        toAdd.setOnKeyPressed(KeyHandler(Operation.AddClass));
        toAdd.requestFocus();
        toAdd.setFocusTraversable(true);

        toDrop = new TextField();
        toDrop.setPromptText("Drop...");
        toDrop.setLayoutX(446);
        toDrop.setLayoutY(240);
        toDrop.setPrefWidth(200);
        toDrop.setOnKeyPressed(KeyHandler(Operation.DropClass));
        toDrop.requestFocus();
        toDrop.setFocusTraversable(true);

        Button run = new Button();
        run.setText("Run");
        run.setLayoutX(352.5);
        run.setLayoutY(700);
        run.setPrefSize(95, 40);
        run.setOnMouseClicked(event -> run());

        Rectangle borderRect = new Rectangle(20, 20, 360, 760);
        borderRect.setStroke(Color.BLACK);
        borderRect.setStrokeWidth(3);
        borderRect.setFill(Color.TRANSPARENT);

        root.getChildren().addAll(title, usernameField, passwordField, usernameFailure, passwordFailure, toAdd, toDrop, toAddListView, toDropListView, borderRect, run);
    }

    private EventHandler<KeyEvent> KeyHandler(Operation op) {
        return event -> {
            TextField field = (op == Operation.AddClass) ? toAdd : toDrop;
            switch (event.getCode()) {
              case ENTER:
                  crnMapping.put(field.getText(), op);
              case ESCAPE:
                  field.clear();
                  break;
          }
        };
    }

    public void run() {
        boolean noUsername = usernameField.getText().isEmpty();
        boolean noPassword = passwordField.getText().isEmpty();

        if (noUsername) usernameFail.play();
        if (noPassword) passwordFail.play();

        if (noUsername || noPassword) return;

        WebDriver driver = new ChromeDriver();
        Actions actions = new Actions(driver);

        driver.get("https://selfservice.brown.edu");

        WebElement username = driver.findElement(By.xpath(".//input[contains(@id, 'username')]"));
        username.click();
        username.sendKeys(usernameField.getText());

        WebElement password = driver.findElement(By.xpath(".//input[contains(@id, 'password')]"));
        password.click();
        password.sendKeys(passwordField.getText());

        driver.findElement(By.xpath(".//button[contains(text(), 'Log In')]")).click();

        while (driver.findElements(By.xpath(".//h2[contains(text(), 'Main Menu')]")).isEmpty()) actions.pause(java.time.Duration.ofSeconds((long) 0.5)).perform();

        driver.findElement(By.xpath(".//a[contains(@href, 'StuMainMnu')]")).click();
        driver.findElement(By.xpath(".//a[contains(@href, 'RegMnu')]")).click();
        driver.findElement(By.xpath(".//a[contains(@href, 'AltPin')]")).click();
        driver.findElement(By.xpath(".//input[contains(@value, 'Submit')]")).click();

        WebElement pin = driver.findElement(By.xpath(".//input[contains(@id, 'apin_id')]"));
        pin.click();
        pin.sendKeys("516115");
        driver.findElement(By.xpath(".//input[contains(@value, 'Submit')]")).click();

        while (LocalDateTime.now().isBefore(LocalDateTime.of(2018, 6, 5, 8, 00))) actions.pause(java.time.Duration.ofMillis(50)).perform();

        List<WebElement> crns = driver.findElements(By.xpath(".//input[contains(@id, 'crn_')]"));

        for (Map.Entry<String, Operation> crn : crnMapping.entrySet()) {
            switch (crn.getValue())
        }

    }

}
