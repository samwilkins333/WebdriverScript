package CourseRegistration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import static CourseRegistration.Constants.*;


public class CourseRegistration {

    public static void main(String[] args) {
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
