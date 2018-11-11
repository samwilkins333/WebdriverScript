package CourseRegistration;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static CourseRegistration.Constants.chrome;
import static CourseRegistration.Constants.driverPath;

class PaneOrganizer {
    public Pane root = new Pane();

    private ObservableList<String> listViewItems = FXCollections.observableArrayList();
    private LinkedHashMap<String, Operation> crnMapping = new LinkedHashMap<>();

    private TextField usernameField;
    private TextField passwordField;
    private TextField advisingPinField;
    private TextField crnEntryField;

    private FadeTransition usernameFail;
    private FadeTransition passwordFail;
    private FadeTransition pinFail;
    private FadeTransition crnFail;

    private FadeTransition emptyListFail;

    private double _screenX;
    private double _height;
 
    PaneOrganizer(double screenX, double screenY) {
        _screenX = screenX;
        root.setFocusTraversable(true);
        root.requestFocus();

        System.setProperty(chrome, driverPath);

        ListView<String> crnListView = new ListView<>();
        crnListView.setLayoutX(62.5);
        crnListView.setLayoutY(275);
        crnListView.setPrefSize(275, 350);
        crnListView.setEditable(true);
        crnListView.setItems(listViewItems);
        crnListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ObservableList<String> selectedItems = crnListView.getSelectionModel().getSelectedItems();
                listViewItems.removeAll(selectedItems);
                selectedItems.forEach(i -> crnMapping.remove(i));
            }
        });

        Label _title = new Label("Registration Wizard");
        _title.setFont(Font.font("Georgia", FontWeight.BOLD, 25));
        _title.setLayoutY(55);
        _title.setLayoutX(70);
        _title.requestFocus();

        usernameField = new TextField();
        usernameField.setPromptText("Brown username...");
        usernameField.setLayoutX(62.5);
        usernameField.setLayoutY(115);
        usernameField.setPrefWidth(275);

        passwordField = new TextField();
        passwordField.setPromptText("Password...");
        passwordField.setLayoutX(62.5);
        passwordField.setLayoutY(155);
        passwordField.setPrefWidth(275);

        advisingPinField = new TextField();
        advisingPinField.setPromptText("Advising PIN...");
        advisingPinField.setLayoutX(62.5);
        advisingPinField.setLayoutY(195);
        advisingPinField.setPrefWidth(275);

        crnEntryField = new TextField();
        crnEntryField.setPromptText("CRN (12345)");
        crnEntryField.setLayoutX(62.5);
        crnEntryField.setLayoutY(235);
        crnEntryField.setPrefWidth(100);
        crnEntryField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) crnEntryField.clear();
        });

        Line usernameFailure = new Line(64, 145, 336, 145);
        usernameFailure.setStroke(Color.RED);
        usernameFailure.setStrokeWidth(2);
        usernameFailure.setStrokeLineCap(StrokeLineCap.ROUND);
        usernameFailure.setOpacity(0);

        Line passwordFailure = new Line(64, 185, 336, 185);
        passwordFailure.setStroke(Color.RED);
        passwordFailure.setStrokeWidth(2);
        passwordFailure.setStrokeLineCap(StrokeLineCap.ROUND);
        passwordFailure.setOpacity(0);

        Line pinFailure = new Line(64, 225, 336, 225);
        pinFailure.setStroke(Color.RED);
        pinFailure.setStrokeWidth(2);
        pinFailure.setStrokeLineCap(StrokeLineCap.ROUND);
        pinFailure.setOpacity(0);

        Line crnFailure = new Line(64, 265, 159.5, 265);
        crnFailure.setStroke(Color.RED);
        crnFailure.setStrokeWidth(2);
        crnFailure.setStrokeLineCap(StrokeLineCap.ROUND);
        crnFailure.setOpacity(0);

        usernameFail = new FadeTransition(Duration.millis(100), usernameFailure);
        usernameFail.setFromValue(0);
        usernameFail.setToValue(1);
        usernameFail.setAutoReverse(true);
        usernameFail.setCycleCount(4);
        usernameFail.setOnFinished(event -> usernameField.requestFocus());

        passwordFail = new FadeTransition(Duration.millis(100), passwordFailure);
        passwordFail.setFromValue(0);
        passwordFail.setToValue(1);
        passwordFail.setAutoReverse(true);
        passwordFail.setCycleCount(4);
        passwordFail.setOnFinished(event -> {
            if (!usernameField.getText().isEmpty()) passwordField.requestFocus();
        });

        pinFail = new FadeTransition(Duration.millis(100), pinFailure);
        pinFail.setFromValue(0);
        pinFail.setToValue(1);
        pinFail.setAutoReverse(true);
        pinFail.setCycleCount(4);
        pinFail.setOnFinished(event -> {
            if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) advisingPinField.requestFocus();
        });

        crnFail = new FadeTransition(Duration.millis(100), crnFailure);
        crnFail.setFromValue(0);
        crnFail.setToValue(1);
        crnFail.setAutoReverse(true);
        crnFail.setCycleCount(4);
        crnFail.setOnFinished(event -> crnEntryField.requestFocus());

        emptyListFail = new FadeTransition(Duration.millis(100), crnListView);
        emptyListFail.setFromValue(1);
        emptyListFail.setToValue(0);
        emptyListFail.setAutoReverse(true);
        emptyListFail.setCycleCount(4);

        Button add = new Button();
        add.setText("Add");
        add.setPrefWidth(48);
        add.setLayoutX(169);
        add.setLayoutY(235);
        add.setOnMouseClicked(ParseCRN(Operation.AddClass));

        Button drop = new Button();
        drop.setText("Drop");
        drop.setPrefWidth(48);
        drop.setLayoutX(220);
        drop.setLayoutY(235);
        drop.setOnMouseClicked(ParseCRN(Operation.DropClass));

        Button submit = new Button();
        submit.setText("Submit");
        submit.setPrefWidth(65);
        submit.setLayoutX(271);
        submit.setLayoutY(235);
        submit.setOnMouseClicked(ParseCRN(Operation.SubmitChanges));

        Button run = new Button();
        run.setText("Register!");
        run.setLayoutX(62.5);
        run.setLayoutY(640);
        run.setPrefSize(275, 45);
        run.setOnMouseClicked(event -> run());

        Button quit = new Button();
        quit.setText("Quit!");
        quit.setLayoutX(62.5);
        quit.setLayoutY(697);
        quit.setPrefSize(275, 25);
        quit.setOnMouseClicked(event -> System.exit(0));

        _height = screenY - 40 - 87;
        Rectangle borderRect = new Rectangle(20, 20, 360, _height);
        borderRect.setStroke(Color.BLACK);
        borderRect.setStrokeWidth(3);
        borderRect.setFill(Color.TRANSPARENT);

        root.getChildren().addAll(borderRect, crnListView, _title);
        root.getChildren().addAll(usernameField, passwordField, advisingPinField, crnEntryField);
        root.getChildren().addAll(usernameFailure, passwordFailure, pinFailure, crnFailure);
        root.getChildren().addAll(add, drop, submit, run, quit);
    }

    private EventHandler<MouseEvent> ParseCRN(Operation operation) {
        return event -> {
            String crn = crnEntryField.getText();

            if (operation != Operation.SubmitChanges) {
                if (crn.isEmpty() || crn.length() != 5 || !tryNumericParse(crn)) {
                    crnFail.play();
                    return;
                }

                String prefix = (operation == Operation.AddClass) ? "+ " : "- ";
                listViewItems.add(prefix + crn);
            } else
                listViewItems.add("* Submit Changes *");

            crnMapping.put(crn, operation);
            crnEntryField.clear();
            crnEntryField.requestFocus();
        };
    }

    private boolean tryNumericParse(String crn) {
        try {
            Integer.parseInt(crn);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public void run() {
        boolean noUsername = usernameField.getText().isEmpty();
        boolean noPassword = passwordField.getText().isEmpty();
        String pin = advisingPinField.getText();
        boolean properPin = pin.length() == 6 && tryNumericParse(pin);

        if (noUsername) usernameFail.play();
        if (noPassword) passwordFail.play();
        if (!properPin) pinFail.play();
        if (listViewItems.isEmpty() && !noUsername && !noPassword && properPin) {
            emptyListFail.play();
            crnEntryField.requestFocus();
            crnEntryField.setFocusTraversable(true);
        }

        if (noUsername || noPassword || !properPin || listViewItems.isEmpty()) return;

        Launch();
    }

    private void Launch() {
        WebDriver driver = new ChromeDriver();
        Actions actions = new Actions(driver);

        driver.manage().window().setPosition(new Point(400, 65));
        driver.manage().window().setSize(new Dimension((int) _screenX - 420, (int)_height));

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

        List<WebElement> pinEls = driver.findElements(By.xpath(".//input[contains(@id, 'apin_id')]"));
        if (pinEls.size() > 0) {
            WebElement pinEl = pinEls.get(0);
            pinEl.click();
            pinEl.sendKeys(advisingPinField.getText());
            driver.findElement(By.xpath(".//input[contains(@value, 'Submit')]")).click();
        }

        while (!driver.findElements(By.xpath(".//input[contains(@id, 'crn_')]")).iterator().hasNext()) {
            driver.navigate().refresh();
        }

        Iterator<WebElement> crns = driver.findElements(By.xpath(".//input[contains(@id, 'crn_')]")).iterator();

        for (Map.Entry<String, Operation> crn : crnMapping.entrySet()) {
            switch (crn.getValue()) {
                case AddClass:
                    if (crns.hasNext()) {
                        WebElement toFill = crns.next();
                        toFill.click();
                        toFill.sendKeys(crn.getKey());
                    }
                    break;
                case DropClass:
                    List<WebElement> candidates = driver.findElements(By.xpath(".//select[./ancestor::td[./following-sibling::td[./child::input[contains(@value, '" + crn.getKey() + "')]]]]"));
                    if (candidates.isEmpty()) break;

                    Select selectable = new Select(candidates.get(0));
                    selectable.selectByVisibleText("DROP");
                    break;
                case SubmitChanges:
                    actions.moveToElement(driver.findElement(By.xpath(".//input[contains(@value, 'Submit Changes')]"))).click().perform();
                    break;
            }
        }

        boolean success = driver.findElements(By.xpath(".//span[contains(@class, 'errortext')]")).isEmpty();
        String green = "-fx-background-color: lawngreen;";
        String red = "-fx-background-color: red;";
        root.setStyle(success ? green : red);
    }

}
