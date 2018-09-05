package CourseRegistration;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import static CourseRegistration.Constants.*;

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

        System.setProperty(chrome, driverPath);
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
    }

}
