package CourseRegistration;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static CourseRegistration.Constants.chrome;
import static CourseRegistration.Constants.driverPath;

public class PaneOrganizer {
    public Pane root = new Pane();
    private ArrayList<String> toAddList = new ArrayList<>();
    private ArrayList<String> toDropList = new ArrayList<>();

    PaneOrganizer() {
        System.setProperty(chrome, driverPath);

        Label title = new Label("Registration Wizard");
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 35));
        title.setLayoutY(85);
        title.setLayoutX(200);

        Label toAddOutput = new Label();
        toAddOutput.setLayoutX(153);
        toAddOutput.setLayoutY(220);
        toAddOutput.setPrefWidth(200);

        Label toDropOutput = new Label();
        toDropOutput.setLayoutX(446);
        toDropOutput.setLayoutY(220);
        toDropOutput.setPrefWidth(200);

        TextField toAdd = new TextField();
        toAdd.setPromptText("Add...");
        toAdd.setLayoutX(153);
        toAdd.setLayoutY(180);
        toAdd.setPrefWidth(200);
        toAdd.setOnKeyPressed(KeyHandler(toAdd, toAddList, toAddOutput));

        TextField toDrop = new TextField();
        toDrop.setPromptText("Drop...");
        toDrop.setLayoutX(446);
        toDrop.setLayoutY(180);
        toDrop.setPrefWidth(200);
        toDrop.setOnKeyPressed(KeyHandler(toDrop, toDropList, toDropOutput));

        Button run = new Button();
        run.setText("Run");
        run.setLayoutX(352.5);
        run.setLayoutY(700);
        run.setPrefSize(95, 40);
        run.setOnMouseClicked(event -> run());

        Rectangle borderRect = new Rectangle(20, 20, 760, 760);
        borderRect.setStroke(Color.BLACK);
        borderRect.setStrokeWidth(3);
        borderRect.setFill(Color.TRANSPARENT);

        root.getChildren().addAll(title, toAdd, toDrop, toAddOutput, toDropOutput, borderRect, run);
    }

    private EventHandler<KeyEvent> KeyHandler(TextField box, List<String> crns, Label output) {
        return event -> {
            switch (event.getCode()) {
              case ENTER:
                  String crn = box.getText();
                  crns.add(crn);
                  output.setText(output.getText() + "\n" + crn);
                  break;
              case ESCAPE:
                  box.clear();
                  break;
          }
        };
    }

    public void run() {
        WebDriver driver = new ChromeDriver();
        Actions actions = new Actions(driver);

        driver.get("https://selfservice.brown.edu");

        WebElement username = driver.findElement(By.xpath(".//input[contains(@id, 'username')]"));
        username.click();
        username.sendKeys("swilkin5");

        WebElement password = driver.findElement(By.xpath(".//input[contains(@id, 'password')]"));
        password.click();
        password.sendKeys("Yj#l2ocDmP.0?@");

        driver.findElement(By.xpath(".//button[contains(text(), 'Log In')]")).click();

        while (driver.findElements(By.xpath(".//h2[contains(text(), 'Main Menu')]")).isEmpty()) actions.pause(Duration.ofSeconds(1)).perform();

        driver.findElement(By.xpath(".//a[contains(@href, 'StuMainMnu')]")).click();
        driver.findElement(By.xpath(".//a[contains(@href, 'RegMnu')]")).click();
        driver.findElement(By.xpath(".//a[contains(@href, 'AltPin')]")).click();
        driver.findElement(By.xpath(".//input[contains(@value, 'Submit')]")).click();

        WebElement pin = driver.findElement(By.xpath(".//input[contains(@id, 'apin_id')]"));
        pin.click();
        pin.sendKeys("516115");
        driver.findElement(By.xpath(".//input[contains(@value, 'Submit')]")).click();

        while (LocalDateTime.now().isBefore(LocalDateTime.of(2018, 6, 5, 8, 00))) actions.pause(Duration.ofMillis(50)).perform();

        List<WebElement> crns = driver.findElements(By.xpath(".//input[contains(@id, 'crn_')]"));
        for (int i = 0; i < toAddList.size(); i++) {
            WebElement thisCrn = crns.get(i);
            thisCrn.click();
            thisCrn.sendKeys(toAddList.get(i));
        }

    }

}
